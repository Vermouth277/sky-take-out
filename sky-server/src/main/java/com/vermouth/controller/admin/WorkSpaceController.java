package com.vermouth.controller.admin;

import com.vermouth.result.Result;
import com.vermouth.service.WorkSpaceService;
import com.vermouth.vo.BusinessDataVO;
import com.vermouth.vo.DishOverViewVO;
import com.vermouth.vo.OrderOverViewVO;
import com.vermouth.vo.SetmealOverViewVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/workspace")
@Slf4j
public class WorkSpaceController {

    @Autowired
    private WorkSpaceService workSpaceService;


    @Operation(summary = "查询今日运营数据")
    @GetMapping("/businessData")
    public Result<BusinessDataVO> businessData() {
        LocalDateTime begin = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDateTime.now();
        log.info("begin:{},end:{}", begin, end);
        return Result.success(workSpaceService.getBusinessData(begin, end));
    }

    @Operation(summary = "查询套餐总览")
    @GetMapping("/overviewSetmeals")
    public Result<SetmealOverViewVO> overviewSetmeals() {
        return Result.success(workSpaceService.getOverviewSetmeals());
    }

    @Operation(summary = "查询菜品总览")
    @GetMapping("/overviewDishes")
    public Result<DishOverViewVO> overviewDishes() {
        return Result.success(workSpaceService.getOverviewDishes());
    }

    @Operation(summary = "查询订单管理数据")
    @GetMapping("/overviewOrders")
    public Result<OrderOverViewVO> overviewOrders() {
        return Result.success(workSpaceService.getOverviewOrders());
    }
}
