package com.vermouth.mapper;

import com.vermouth.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    /**
     * 新增
     * @param addressBook
     */
    void insert(AddressBook addressBook);

    /**
     * 动态查询
     * @param addressBook
     * @return
     */
    List<AddressBook> list(AddressBook addressBook);

    @Select("select * from address_book where id = #{id}")
    AddressBook selectById(Long id);

    /**
     * 动态修改
     * @param addressBook
     */
    void update(AddressBook addressBook);

    @Delete("delete from address_book where id = #{id}")
    void delete(Long id);
}
