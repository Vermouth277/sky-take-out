package com.vermouth.service.impl;

import com.vermouth.constant.StatusConstant;
import com.vermouth.entity.Orders;
import com.vermouth.mapper.DishMapper;
import com.vermouth.mapper.OrderMapper;
import com.vermouth.mapper.SetmealMapper;
import com.vermouth.mapper.UserMapper;
import com.vermouth.service.*;
import com.vermouth.vo.BusinessDataVO;
import com.vermouth.vo.DishOverViewVO;
import com.vermouth.vo.OrderOverViewVO;
import com.vermouth.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private DishMapper dishMapper;

    /**
     * 今日数据查询
     * @param begin
     * @param end
     * @return
     */
    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        //新增用户数量
        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);
        Integer newUsers = userMapper.countByMap(map);
        newUsers = newUsers == null ? 0 : newUsers;

        //全部订单
        Integer orderCount =  orderMapper.countByMap(map);
        orderCount = orderCount == null ? 0 : orderCount;

        //有效订单数
        map.put("status", Orders.COMPLETED);
        Integer validOrderCount = orderMapper.countByMap(map);
        validOrderCount = validOrderCount == null ? 0 : validOrderCount;

        //营业额
        Double turnover = orderMapper.sumByMap(map);
        turnover = turnover == null? 0.0 : turnover;


        Double unitPrice = 0.0;
        Double orderCompletionRate = 0.0;

        if(orderCount != 0 && validOrderCount != 0){
            //订单完成率
            orderCompletionRate =  validOrderCount.doubleValue() / orderCount;
            //平均客单价
            unitPrice = turnover / validOrderCount;
        }

        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }

    /**
     * 查询套餐总览
     * @return
     */
    @Override
    public SetmealOverViewVO getOverviewSetmeals() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = setmealMapper.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = setmealMapper.countByMap(map);

        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    /**
     * 查询菜品总览
     * @return
     */
    @Override
    public DishOverViewVO getOverviewDishes() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = dishMapper.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = dishMapper.countByMap(map);

        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    /**
     * 查询订单管理数据
     * @return
     */
    @Override
    public OrderOverViewVO getOverviewOrders() {
        Map map = new HashMap();
        map.put("begin", LocalDateTime.now().with(LocalTime.MIN));
        map.put("status", Orders.TO_BE_CONFIRMED);

        //待接单
        Integer waitingOrders = orderMapper.countByMap(map);

        //待派送
        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = orderMapper.countByMap(map);

        //已完成
        map.put("status", Orders.COMPLETED);
        Integer completedOrders = orderMapper.countByMap(map);

        //已取消
        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = orderMapper.countByMap(map);

        //全部订单
        map.put("status", null);
        Integer allOrders = orderMapper.countByMap(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }
}
