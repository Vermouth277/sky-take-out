package com.vermouth.service.impl;

import com.vermouth.constant.MessageConstant;
import com.vermouth.constant.StatusConstant;
import com.vermouth.dto.EmployeeLoginDTO;
import com.vermouth.entity.Employee;
import com.vermouth.exception.AccountLockedException;
import com.vermouth.exception.AccountNotFoundException;
import com.vermouth.exception.PasswordErrorException;
import com.vermouth.mapper.EmployeeMapper;
import com.vermouth.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        Employee employee = employeeMapper.getEmployeeByUsername(username);

        // 各种异常处理，
        //用户不存在
        if (employee == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码对比
        // md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //账号被锁定
        if (employee.getStatus().equals(StatusConstant.DISABLE)) {
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        return  employee;
    }
}
