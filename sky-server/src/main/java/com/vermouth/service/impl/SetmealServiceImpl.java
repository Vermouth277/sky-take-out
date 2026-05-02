package com.vermouth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vermouth.dto.SetmealDTO;
import com.vermouth.dto.SetmealPageQueryDTO;
import com.vermouth.entity.Setmeal;
import com.vermouth.entity.SetmealDish;
import com.vermouth.mapper.SetmealDishMapper;
import com.vermouth.mapper.SetmealMapper;
import com.vermouth.result.PageResult;
import com.vermouth.service.SetmealService;
import com.vermouth.vo.DishItemVO;
import com.vermouth.vo.SetmealExtraVO;
import com.vermouth.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper  setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;


    /**
     * 新增套餐
     * @param setmealDTO
     */
    @Transactional
    @Override
    public void addSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        setmealMapper.insert(setmeal);

        Long setmealId = setmeal.getId();

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if( setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealId);
            });

            setmealDishMapper.insert(setmealDishes);
        }
    }

    /**
     * 动态分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> setmealVOS = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(setmealVOS.getTotal(),setmealVOS.getResult());
    }

    /**
     * 根据Id查询套餐
     * @param id
     * @return
     */
    @Override
    public SetmealExtraVO selectById(Long id) {
        Setmeal setmeal = setmealMapper.selectById(id);

        SetmealExtraVO setmealExtraVO = new SetmealExtraVO();
        BeanUtils.copyProperties(setmeal,setmealExtraVO);

        List<SetmealDish> setmealDishes = setmealDishMapper.getSetmealDishesBySetmealId(id);
        setmealExtraVO.setSetmealDishes(setmealDishes);

        return setmealExtraVO;
    }

    /**
     * 更新套餐
     * @param setmealDTO
     */
    @Transactional
    @Override
    public void updateSetmeal(SetmealDTO setmealDTO) {
        Setmeal  setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        setmealMapper.update(setmeal);

        Long setmealId = setmeal.getId();
        setmealDishMapper.deleteBySetmealId(setmealId);


        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if( setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealId);
            });

            setmealDishMapper.insert(setmealDishes);
        }
    }

    /**
     * 套餐起售、停售
     * @param status
     * @param id
     */
    @Override
    public void updateSetmealStatus(Integer status, Long id) {
        Setmeal setmeal = setmealMapper.selectById(id);
        setmeal.setStatus(status);
        setmealMapper.update(setmeal);
    }

    /**
     * 批量删除套餐
     * @param ids
     */
    @Override
    public void deleteSetmeal(List<Long> ids) {
        if(ids != null && !ids.isEmpty()) {
            setmealDishMapper.deleteBatch(ids);
            setmealMapper.deleteBatch(ids);
        }
    }

    /**
     * 根据分类id查询套餐
     * @param setmeal
     * @return
     */
    @Override
    public List<Setmeal> getByCategoryId(Setmeal setmeal) {
        return setmealMapper.list(setmeal);
    }

    /**
     * 根据套餐id查询包含的菜品
     * @param id
     * @return
     */
    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
