package com.example.application.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        Error error = new Error();
//        error.setMessage(e.getMessage());
//        error.setStatus(404);
//        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, null, 404);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        return new ResponseEntity<>(Map.of("description","Incorrect car data provided"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String,String>> malformedAttributeException(HttpMessageNotReadableException e, HttpServletRequest request) {
        return new ResponseEntity<>(Map.of("description","Incorrect car data provided"), HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler({IllegalArgumentException.class, IncorrectResultSizeDataAccessException.class})
//    public ResponseEntity<Map<String,String>> carAlreadyExistsException(IllegalArgumentException e, IncorrectResultSizeDataAccessException a, HttpServletRequest request) {
//        return new ResponseEntity<>(Map.of("description","Incorrect car data provided"), HttpStatus.BAD_REQUEST);
//    }
    @ExceptionHandler(value = CarAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleCarAlreadyExistsException(CarAlreadyExistsException carAlreadyExistsException) {
        return new ResponseEntity(Map.of("description","Car already exists"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = EmptyListException.class)
    public ResponseEntity<Map<String, String>> handleEmptyListExistsException(EmptyListException emptyListException) {
        return new ResponseEntity(emptyListException.getCurrentMessage(), HttpStatus.BAD_REQUEST);
    }



}
