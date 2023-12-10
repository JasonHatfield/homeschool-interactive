package com.jasonhatfield.homeschoolinteractive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class is a global exception handler for handling runtime exceptions and illegal argument exceptions.
 * It provides methods to handle different types of exceptions and return appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles runtime exceptions and returns an HTTP response with status code 500 (Internal Server Error).
     * The response body contains the error message from the exception.
     *
     * @param ex The runtime exception to be handled.
     * @return An HTTP response entity with status code 500 and the error message.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error: " + ex.getMessage());
    }

    /**
     * Handles illegal argument exceptions and returns an HTTP response with status code 400 (Bad Request).
     * The response body contains the error message from the exception.
     *
     * @param ex The illegal argument exception to be handled.
     * @return An HTTP response entity with status code 400 and the error message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Bad request: " + ex.getMessage());
    }

}
