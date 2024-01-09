package com.project.tegshop.controller;

import com.project.tegshop.dto.LoginDto;
import com.project.tegshop.dto.UserDto;
import com.project.tegshop.exception.AuthException;
import com.project.tegshop.exception.RegisterTokenException;
import com.project.tegshop.service.auth.AuthService;
import com.project.tegshop.shared.GenericMessage;
import com.project.tegshop.shared.GenericResponse;
import com.project.tegshop.shared.MessageResponse;
import com.project.tegshop.shared.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<GenericResponse> registerHandler(@Valid @RequestBody UserDto userDto,
                                                           final HttpServletRequest request) throws AuthException {
        String token = authService.registerUser(userDto);
        String url = applicationUrl(request) + "/api/auth/verify-registration?token=" + token;

        GenericResponse response = new GenericResponse(GenericMessage.LINK_CONFIRM_IS_SENT, url);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/verify-registration")
    public ResponseEntity<MessageResponse> verifyRegistrationHandler(@RequestParam("token") String token)
            throws RegisterTokenException {
        String message = authService.verifyRegistration(token);

        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse> loginHandler(@Valid @RequestBody LoginDto loginDto) throws Exception {
        String token = authService.loginUser(loginDto);

        TokenResponse tokenResponse = new TokenResponse(token);
        GenericResponse response = new GenericResponse(GenericMessage.LOGIN_SUCCESSFULLY, tokenResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
