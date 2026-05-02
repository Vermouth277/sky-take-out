package com.vermouth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.vermouth.constant.MessageConstant;
import com.vermouth.dto.UserLoginDTO;
import com.vermouth.entity.User;
import com.vermouth.exception.LoginFailedException;
import com.vermouth.mapper.UserMapper;
import com.vermouth.properties.WeChatProperties;
import com.vermouth.service.UserService;
import com.vermouth.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatPropertiesb;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());

        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        User user = userMapper.getByOpenid(openid);

        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        return user;
    }

    private String getOpenid(String code) {
        Map<String,String> map = new HashMap<>();
        map.put("appid", weChatPropertiesb.getAppid());
        map.put("secret", weChatPropertiesb.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, map);

        JSONObject jsonObject = JSONObject.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }

}
