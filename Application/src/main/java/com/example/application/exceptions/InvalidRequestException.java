package com.example.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {
    private Map<String, String> message = Map.of("description", "Incorrect query parameter provided");

    public void InvalidRequestException(Map<String, String> message) {
        this.message = message;
    }

    public Map<String, String> getCurrentMessage() {
        return message;
    }
}

