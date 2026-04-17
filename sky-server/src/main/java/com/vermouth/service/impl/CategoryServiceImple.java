package com.vermouth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vermouth.context.BaseContext;
import com.vermouth.dto.CategoryDTO;
import com.vermouth.dto.CategoryPageQueryDTO;
import com.vermouth.entity.Category;
import com.vermouth.mapper.CategoryMapper;
import com.vermouth.result.PageResult;
import com.vermouth.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public void startOrStop(Integer status, Long id) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();

        categoryMapper.update(category);
    }

    @Override
    public void add(CategoryDTO categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);

        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());

        category.setCreateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());

        categoryMapper.inster(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryMapper.deltetById(id);
    }

    @Override
    public List<Category> getByType(Integer type) {
        List<Category> categories =  categoryMapper.list(type);
        return categories;
    }

    @Override
    public void update(CategoryDTO categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }


}
