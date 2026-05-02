package com.vermouth.controller.user;

import com.vermouth.entity.Category;
import com.vermouth.result.Result;
import com.vermouth.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Slf4j
@Tag(name = "菜品分类管理", description = "菜品分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "条件查询")
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type) {
        List<Category> list = categoryService.getByType(type);
        return Result.success(list);
    }
}
