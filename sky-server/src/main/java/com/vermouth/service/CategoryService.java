package com.vermouth.service;

import com.vermouth.dto.CategoryDTO;
import com.vermouth.dto.CategoryPageQueryDTO;
import com.vermouth.entity.Category;
import com.vermouth.result.PageResult;

import java.util.List;

public interface CategoryService {

    /**
     * 分类分页查询
     * @return
     */
    PageResult page(CategoryPageQueryDTO categoryPageQueryDto);

    /**
     * 禁用、启用分类
     * @param status 1为启用 0为禁用
     * @param id 分类id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 新增分类
     * @param categoryDto
     */
    void add(CategoryDTO categoryDto);

    /**
     * 根据Id删除分类
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    List<Category> getByType(Integer type);

    /**
     * 修改分类
     * @param categoryDto
     */
    void update(CategoryDTO categoryDto);
}
