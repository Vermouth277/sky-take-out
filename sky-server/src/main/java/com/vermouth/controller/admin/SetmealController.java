package com.vermouth.controller.admin;

import com.vermouth.dto.SetmealDTO;
import com.vermouth.dto.SetmealPageQueryDTO;
import com.vermouth.result.PageResult;
import com.vermouth.result.Result;
import com.vermouth.service.SetmealService;
import com.vermouth.vo.SetmealExtraVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/setmeal")
@Tag(name = "套餐相关接口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     * @param setmealDTO
     * @return
     */
    @Operation(summary = "新增套餐")
    @PostMapping()
    private Result addSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐：{}",setmealDTO);
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }


    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Operation(summary = "分页查询")
    @GetMapping("/page")
    private Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("分页查询：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.page(setmealPageQueryDTO);
        return  Result.success(pageResult);
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询套餐")
    @GetMapping("/{id}")
    private Result<SetmealExtraVO> selectById(@PathVariable Long id){
        log.info("根据id查询套餐：{}", id);
        SetmealExtraVO setmealExtraVO = setmealService.selectById(id);
        return Result.success(setmealExtraVO);
    }

    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    @Operation(summary = "修改套餐")
    @PutMapping()
    private Result updateSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("修改套餐：{}", setmealDTO);
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }

    /**
     * 套餐起售、停售
     * @param status
     * @param id
     * @return
     */
    @Operation(summary = "套餐起售、停售")
    @PostMapping("/status/{status}")
    private Result updateSetmealStatus(@PathVariable Integer status, @RequestParam Long id){
        setmealService.updateSetmealStatus(status, id);
        return Result.success();
    }

    /**
     * 批量删除套餐
     * @param ids
     * @return
     */
    @Operation(summary = "批量删除套餐")
    @DeleteMapping()
    private Result deleteSetmeal(@RequestParam List<Long> ids){
        setmealService.deleteSetmeal(ids);
        return Result.success();
    }
}
