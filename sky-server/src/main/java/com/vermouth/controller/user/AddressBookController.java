package com.vermouth.controller.user;

import com.vermouth.entity.AddressBook;
import com.vermouth.result.Result;
import com.vermouth.service.AddressBookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @Operation(summary = "新增地址")
    @PostMapping
    public Result<String> add(@RequestBody AddressBook addressBook) {
        log.info("新增地址：{}", addressBook);
        addressBookService.add(addressBook);
        return Result.success();
    }

    @Operation(summary = "查询当前登录用户的所有地址信息")
    @GetMapping("/list")
    public Result<List<AddressBook>> getList() {
        log.info("查询当前登录用户的所有地址信息...");
        List<AddressBook> list = addressBookService.list();
        return Result.success(list);
    }

    @Operation(summary = "查询默认地址")
    @GetMapping("/default")
    public Result<AddressBook> getDefault() {
        log.info("查询默认地址...");
        AddressBook addressBook = addressBookService.getDefault();
        return Result.success(addressBook);
    }

    @Operation(summary = "根据id修改地址")
    @PutMapping
    public Result<String> update(@RequestBody AddressBook addressBook) {
        log.info("根据id修改地址：{}", addressBook);
        addressBookService.update(addressBook);
        return Result.success();
    }

    @Operation(summary = "根据id删除地址")
    @DeleteMapping("/")
    public Result<String> deleteById(Long id) {
        log.info("根据id删除地址：{}", id);
        addressBookService.deleteById(id);
        return Result.success();
    }

    @Operation(summary = "根据id查询地址")
    @GetMapping("/{id}")
    public Result<AddressBook> getById(@PathVariable("id") Long id) {
        log.info("根据id查询地址：{}", id);
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    @Operation(summary = "设置默认地址")
    @PutMapping("/default")
    public Result<String> setDefault(@RequestBody AddressBook addressBook) {
        log.info("设置默认地址：{}", addressBook);
        addressBookService.setDefault(addressBook.getId());
        return Result.success();
    }

}
