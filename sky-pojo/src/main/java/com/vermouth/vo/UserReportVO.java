package com.vermouth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReportVO implements Serializable {

    //日期列表，以逗号分隔 例如：2022-05-01,2022-05-31
    private String dateList;

    //新增用户数列表，以逗号分隔 例如：22,23,34
    private String newUserList;

    //总用户量列表，以逗号分隔 例如：200,210,220
    private String totalUserList;
}
