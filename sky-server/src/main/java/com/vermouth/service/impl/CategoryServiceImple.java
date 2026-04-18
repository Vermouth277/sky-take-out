package com.vermouth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vermouth.dto.CategoryDTO;
import com.vermouth.dto.CategoryPageQueryDTO;
import com.vermouth.entity.Category;
import com.vermouth.mapper.CategoryMapper;
import com.vermouth.result.PageResult;
import com.vermouth.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImple implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 分类分页查询
     * @return
     */
    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDto) {
        PageHelper.startPage(categoryPageQueryDto.getPage(), categoryPageQueryDto.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDto);
        Long total = page.getTotal();
        List<Category> categories = page.getResult();
        return new PageResult(total, categories);
    }

    /**
     * 启用、禁用分类
     * @param status 1为启用 0为禁用
     * @param id 分类id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .build();

        categoryMapper.update(category);
    }

    /**
     * 新增分类
     * @param categoryDto
     */
    @Override
    public void add(CategoryDTO categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);

        category.setStatus(0);

        categoryMapper.inster(category);
    }

    /**
     * 根据id删除分类
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        categoryMapper.deltetById(id);
    }

    /**
     *  根据类型查询分类
     * @param type
     * @return
     */
    @Override
    public List<Category> getByType(Integer type) {
        List<Category> categories =  categoryMapper.list(type);
        return categories;
    }

    /**
     * 修改分类
     * @param categoryDto
     */
    @Override
    public void update(CategoryDTO categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        categoryMapper.update(category);
    }


}
