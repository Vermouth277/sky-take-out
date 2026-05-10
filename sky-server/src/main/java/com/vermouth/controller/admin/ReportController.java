package com.vermouth.controller.admin;

import com.vermouth.result.Result;
import com.vermouth.service.ReportService;
import com.vermouth.vo.OrderReportVO;
import com.vermouth.vo.SalesTop10ReportVO;
import com.vermouth.vo.TurnoverReportVO;
import com.vermouth.vo.UserReportVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
@Slf4j
@Tag(name = "统计报表相关接口")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Operation(summary = "营业额数据统计")
    @GetMapping("turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return Result.success(reportService.getTurnover(begin, end));
    }

    @Operation(summary = "用户统计")
    @GetMapping("userStatistics")
    public Result<UserReportVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return Result.success(reportService.getUser(begin, end));
    }


    @Operation(summary = "用户统计")
    @GetMapping("ordersStatistics")
    public Result<OrderReportVO> getOrderStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return Result.success(reportService.getOrder(begin, end));
    }

    @Operation(summary = "销量排名统计")
    @GetMapping("top10")
    public Result<SalesTop10ReportVO> top10(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ){
        return Result.success(reportService.getSalesTop10(begin,end));
    }

    @Operation(summary = "导出运营数据报表")
    @GetMapping("export")
    public void export(HttpServletResponse response){
        reportService.exportBusinessData(response);
    }
}
