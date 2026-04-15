package com.vermouth.service;

import com.vermouth.dto.EmployeeLoginDTO;
import com.vermouth.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);
}
