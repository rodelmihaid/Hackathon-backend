package com.pocu.catalog.exception;

public class UserDoesNotExists extends RuntimeException{
    public UserDoesNotExists(String message) {
        super(message);
    }
}
