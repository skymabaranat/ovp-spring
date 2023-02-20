package com.example.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EmptyListException extends RuntimeException {
    private Map<String, String> message = Map.of("description", "Empty List");

    public void EmptyListException(Map<String, String> message) {
        this.message = message;
    }

    public Map<String, String> getCurrentMessage() {
        return message;
    }
}
