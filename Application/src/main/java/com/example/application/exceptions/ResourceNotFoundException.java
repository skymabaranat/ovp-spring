package com.example.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceNotFoundException extends RuntimeException {

    private String message = "Resource not found";

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public String getCurrentMessage() {
        return message;
    }

    public ResourceNotFoundException(){

    }

}
