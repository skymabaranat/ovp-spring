package com.example.application.controller;

import com.example.application.entities.Car;
import com.example.application.entities.CarDTO;
import com.example.application.exceptions.EmptyListException;
import com.example.application.repository.CarRepository;
import com.example.application.service.CarService;
import com.example.application.service.CarServiceTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

    @Mock
    CarService carService;

    @Mock
    CarRepository carRepository;
    @InjectMocks
    CarController carController;

    private CarDTO mockCarDTO;

    @BeforeEach
    public void init() {
        mockCarDTO = new CarDTO("VW", "Tiguan", 2005, 3500, 56000, "red");
    }

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

        ResponseEntity response = carController.saveCar(List.of(mockCarDTO));
        Assertions.assertAll(
                () -> Assertions.assertEquals("{description=Database updated}", response.getBody().toString()),
                () -> Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode())
        );
    }    @Test
    public void saveCar_returnsExceptionWhenListIsEmpty(){
        ResponseEntity response = carController.saveCar();
        Throwable exception = Assertions.assertThrows(EmptyListException.class, () -> carService.addCar(null));

        Assertions.assertAll(
                () -> Assertions.assertEquals("{description=Database updated}", response.getBody().toString()),
                () -> Assertions.assertEquals(HttpStatus.CREATED, exception.getMessage())
        );
    }
}
