package com.vermouth.dto;

import com.vermouth.entity.SetmealDish;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "新增套餐传递的数据模型")
public class SetmealDTO {

    private Long id;

    //套餐名称
    private String name;

    //分类id
    private Long categoryId;

    //套餐价格
    private BigDecimal price;

    //图片路径
    private String image;

    //套餐描述
    private String description;

    //售卖状态 1起售 0停售
    private Integer status;

    private List<SetmealDish>  setmealDishes =  new ArrayList<>();
}
