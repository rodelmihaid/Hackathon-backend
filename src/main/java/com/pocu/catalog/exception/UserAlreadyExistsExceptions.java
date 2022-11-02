package com.pocu.catalog.exception;

public class UserAlreadyExistsExceptions extends RuntimeException{
    public UserAlreadyExistsExceptions(String message) {
        super(message);
    }
}
