package com.example.application.service;

import com.example.application.entities.Car;
import com.example.application.entities.CarDTO;
import com.example.application.exceptions.CarAlreadyExistsException;
import com.example.application.exceptions.EmptyListException;
import com.example.application.repository.CarRepository;
import jakarta.validation.ConstraintViolation;
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
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    public void getAllCars_returnsAListOfAllCarsInTheRepository() {
        Mockito.when(carRepository.findAll()).thenReturn(List.of(mockCar));
        List<CarDTO> mockCarList = carService.getCarsBy(null, null, null, null, null, null);
        assertEquals(List.of(mockCarDTO), mockCarList);
        verify(carRepository, times(1)).findAll();
    }
    @Test
    public void getCarsBy_returnsAListOfFilteredCars() {
        Car car1 = new Car("BMW", "X5", 2022, 65000, 0, "Black");
        Car car2 = new Car("BMW", "X3", 2021, 55000, 1000, "White");
        Car car3 = new Car("VW", "Tiguan", 2005, 3500, 56000, "red");

        // Set up the mock cars list to return car1 and car2
        Mockito.when(carRepository.findAll()).thenReturn(List.of(car1, car2, car3));

        // Test filtering by brand
        List<CarDTO> filteredCars = carService.getCarsBy("BMW", null, null, null, null,  null);
        assertEquals(2, filteredCars.size());

        // Test filtering by model
        filteredCars = carService.getCarsBy(null, "X5", null, null, null, null);
        assertEquals(1, filteredCars.size());
        assertEquals("X5", filteredCars.get(0).getModel());

        filteredCars = carService.getCarsBy(null, "Tiguan", null, null, null, null);
        assertEquals(1, filteredCars.size());

        // Test filtering by year
        filteredCars = carService.getCarsBy(null, null, 2021, null, null, null);
        assertEquals(1, filteredCars.size());
        assertEquals(2021, filteredCars.get(0).getYear());

        // Test sorting by price
        filteredCars = carService.getCarsBy(null, null,  null, 65000, null, null);
        assertEquals(1, filteredCars.size());
        assertEquals(65000.0, filteredCars.get(0).getPrice(), 0.0);

        // Test sorting by mileage
        filteredCars = carService.getCarsBy(null, null, null, null, 0, null);
        assertEquals(1, filteredCars.size());
        assertEquals(0.0, filteredCars.get(0).getMileage(), 0.0);

        // Test sorting by colour
        filteredCars = carService.getCarsBy(null, null, null, null, null, "Black");
        assertEquals(1, filteredCars.size());
        assertEquals("Black", filteredCars.get(0).getColour());
    }


    @Test
    public void addCar_callsRepositoryOnce() {
        List<CarDTO> mockCarList = List.of(mockCarDTO);
        carService.addCar(mockCarList);
        verify(carRepository, times(1)).saveAll(any());
    }

    @Test
    public void addCar_whenCarAlreadyExists_throwsCarAlreadyExistsException() {
        List<CarDTO> mockCarList = List.of(mockCarDTO);
        Mockito.when(carRepository.saveAll(any())).thenThrow(new CarAlreadyExistsException());
        Assertions.assertThrows(CarAlreadyExistsException.class, () -> carService.addCar(mockCarList));
    }

    @Test
    public void addCar_whenInvalidData_throwsConstraintViolationException() {
        List<CarDTO> mockCarList = List.of(mockCarDTO);
        Mockito.when(carRepository.saveAll(any())).thenThrow(new ConstraintViolationException(new HashSet<ConstraintViolation<?>>()));
        Assertions.assertThrows(ConstraintViolationException.class, () -> carService.addCar(mockCarList));
    }

    @Test
    public void addCar_whenMalformedData_throwsHttpMessageNotReadableException() {
        List<CarDTO> mockCarList = List.of(mockCarDTO);
        Mockito.when(carRepository.saveAll(any())).thenThrow(new HttpMessageNotReadableException("Incorrect car data provided", new Exception(), null));
        Assertions.assertThrows(HttpMessageNotReadableException.class, () -> carService.addCar(mockCarList));
    }

    @Test
    public void updateCar_returnsTheCorrectCar() throws Exception {
        // Create a CarDTO object with updated car data
        CarDTO updatedCar = new CarDTO("Toyota", "Camry", 2020, 25000, 10000, "Red");

        // Set up the carRepository to return a dummy Car object when finding a car by ID
        when(carRepository.findCarById("123")).thenReturn(new Car());

        // Set up the carRepository to return false when checking if a car with the same data already exists
        when(carRepository.existsByBrandAndModelAndYearAndPriceAndMileageAndColour(
                updatedCar.getBrand(), updatedCar.getModel(), updatedCar.getYear(),
                updatedCar.getPrice(), updatedCar.getMileage(), updatedCar.getColour()))
                .thenReturn(false);

        // Call the updateCar() method on the service
        carService.updateCar("123", updatedCar);

        // Verify that the carRepository was called with the correct parameters
        verify(carRepository).findCarById("123");
        verify(carRepository).existsByBrandAndModelAndYearAndPriceAndMileageAndColour(
                updatedCar.getBrand(), updatedCar.getModel(), updatedCar.getYear(),
                updatedCar.getPrice(), updatedCar.getMileage(), updatedCar.getColour());
        verify(carRepository).save(any(Car.class));
    }
}
