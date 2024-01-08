package com.project.tegshop.controller;

import com.project.tegshop.dto.LoginDto;
import com.project.tegshop.dto.UserDto;
import com.project.tegshop.exception.AuthException;
import com.project.tegshop.model.UserEntity;
import com.project.tegshop.service.auth.AuthService;
import com.project.tegshop.shared.GenericMessage;
import com.project.tegshop.shared.GenericResponse;
import com.project.tegshop.shared.MessageResponse;
import com.project.tegshop.shared.response.TokenResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse> registerHandler(@Valid @RequestBody UserDto userDto) throws AuthException {

        UserEntity user = authService.registerUser(userDto);

        GenericResponse response = new GenericResponse(GenericMessage.REGISTER_SUCCESSFULLY, userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse> loginHandler(@Valid @RequestBody LoginDto loginDto) throws Exception {
        String token = authService.loginUser(loginDto);

        TokenResponse tokenResponse = new TokenResponse(token);
        GenericResponse response = new GenericResponse(GenericMessage.LOGIN_SUCCESSFULLY, tokenResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/hello")
    public ResponseEntity<GenericResponse> helloWorld(@Valid @RequestBody LoginDto loginDto) {
        System.out.println("abc231231");
        return new ResponseEntity<>(new GenericResponse("why not accept request body: " + loginDto.getEmailId(), loginDto), HttpStatus.OK);
    }
}
