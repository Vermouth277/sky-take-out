package com.vermouth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vermouth.constant.MessageConstant;
import com.vermouth.constant.StatusConstant;
import com.vermouth.dto.DishDTO;
import com.vermouth.dto.DishPageQueryDTO;
import com.vermouth.entity.Dish;
import com.vermouth.entity.DishFlavor;
import com.vermouth.exception.DeletionNotAllowedException;
import com.vermouth.mapper.DishFlavorMapper;
import com.vermouth.mapper.DishMapper;
import com.vermouth.mapper.SetmealDishMapper;
import com.vermouth.result.PageResult;
import com.vermouth.service.DishService;
import com.vermouth.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.vermouth.constant.RedisConstant.CACHE_DISH_KEY;
import static com.vermouth.constant.RedisConstant.CACHE_DISH_TTL;

@Slf4j
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    DishMapper dishMapper;

    @Autowired
    DishFlavorMapper dishFlavorMapper;

    @Autowired
    SetmealDishMapper setmealDishMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     * @param dishDTO
     */
    @Transactional
    @Override
    public void addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        //插入菜品
        dishMapper.insert(dish);

        //获得回显id
        Long dishId = dish.getId();

        //给口味加上Id并批量插入到数据库
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if ( flavors != null && !flavors.isEmpty()){
            for (DishFlavor flavor:flavors){
                flavor.setDishId(dishId);
            }
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 菜品批量删除
     * @param ids
     */
    @Transactional
    @Override
    public void deleteBatch(List<Long> ids) {
        //判断当前菜品是否能够删除---是否存在起售中的菜品？？
        for(Long id:ids){
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE){
                //当前菜品处于起售中，不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        //判断当前菜品是否能够删除---是否被套餐关联了？？
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && !setmealIds.isEmpty()){
            //当前菜品被套餐关联了，不能删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //删除菜品表中的菜品数据
        for (Long id : ids) {
            dishMapper.deleteById(id);
            //删除菜品关联的口味数据
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    /**
     * 根据id查询菜品和对应的口味数据
     * @param id
     * @return
     */
    @Override
    public DishVO getByIdWithFlavor(Long id) {
        //根据id查询菜品数据
        Dish dish = dishMapper.getById(id);

        //根据菜品id查询口味数据
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);

        //将查询到的数据封装到VO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);

        return dishVO;
    }

    /**
     * 修改菜品和对应的口味数据
     * @param dishDTO
     */
    @Transactional
    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        //修改菜品表基本信息
        dishMapper.update(dish);

        //删除原有的口味数据
        dishFlavorMapper.deleteByDishId(dishDTO.getId());

        //重新插入口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if ( flavors != null && !flavors.isEmpty()){
            for (DishFlavor flavor:flavors){
                flavor.setDishId(dishDTO.getId());
            }
            dishFlavorMapper.insertBatch(flavors);
        }

        String key = CACHE_DISH_KEY + dish.getCategoryId();
        redisTemplate.delete(key);
    }

    /**
     * 根据分类Id查询菜品
     * @param categoryId
     * @return
     */
    @Override
    public List<Dish> getByCategoryId(Long categoryId) {
        return dishMapper.getByCategoryId(categoryId);
    }

    /**
     * 菜品起售、停售
     * @param status
     */
    @Transactional
    @Override
    public void updateStatus(Integer status, Long id) {
        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .build();

        dishMapper.update(dish);
        Dish newDish = dishMapper.getById(id);
        redisTemplate.delete(CACHE_DISH_KEY + newDish.getCategoryId());
    }

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        //查Redis
        String key = CACHE_DISH_KEY +  dish.getCategoryId();

        List<DishVO> redisList = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if (redisList != null && !redisList.isEmpty()) {
            redisTemplate.expire(key, CACHE_DISH_TTL, TimeUnit.MINUTES);
            return redisList;
        }

        //未命中，查数据库
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        //保存到Redis
        redisTemplate.opsForValue().set(key, dishVOList, CACHE_DISH_TTL, TimeUnit.MINUTES);
        return dishVOList;
    }
}
