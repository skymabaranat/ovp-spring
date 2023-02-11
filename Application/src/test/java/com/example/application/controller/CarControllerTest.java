package com.example.application.controller;

import com.example.application.entities.Car;
import com.example.application.entities.CarDTO;
import com.example.application.repository.CarRepository;
import com.example.application.service.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

    @Mock
    CarService carService;

    @Mock
    CarRepository carRepository;
    @InjectMocks
    CarController carController;


//    @Test
//    public void carMethodReturnsCreated() {
////        Car mockCar = new Car("VW", "Tiguan", 2005, 3500, 56000, "red");
//
//        ResponseEntity<Map<String, String>> response = carController,
//        Assertions.assertAll(
//                ()-> Assertions.assertEquals(HttpStatus.CREATED, )
//        );

    @Test
    public void saveCar_returnsCreated(){
        ResponseEntity response = carController.saveCar(null);
        Assertions.assertAll(
                () -> Assertions.assertEquals("{description=Database updated}", response.getBody().toString()),
                () -> Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode())
        );
    }

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertCarEntityToCarDTO_thenCorrect() {
        Car car = new Car("VW", "Tiguan", 2005, 3500, 56000, "red");

        CarDTO carDto = modelMapper.map(car, CarDTO.class);
        assertEquals(car.getBrand(), carDto.getBrand());
        assertEquals(car.getModel(), carDto.getModel());
        assertEquals(car.getYear(), carDto.getYear());
        assertEquals(car.getPrice(), carDto.getPrice());
        assertEquals(car.getMileage(), carDto.getMileage());
        assertEquals(car.getColour(), carDto.getColour());
    }

    @Test
    public void whenConvertCarDTOToCarEntity_thenCorrect() {
        CarDTO carDto = new CarDTO();
        carDto.setBrand("1");
        carDto.setModel("Tiguan");
        carDto.setYear(2022);
        carDto.setPrice(15000);
        carDto.setMileage(10);
        carDto.setColour("Midnight Blue");

        Car car = modelMapper.map(carDto, Car.class);
        assertEquals(carDto.getBrand(), car.getBrand());
        assertEquals(carDto.getModel(), car.getModel());
        assertEquals(carDto.getYear(), car.getYear());
        assertEquals(carDto.getPrice(), car.getPrice());
        assertEquals(carDto.getMileage(), car.getMileage());
        assertEquals(carDto.getColour(), car.getColour());
    }
}
