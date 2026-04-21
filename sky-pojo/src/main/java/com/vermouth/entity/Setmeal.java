package com.vermouth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Setmeal implements Serializable {
    private static final long serialVersionUID = 1L;

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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
