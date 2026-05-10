package com.vermouth.service;

import com.vermouth.vo.BusinessDataVO;
import com.vermouth.vo.DishOverViewVO;
import com.vermouth.vo.OrderOverViewVO;
import com.vermouth.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkSpaceService {

    /**
     * 今日数据查询
     * @param begin
     * @param end
     * @return
     */
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    /**
     * 查询套餐总览
     * @return
     */
    SetmealOverViewVO getOverviewSetmeals();

    /**
     * 查询菜品总览
     * @return
     */
    DishOverViewVO getOverviewDishes();

    /**
     * 查询订单管理数据
     * @return
     */
    OrderOverViewVO getOverviewOrders();
}
