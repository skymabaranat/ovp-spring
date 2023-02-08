package com.example.application.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class StatusControllerTest {
    @InjectMocks
    StatusController statusController;
    @Test
    public void getStatus_returnsOK(){
        ResponseEntity response = statusController.getStatus();
        Assertions.assertAll(
                () -> Assertions.assertEquals("OK", response.getBody()),
                () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }
}
