package com.vermouth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vermouth.constant.MessageConstant;
import com.vermouth.constant.PasswordConstant;
import com.vermouth.constant.StatusConstant;
import com.vermouth.context.BaseContext;
import com.vermouth.dto.EmployeeDTO;
import com.vermouth.dto.EmployeeEditPasswordDTO;
import com.vermouth.dto.EmployeeLoginDTO;
import com.vermouth.dto.EmployeePageQueryDTO;
import com.vermouth.entity.Employee;
import com.vermouth.exception.AccountLockedException;
import com.vermouth.exception.AccountNotFoundException;
import com.vermouth.exception.PasswordErrorException;
import com.vermouth.mapper.EmployeeMapper;
import com.vermouth.result.PageResult;
import com.vermouth.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 新增员工
     * @param employeeDTO
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        //对象属性拷贝
        BeanUtils.copyProperties(employeeDTO, employee);

        //设置账号状态，默认正常状态 1正常 0锁定
        employee.setStatus(StatusConstant.ENABLE);

        //设置默认密码
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        //设置记录当前创建时间和修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //设置当前记录创建人id和修改人id
        Long empId = BaseContext.getCurrentId();
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

            employeeMapper.insert(employee);
    }


    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return
     */
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        Long total = page.getTotal();
        List<Employee> employees = page.getResult();
        return new PageResult(total, employees);
    }

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        employeeMapper.update(employee);
    }

    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        employee.setPassword("******");
        return employee;
    }

    @Override
    public void update(EmployeeDTO employeeDTO) {
       Employee employee = new Employee();
       BeanUtils.copyProperties(employeeDTO, employee);

       employee.setUpdateTime(LocalDateTime.now());
       employee.setUpdateUser(BaseContext.getCurrentId());
       employeeMapper.update(employee);
    }

    @Override
    public void editPassword(EmployeeEditPasswordDTO employeeEditPasswordDTO) {
        Long empId = employeeEditPasswordDTO.getEmpId();
        String oldPassword = employeeEditPasswordDTO.getOldPassword();
        String newPassword = employeeEditPasswordDTO.getNewPassword();

        Employee employee = employeeMapper.getById(empId);

        //对比密码
        if (!employee.getPassword().equals(DigestUtils.md5DigestAsHex(oldPassword.getBytes()))) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        Employee updateEmployee = Employee.builder()
                .id(empId)
                .password(DigestUtils.md5DigestAsHex(newPassword.getBytes()))
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        employeeMapper.update(updateEmployee);
    }
}
