package com.project.tegshop.controller;

import com.project.tegshop.dto.LoginDto;
import com.project.tegshop.dto.RefreshTokenDto;
import com.project.tegshop.dto.UserDto;
import com.project.tegshop.exception.RegisterTokenException;
import com.project.tegshop.exception.UserException;
import com.project.tegshop.model.EmailDetails;
import com.project.tegshop.service.auth.AuthService;
import com.project.tegshop.service.mail.MailService;
import com.project.tegshop.shared.GenericMessage;
import com.project.tegshop.shared.GenericResponse;
import com.project.tegshop.shared.MessageResponse;
import com.project.tegshop.shared.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    private AuthService authService;
    private MailService mailService;

    @Autowired
    public AuthController(AuthService authService, MailService mailService) {
        this.authService = authService;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerHandler(@Valid @RequestBody UserDto userDto,
                                                           final HttpServletRequest request) throws Exception {
        String token = authService.registerUser(userDto);

        EmailDetails emailDetails = new EmailDetails(userDto.getEmailId(),
                GenericMessage.SUBJECT_MAIL,
                "Token verify: \n" + token);
        mailService.sendSimpleMail(emailDetails);

        MessageResponse response = new MessageResponse(GenericMessage.TOKEN_CONFIRM_IS_SENT);
//        GenericResponse response = new GenericResponse(GenericMessage.TOKEN_CONFIRM_IS_SENT, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/verify-registration")
    public ResponseEntity<MessageResponse> verifyRegistrationHandler(@RequestParam("token") String token)
            throws RegisterTokenException {
        String message = authService.verifyRegistration(token);

        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginHandler(@Valid @RequestBody LoginDto loginDto) throws Exception {
        TokenResponse tokenResponse = authService.loginUser(loginDto);

        GenericResponse response = new GenericResponse(GenericMessage.LOGIN_SUCCESSFULLY, tokenResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshTokenHandler(@Valid @RequestBody RefreshTokenDto refreshTokenDto)
            throws AuthenticationException, UserException {
        TokenResponse tokenResponse = authService.refreshToken(refreshTokenDto);

        GenericResponse response = new GenericResponse(GenericMessage.TOKEN_REFRESH, tokenResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
