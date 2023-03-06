package com.example.application.controller;

import com.example.application.entities.CarDTO;
import com.example.application.exceptions.CarAlreadyExistsException;
import com.example.application.exceptions.EmptyListException;
import com.example.application.exceptions.InvalidRequestException;
import com.example.application.service.CarService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cars")
@Validated
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private ModelMapper modelMapper;


    public CarController (CarService carService){
        super();
        this.carService = carService;
//        this.modelMapper = modelMapper;
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
//    public ResponseEntity<List<CarDTO>> getCars() {
//        List<CarDTO> cars = carService.getAllCars();
//        return new ResponseEntity<>(cars, HttpStatus.OK);
//    }

//    @GetMapping("/admin/{id}")
//    @ResponseBody
//    public Optional<Car> getCars(@PathVariable("id") String id) {
//        Optional<Car> car = carService.getCarByID(id);
//        return car;
//    }

    @GetMapping("/adminbrand")
    @ResponseBody
    public ResponseEntity<List<CarDTO>> getCarsByBrand(@RequestParam(required = false) String brand){
        List<CarDTO> cars = carService.getCarsByBrand(brand);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
    @GetMapping("/adminbrandpath")
    @ResponseBody
    public ResponseEntity<List<CarDTO>> getCarsByBrand1(@PathVariable(required = false) String brand){
        List<CarDTO> cars = carService.getCarsByBrand(brand);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
    @GetMapping("/admin")
    public ResponseEntity<List<CarDTO>> getCars(
            HttpServletRequest request,
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "year", required = false) @Digits(integer = 10, fraction = 0) Integer year,
            @RequestParam(name = "price", required = false) @Digits(integer = 10, fraction = 0) Integer price,
            @RequestParam(name = "mileage", required = false) @Digits(integer = 10, fraction = 0) Integer mileage,
            @RequestParam(name = "colour", required = false) String colour) {

        if (request.getParameter("brand") != null && !brand.matches("^[a-zA-Z]+$")) {
            throw new InvalidRequestException();
        }
        if (request.getParameter("model") != null && !model.matches("^[a-zA-Z0-9]*$")) {
            throw new InvalidRequestException();
        }
        if (request.getParameter("year") != null && year== null) {
            throw new InvalidRequestException();
        }
        if (request.getParameter("price") != null && price == null) {
            throw new InvalidRequestException();
        }
        if (request.getParameter("mileage") != null && mileage == null) {
            throw new InvalidRequestException();
        }
        if (request.getParameter("colour") != null && !colour.matches("^[a-zA-Z]+$")) {
            throw new InvalidRequestException();
        }

        List<CarDTO> cars = carService.getCarsBy(brand, model, year, price, mileage, colour);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

//    @PutMapping("/admin")
//    public ResponseEntity<Map<String, String>> updateCarDTO (@RequestParam @Valid String brand, @Valid Integer mileage, @RequestBody @Valid CarDTO newCarDTO) throws InvalidRequestException{
//        carService.updateCarDTO(brand, mileage, newCarDTO);
//        return new ResponseEntity<>(Map.of("description", "Car updated"), HttpStatus.CREATED);
//    }
    @PutMapping("/admin")
    public ResponseEntity<Map<String, String>> updateCar (@RequestParam @Valid String id, @RequestBody @Valid CarDTO newCarDTO) throws InvalidRequestException{
        carService.updateCar(id, newCarDTO);
        return new ResponseEntity<>(Map.of("description", "Car updated"), HttpStatus.OK);
    }

    @DeleteMapping("/admin")
    public ResponseEntity<Map<String, String>> deleteCar(@RequestParam String id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(Map.of("description", "Car deleted"), HttpStatus.OK);
    }

}



