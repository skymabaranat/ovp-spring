package com.example.functional.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.annotation.DirtiesContext;

@CucumberContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CucumberConfig {
}
