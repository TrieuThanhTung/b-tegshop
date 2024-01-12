package com.project.tegshop.exception;

import com.project.tegshop.shared.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> usernameNotFoundExceptionHandler(UsernameNotFoundException exception,
                                                                         WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> authenticationExceptionHandler(AuthenticationException exception,
                                                                         WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> requestBodyValidExceptionHandler(MethodArgumentNotValidException exception,
                                                             WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getBindingResult().getFieldError().getDefaultMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorMessage> UserExceptionHandler(UserException exception,
                                                             WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundExceptionHandler(UserNotFoundException exception,
                                                             WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegisterTokenException.class)
    public ResponseEntity<ErrorMessage> registerNotFoundExceptionHandler(RegisterTokenException exception,
                                                                         WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorMessage> productExceptionHandler(ProductException exception,
                                                                         WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessage> productExceptionHandler(ProductNotFoundException exception,
                                                                WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartItemException.class)
    public ResponseEntity<ErrorMessage> cartItemExceptionHandler(CartItemException exception,
                                                                WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ErrorMessage> cartItemNotFoundExceptionHandler(CartItemNotFoundException exception,
                                                                 WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
