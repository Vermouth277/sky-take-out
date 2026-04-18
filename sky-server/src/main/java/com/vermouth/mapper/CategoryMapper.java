package com.vermouth.mapper;

import com.github.pagehelper.Page;
import com.vermouth.annotation.AutoFill;
import com.vermouth.dto.CategoryPageQueryDTO;
import com.vermouth.entity.Category;
import com.vermouth.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 分类分页查询
     * @param categoryPageQueryDto
     * @return
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDto);

    /**
     * 更新
     * @param category
     */
    @AutoFill(OperationType.UPDATE)
    void update(Category category);

    /**
     * 新增
     * @param category
     */
    @AutoFill(OperationType.INSERT)
    @Insert("insert into category (name, type, sort, status, create_time, update_time, create_user, update_user)" +
            "values " +
            "(#{name},#{type},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void inster(Category category);

    /**
     * 根据Id删除
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void deltetById(Long id);

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    List<Category> list(Integer type);
}
