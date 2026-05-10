package com.vermouth.service.impl;

import com.vermouth.context.BaseContext;
import com.vermouth.entity.AddressBook;
import com.vermouth.mapper.AddressBookMapper;
import com.vermouth.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;


    /**
     * 新增地址
     * @param addressBook
     */
    @Override
    public void add(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBookMapper.insert(addressBook);
    }

    /**
     * 查询当前登录用户的所有地址信息
     * @return
     */
    @Override
    public List<AddressBook> list() {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        return addressBookMapper.list(addressBook);
    }

    /**
     * 查询默认地址
     * @return
     */
    @Override
    public AddressBook getDefault() {
        AddressBook addressBook = AddressBook.builder()
                .isDefault(1)
                .userId(BaseContext.getCurrentId())
                .build();
        return addressBookMapper.list(addressBook).get(0);
    }

    /**
     * 根据id修改地址
     * @param addressBook
     */
    @Override
    public void update(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.update(addressBook);
    }

    /**
     * 根据id删除地址
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        addressBookMapper.delete(id);
    }

    /**
     * 根据id查询地址
     * @return
     */
    @Override
    public AddressBook getById(Long id) {
        return addressBookMapper.selectById(id);
    }

    /**
     * 设置默认地址
     * @param id
     */
    @Transactional
    @Override
    public void setDefault(Long id) {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(1);
        List<AddressBook> list = addressBookMapper.list(addressBook);

        if (list != null && !list.isEmpty()) {
            list.forEach(item -> {
                item.setIsDefault(0);
                addressBookMapper.update(item);
            });
        }

        addressBook = addressBookMapper.selectById(id);
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }
}
