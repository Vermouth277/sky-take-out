package com.vermouth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "菜品分类分页查询传递的数据模型")
public class CategoryPageQueryDTO implements Serializable {

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "页码")
    private Integer page;

    @Schema(description = "每页记录数")
    private Integer pageSize;

    @Schema(description = "分类类型")
    private String type;
}
