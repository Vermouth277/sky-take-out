package com.vermouth.mapper;

import com.github.pagehelper.Page;
import com.vermouth.annotation.AutoFill;
import com.vermouth.dto.DishPageQueryDTO;
import com.vermouth.entity.Dish;
import com.vermouth.enumeration.OperationType;
import com.vermouth.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {
    /**
     * 新增菜品
     * @param dish
     */
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 动态查询菜品
     * @param dishPageQueryDTO
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    /**
     * 根据菜品id批量查询分类id
     * @param ids
     * @return
     */
    List<Long> getCategoryIdByDishId(List<Long> ids);

    /**
     * 根据id删除菜品
     * @param id
     */
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    /**
     * 修改菜品
     * @param dish
     */
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 根据分类Id查询菜品
     * @param categoryId
     * @return
     */
    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> getByCategoryId(Long categoryId);

    /**
     * 动态查询
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);

    /**
     * 根据条件统计菜品数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
