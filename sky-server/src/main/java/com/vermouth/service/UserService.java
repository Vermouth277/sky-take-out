package com.vermouth.service;

import com.vermouth.dto.UserLoginDTO;
import com.vermouth.entity.User;

public interface UserService {

    User wxLogin(UserLoginDTO userLoginDTO);
}
