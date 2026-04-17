package com.vermouth.controller.admin;

import com.vermouth.constant.JwtClaimsConstant;
import com.vermouth.dto.EmployeeDTO;
import com.vermouth.dto.EmployeeEditPasswordDTO;
import com.vermouth.dto.EmployeeLoginDTO;
import com.vermouth.dto.EmployeePageQueryDTO;
import com.vermouth.entity.Employee;
import com.vermouth.properties.JwtProperties;
import com.vermouth.result.PageResult;
import com.vermouth.result.Result;
import com.vermouth.service.EmployeeService;
import com.vermouth.vo.EmployeeLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.vermouth.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Tag(name = "员工管理", description = "员工登录、退出相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     * @param employeeLoginDTO
     * @return
     */
    @Operation(summary = "员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录: {}",employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID,employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出登录
     * @return
     */
    @Operation(summary = "员工退出")
    @PostMapping("/logout")
    public Result<String> logout() { return Result.success(); }


    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    @Operation(summary = "新增员工")
    @PostMapping
    public Result<String> save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工: {}",employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }


    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Operation(summary = "员工分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(@ParameterObject EmployeePageQueryDTO  employeePageQueryDTO) {
        log.info("员工分页查询: {}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     * @return
     */
    @Operation(summary = "启用禁用员工账号")
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status,Long id) {
        log.info("启用禁用员工账号: {},{}",status,id);
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询员工")
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工：{}",id);
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    @Operation(summary = "编辑员工信息")
    @PutMapping()
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("编辑员工信息：{}",employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }

    @Operation(summary = "修改密码")
    @PutMapping("/editPassword")
    public Result editPassword(
            @RequestBody EmployeeEditPasswordDTO employeeEditPasswordDTO) {
        log.info("修改密码：{}",employeeEditPasswordDTO);
        employeeService.editPassword(employeeEditPasswordDTO);
        return Result.success();
    }
}
