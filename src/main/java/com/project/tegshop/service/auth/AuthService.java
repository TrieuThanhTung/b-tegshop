package com.project.tegshop.service.auth;

import com.project.tegshop.dto.LoginDto;
import com.project.tegshop.dto.UserDto;
import com.project.tegshop.exception.AuthException;
import com.project.tegshop.model.UserEntity;

public interface AuthService {
    UserEntity registerUser(UserDto userDto) throws AuthException;

    String loginUser(LoginDto loginDto) throws Exception;
}
