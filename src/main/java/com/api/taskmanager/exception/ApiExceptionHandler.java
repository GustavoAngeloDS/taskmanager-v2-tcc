package com.api.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleApiExceptions(Exception exception) {
        ApiException apiException = new ApiException(exception.getMessage(), BAD_REQUEST, ZonedDateTime.now());

        return new ResponseEntity<>(apiException, BAD_REQUEST);
    }
}
