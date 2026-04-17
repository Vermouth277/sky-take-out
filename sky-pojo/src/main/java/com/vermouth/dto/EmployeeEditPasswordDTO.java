package com.vermouth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "修改密码时传递的数据模型")
public class EmployeeEditPasswordDTO implements Serializable {

    @Schema(description = "用户Id")
    private Long empId;

    @Schema(description = "旧密码")
    private String oldPassword;

    @Schema(description = "新密码")
    private String newPassword;

}
