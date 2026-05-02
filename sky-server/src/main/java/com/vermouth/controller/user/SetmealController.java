package com.vermouth.controller.user;

import com.vermouth.constant.StatusConstant;
import com.vermouth.entity.Setmeal;
import com.vermouth.result.Result;
import com.vermouth.service.SetmealService;
import com.vermouth.vo.DishItemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetmealController")
@Slf4j
@RequestMapping("/user/setmeal")
@Tag(name = "套餐相关接口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Operation(summary = "根据分类id查询套餐")
    @GetMapping("list")
    public Result<List<Setmeal>> getSetmealList( Long categoryId) {
        log.info("C端-根据分类查套餐：{}", categoryId);
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);

        List<Setmeal> setmeals = setmealService.getByCategoryId(setmeal);
        return Result.success(setmeals);
    }

    @Operation(summary = "根据套餐id查询包含的菜品")
    @GetMapping("dish/{id}")
    public Result<List<DishItemVO>> dishList(@PathVariable("id") Long id){
        log.info("C端-根据套餐id查询包含的菜品：{}", id);
        List<DishItemVO> list = setmealService.getDishItemById(id);
        return Result.success(list);
    };
}
