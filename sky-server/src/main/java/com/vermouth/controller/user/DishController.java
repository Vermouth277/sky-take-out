package com.vermouth.controller.user;

import com.vermouth.constant.StatusConstant;
import com.vermouth.entity.Dish;
import com.vermouth.result.Result;
import com.vermouth.service.DishService;
import com.vermouth.vo.DishVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.vermouth.constant.RedisConstant.CACHE_DISH_KEY;
import static com.vermouth.constant.RedisConstant.CACHE_DISH_TTL;


@RestController("userDishController")
@RequestMapping("/user/dish")
@Tag(name = "菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;


    @Operation(summary = "根据分类id查询菜品")
    @GetMapping("/list")
    public Result<List<DishVO>> list(@RequestParam Long categoryId) {
        log.info("C端-根据分类id查询菜品：{}", categoryId);

        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);

        List<DishVO> list = dishService.listWithFlavor(dish);
        return Result.success(list);
    }
}
