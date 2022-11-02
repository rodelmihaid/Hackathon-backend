package com.pocu.catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProjectStudentNotFoundException extends BaseException {
    public ProjectStudentNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ProjectStudentNotFoundException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}

