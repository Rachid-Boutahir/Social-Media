package com.simplon.exception;

import org.springframework.http.HttpStatus;
/**
 * This class is used to create a custom exception.
 * It contains the status and the error message.
 */
public class CustomNotFoundException extends RuntimeException {

    private final HttpStatus status;
    public CustomNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}