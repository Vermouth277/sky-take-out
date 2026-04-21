package com.vermouth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "套餐分页查询传递的数据模型")
public class SetmealPageQueryDTO implements Serializable {

    @Schema(description = "分类id")
    private Long categoryId;

    @Schema(description = "套餐查询名")
    private String name;

    @Schema(description = "页码")
    private Integer page;

    @Schema(description = "每页记录数")
    private Integer pageSize;

    @Schema(description = "套餐起售状态")
    private Integer status;
}
