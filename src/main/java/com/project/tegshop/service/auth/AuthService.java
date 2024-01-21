package com.project.tegshop.service.auth;

import com.project.tegshop.dto.LoginDto;
import com.project.tegshop.dto.RefreshTokenDto;
import com.project.tegshop.dto.UserDto;
import com.project.tegshop.exception.UserException;
import com.project.tegshop.exception.RegisterTokenException;
import com.project.tegshop.shared.response.TokenResponse;
import org.apache.tomcat.websocket.AuthenticationException;

public interface AuthService {
    String registerUser(UserDto userDto) throws UserException, RegisterTokenException;
    String verifyRegistration(String token) throws RegisterTokenException;

    TokenResponse loginUser(LoginDto loginDto) throws Exception;
    TokenResponse refreshToken(RefreshTokenDto refreshTokenDto) throws AuthenticationException, UserException;
}
