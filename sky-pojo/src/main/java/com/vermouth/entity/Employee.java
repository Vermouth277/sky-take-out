package com.vermouth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    private Long id;

    //姓名
    private String name;

    //用户名
    private String username;

    //密码
    private String password;

    //手机号
    private String phone;

    //性别
    private String sex;

    //身份证号
    private String idNumber;

    //账号状态 1正常 0锁定
    private Integer status;

    //创建时间
    private LocalDateTime createTime;

    //最后修改时间
    private LocalDateTime updateTime;

    //创建人id
    private Long createUser;

    //最后修改人id
    private Long updateUser;
}
