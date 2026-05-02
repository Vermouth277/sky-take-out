package com.vermouth.controller.user;

import com.vermouth.dto.ShoppingCartDTO;
import com.vermouth.entity.ShoppingCart;
import com.vermouth.result.Result;
import com.vermouth.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Operation(summary = "添加购物车")
    @PostMapping("/add")
    public Result<String> addShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车: {}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    @Operation(summary = "查看购物车")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> listShoppingCart() {
        log.info("查看购物车");
        List<ShoppingCart> list = shoppingCartService.list();
        return Result.success(list);
    }

    @Operation(summary = "清空购物车")
    @DeleteMapping("/clean")
    public Result<String> cleanShoppingCart() {
        log.info("清空购物车");
        shoppingCartService.cleanShoppingCart();
        return Result.success();
    }

    @Operation(summary = "删除购物车中一个商品")
    @PostMapping("/sub")
    public Result<String> subShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("删除购物车中一个商品: {}", shoppingCartDTO);
        shoppingCartService.subShoppingCart(shoppingCartDTO);
        return Result.success();
    }

}
