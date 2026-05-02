package com.vermouth.service;

import com.vermouth.dto.SetmealDTO;
import com.vermouth.dto.SetmealPageQueryDTO;
import com.vermouth.entity.Setmeal;
import com.vermouth.result.PageResult;
import com.vermouth.vo.DishItemVO;
import com.vermouth.vo.SetmealExtraVO;

import java.util.List;

public interface SetmealService {

    /**
     * 新增套餐
     * @param setmealDTO
     */
    void addSetmeal(SetmealDTO setmealDTO);

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据Id查询套餐
     * @param id
     * @return
     */
    SetmealExtraVO selectById(Long id);

    /**
     * 修改套餐
     * @param setmealDTO
     */
    void updateSetmeal(SetmealDTO setmealDTO);

    /**
     * 套餐起售、停售
     * @param status
     * @param id
     */
    void updateSetmealStatus(Integer status, Long id);

    /**
     * 批量删除套餐
     * @param ids
     */
    void deleteSetmeal(List<Long> ids);


    /**
     * 根据分类id查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> getByCategoryId(Setmeal setmeal);

    /**
     *根据套餐id查询包含的菜品
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
