package com.example.application.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingMatrixVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setStatus(404);
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, null, 404);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        Error error = new Error();
        error.setMessage("Incorrect car data provided");
        error.setStatus(400);
        return new ResponseEntity<>(error, null, 400);
    }
}
