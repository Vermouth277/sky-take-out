package com.vermouth.interceptor;

import com.vermouth.constant.JwtClaimsConstant;
import com.vermouth.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.vermouth.utils.JwtUtils;

@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        // 从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        // 校验令牌
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtils.parseJWT(jwtProperties.getAdminTokenName(), token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
            log.info("当前员工id：",empId);

            // 放行
            return true;
        } catch (Exception e) {
            // 校验失败，响应401状态码
            response.setStatus(401);
            return false;
        }
    }
}
