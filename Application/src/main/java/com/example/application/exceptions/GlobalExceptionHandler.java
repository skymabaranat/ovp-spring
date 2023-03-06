package com.example.application.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException resourceNotFoundException, HttpServletRequest request) {
        return new ResponseEntity(resourceNotFoundException.getCurrentMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        return new ResponseEntity<>(Map.of("description","Incorrect car data provided"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String,String>> malformedAttributeException(HttpMessageNotReadableException e, HttpServletRequest request) {
        return new ResponseEntity<>(Map.of("description","Incorrect car data provided"), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = CarAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleCarAlreadyExistsException(CarAlreadyExistsException carAlreadyExistsException) {
        return new ResponseEntity(Map.of("description","Car already exists"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = EmptyListException.class)
    public ResponseEntity<Map<String, String>> handleEmptyListExistsException(EmptyListException emptyListException) {
        return new ResponseEntity(emptyListException.getCurrentMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidRequestException.class)
    public ResponseEntity<Map<String, String>> handleInvalidRequestException(InvalidRequestException invalidRequestException) {
        return new ResponseEntity(invalidRequestException.getCurrentMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        return new ResponseEntity(Map.of("description","Incorrect query parameter provided"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        return new ResponseEntity(Map.of("description","Incorrect car data provided"), HttpStatus.BAD_REQUEST);
    }


}
