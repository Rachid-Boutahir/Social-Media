package com.simplon.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * This class is used to handle the custom exceptions.
 * It contains the method handleCustomNotFoundException.
 */
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomNotFoundException(CustomNotFoundException ex) {
        CustomErrorResponse response = new CustomErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(ex.getStatus());
        response.setError(ex.getMessage());

        return new ResponseEntity<>(response, ex.getStatus());
    }
}