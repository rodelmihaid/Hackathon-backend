package com.pocu.catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SubjectNotFoundException extends BaseException {

    public SubjectNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }

    public SubjectNotFoundException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
