package com.example.application.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CarService {
    @Autowired
    private RestTemplate template = new RestTemplate();

    public String generateString() {
        return "OK";
    }
}
