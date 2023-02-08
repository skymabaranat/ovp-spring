package com.example.application.controller;

import com.example.application.entities.Car;
import com.example.application.repository.CarRepository;
import com.example.application.service.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

    @Mock
    CarService carService;

    @Mock
    CarRepository carRepository;
    @InjectMocks
    CarController carController;


    @Test
    public void getStatus_returnsOK(){
        ResponseEntity response = carController.getStatus();

        Assertions.assertAll(
                () -> Assertions.assertEquals("OK", response.getBody()),
                () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode())
        );
    }

//    @Test
//    public void postMethodReturnsCreated() {
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

}
