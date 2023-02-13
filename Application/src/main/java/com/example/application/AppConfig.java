package com.example.application;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;
@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
