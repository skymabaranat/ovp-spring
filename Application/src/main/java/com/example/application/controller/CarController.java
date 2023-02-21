package com.example.application.controller;

import com.example.application.entities.Car;
import com.example.application.entities.CarDTO;
import com.example.application.exceptions.CarAlreadyExistsException;
import com.example.application.exceptions.EmptyListException;
import com.example.application.repository.CarRepository;
import com.example.application.service.CarService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
@Validated
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

//    private CarDTO convertToDto(Car car) {
//        return this.modelMapper.map(car, CarDTO.class);
//    }
    @PostMapping("/admin")
    public ResponseEntity<Map<String, String>> saveCar(@RequestBody List<@Valid CarDTO> cardto) throws CarAlreadyExistsException {
        if (!cardto.isEmpty()) {
            carService.addCar(cardto);
            return new ResponseEntity<>(Map.of("description", "Database updated"), HttpStatus.CREATED);
        } throw new EmptyListException();
    }

//    @GetMapping("/admin")
//    @ResponseBody
//    public List<CarDTO> getCars() {
//        List<CarDTO> cars = carService.getAllCars();
//        return cars;

    @GetMapping("/admin/{id}")
    @ResponseBody
    public Optional<Car> getCars(@PathVariable("id") String id) {
        Optional<Car> car = carService.getCarByID(id);
        return car;
    }

    @GetMapping("/admin")
    @ResponseBody
    public ResponseEntity<List<CarDTO>> getCars() {
        List<CarDTO> cars = carService.getAllCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
}
