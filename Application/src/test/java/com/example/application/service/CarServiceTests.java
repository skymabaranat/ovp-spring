package com.example.application.service;

import com.example.application.entities.Car;
import com.example.application.entities.CarDTO;
import com.example.application.exceptions.CarAlreadyExistsException;
import com.example.application.repository.CarRepository;
import jakarta.validation.ConstraintViolationException;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CarServiceTests {

    @InjectMocks
    CarService carService;
    @Mock
    CarRepository carRepository;
    @Spy
    ModelMapper modelMapper = new ModelMapper();

    private Car mockCar;
    private CarDTO mockCarDTO;


    @BeforeEach
    public void init() {
        mockCar = new Car("VW", "Tiguan", 2005, 3500, 56000, "red");
        mockCarDTO = new CarDTO("VW", "Tiguan", 2005, 3500, 56000, "red");
    }

    @Test
     void whenConvertCarDTOToCarEntity_thenCorrect() {

        Car car = modelMapper.map(mockCarDTO, Car.class);
        assertEquals(mockCarDTO.getBrand(), car.getBrand());
        assertEquals(mockCarDTO.getModel(), car.getModel());
        assertEquals(mockCarDTO.getYear(), car.getYear());
        assertEquals(mockCarDTO.getPrice(), car.getPrice());
        assertEquals(mockCarDTO.getMileage(), car.getMileage());
        assertEquals(mockCarDTO.getColour(), car.getColour());
    }

    @Test
    public void whenConvertCarEntityToCarDTO_thenCorrect() {
        CarDTO carDto = modelMapper.map(mockCar, CarDTO.class);
        assertEquals(mockCar.getBrand(), carDto.getBrand());
        assertEquals(mockCar.getModel(), carDto.getModel());
        assertEquals(mockCar.getYear(), carDto.getYear());
        assertEquals(mockCar.getPrice(), carDto.getPrice());
        assertEquals(mockCar.getMileage(), carDto.getMileage());
        assertEquals(mockCar.getColour(), carDto.getColour());
    }
    @Test
    public void addCar_throwsAConflictException_WhenCarAlreadyExists() {
        Mockito.when(carRepository.save(mockCar)).thenThrow(new CarAlreadyExistsException());
    }

    @Test
    public void addCar_throwsAnException_WhenDataIsInvalid() {

    }

    @Test
    public void getAllCars_returnsAListOfAllCarsInTheRepository() {
        Mockito.when(carRepository.findAll()).thenReturn(List.of(mockCar));
        List<CarDTO> mockCarList = carService.getAllCars();
        assertEquals(List.of(mockCarDTO), mockCarList);
    }

    @Test
    public void verifyNumberOfTimesMethodWasCalled() {
    }
}


// https://www.developer.com/java/mockito-java-unit-testing-with-mock-objects/