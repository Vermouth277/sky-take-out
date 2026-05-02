package com.vermouth.controller.user;

import com.vermouth.constant.JwtClaimsConstant;
import com.vermouth.dto.UserLoginDTO;
import com.vermouth.entity.User;
import com.vermouth.properties.JwtProperties;
import com.vermouth.result.Result;
import com.vermouth.service.UserService;
import com.vermouth.utils.JwtUtil;
import com.vermouth.vo.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Operation( summary = "登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("微信用户登陆：{}", userLoginDTO);

        User user = userService.wxLogin(userLoginDTO);
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }

}
