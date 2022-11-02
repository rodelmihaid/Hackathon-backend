package com.pocu.catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class StudentAlreadyEnrolledException extends BaseException {

    public StudentAlreadyEnrolledException(String message) {
        super(message, "STUDENT_ALREADY_ENROLLED");
    }

    public StudentAlreadyEnrolledException(String message, String errorCode) {
        super(message, errorCode);
    }

    public StudentAlreadyEnrolledException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
