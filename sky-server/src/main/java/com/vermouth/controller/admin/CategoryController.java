package com.vermouth.controller.admin;

import com.vermouth.dto.CategoryDTO;
import com.vermouth.dto.CategoryPageQueryDTO;
import com.vermouth.entity.Category;
import com.vermouth.result.PageResult;
import com.vermouth.result.Result;
import com.vermouth.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Tag(name = "菜品分类管理", description = "菜品分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "分类分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(@ParameterObject CategoryPageQueryDTO categoryPageQueryDto) {
        log.info("分类分页查询：{}",categoryPageQueryDto);
        PageResult pageResult = categoryService.page(categoryPageQueryDto);
        return Result.success(pageResult);
    }

    @Operation(summary = "根据类型查询分类")
    @GetMapping("/list")
    public Result<List<Category>> getByType(Integer type) {
        log.info("根据类型查询分类：{}",type);
        List<Category> categories = categoryService.getByType(type);
        return Result.success(categories);
    }

    @Operation(summary = "禁用、启用分类")
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable("status") Integer status, Long id) {
        log.info("禁用、启用分类：{} {}",status,id);
        categoryService.startOrStop(status, id);
        return Result.success();
    }

    @Operation(summary = "新增分类")
    @PostMapping
    public Result add(@RequestBody CategoryDTO categoryDto) {
        log.info("新增分类：{}",categoryDto);
        categoryService.add(categoryDto);
        return Result.success();
    }

    @Operation(summary = "根据Id删除分类")
    @DeleteMapping
    public Result deleteById(@RequestParam("id") Long id) {
        log.info("根据Id删除分类: {}",id);
        categoryService.deleteById(id);
        return Result.success();
    }

    @Operation(summary = "修改分类")
    @PutMapping
    public Result update(@RequestBody CategoryDTO categoryDto) {
        log.info("修改分类: {}",categoryDto);
        categoryService.update(categoryDto);
        return Result.success();
    }

}
