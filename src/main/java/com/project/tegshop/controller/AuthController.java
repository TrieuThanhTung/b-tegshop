package com.project.tegshop.controller;

import com.project.tegshop.dto.LoginDto;
import com.project.tegshop.dto.UserDto;
import com.project.tegshop.exception.RegisterTokenException;
import com.project.tegshop.model.EmailDetails;
import com.project.tegshop.service.auth.AuthService;
import com.project.tegshop.service.mail.MailService;
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
    private AuthService authService;
    private MailService mailService;

    @Autowired
    public AuthController(AuthService authService, MailService mailService) {
        this.authService = authService;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponse> registerHandler(@Valid @RequestBody UserDto userDto,
                                                           final HttpServletRequest request) throws Exception {
        String token = authService.registerUser(userDto);
        String url = applicationUrl(request) + "/api/auth/verify-registration?token=" + token;

        EmailDetails emailDetails = new EmailDetails(userDto.getEmailId(),
                GenericMessage.SUBJECT_MAIL,
                "Link verify: \n" + url);
        mailService.sendSimpleMail(emailDetails);

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
