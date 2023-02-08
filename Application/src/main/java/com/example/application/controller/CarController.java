package com.example.application.controller;

import com.example.application.entities.Car;
import com.example.application.repository.CarRepository;
import com.example.application.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/private")
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private CarRepository carRepository;

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/cars/admin")
    public ResponseEntity<Map<String, String>> saveCar(@RequestBody Car car) {
        carRepository.save(car);
        return new ResponseEntity<>(Map.of("description", "Database updated"), HttpStatus.CREATED);
    }

    @GetMapping("/cars/admin")
    public List<Car> getCars () {
        return carRepository.findAll();
    }
}
