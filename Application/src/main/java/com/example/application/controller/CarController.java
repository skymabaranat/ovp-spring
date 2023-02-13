package com.example.application.controller;

import com.example.application.entities.Car;
import com.example.application.entities.CarDTO;
import com.example.application.repository.CarRepository;
import com.example.application.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private ModelMapper modelMapper;


    public CarController (CarService carService, ModelMapper modelMapper){
        super();
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    private CarDTO convertToDto(Car car) {
        return this.modelMapper.map(car, CarDTO.class);
    }
    @PostMapping("/admin")
    public ResponseEntity<Map<String, String>> saveCar(@RequestBody List<CarDTO> cardto) {
        carService.addCar(cardto);
        return new ResponseEntity<>(Map.of("description", "Database updated"), HttpStatus.CREATED);
    }

    @GetMapping("/cars/admin")
    @ResponseBody
    public List<CarDTO> getCars() {
        List<Car> cars = carService.getAllCars();
        return cars.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
