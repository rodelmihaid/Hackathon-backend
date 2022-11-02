package com.pocu.catalog.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {
    private static final String USER_ALREADY_EXISTS = "User already exists in database!";
    private static final String USER_DOES_NOT_EXIST = "User does not exist!";
    private static final String BAD_CREDENTIALS = "Wrong email or password!";

    @ExceptionHandler(UserAlreadyExistsExceptions.class)
    public ResponseEntity<String> userAlreadyExists() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, USER_ALREADY_EXISTS);
    }
    @ExceptionHandler(UserDoesNotExists.class)
    public ResponseEntity<String> userDoesNotExist() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, USER_DOES_NOT_EXIST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> badCredentialsException() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, BAD_CREDENTIALS);
    }

    private ResponseEntity<String> createHttpResponse(HttpStatus httpStatus, String message) {

//        return new ResponseEntity<>(HttpResponse.builder().httpStatus(httpStatus)
//
//                .httpStatusCode(httpStatus.value())
//
//                .reason(httpStatus.getReasonPhrase().toUpperCase())
//
//                .message(SecurityConstant.ACCESS_DENIED_MESSAGE)
//
//                .build(), httpStatus);
        return new ResponseEntity<>(message, httpStatus);

    }
}
