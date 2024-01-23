package com.project.tegshop.exception;

import com.project.tegshop.shared.ErrorMessage;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
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

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorMessage> SQLExceptionHandler(SQLException exception,
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> UserExceptionHandler(UserException exception,
                                                             WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
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

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorMessage> addressNotFoundExceptionHandler(AddressNotFoundException exception,
                                                                         WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorMessage> orderNotFoundExceptionHandler(OrderNotFoundException exception,
                                                                        WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ErrorMessage> orderExceptionHandler(OrderException exception,
                                                                      WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
