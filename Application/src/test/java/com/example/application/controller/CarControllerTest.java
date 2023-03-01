package com.example.application.controller;

import com.example.application.entities.Car;
import com.example.application.entities.CarDTO;
import com.example.application.exceptions.EmptyListException;
import com.example.application.exceptions.InvalidRequestException;
import com.example.application.repository.CarRepository;
import com.example.application.service.CarService;
import com.example.application.service.CarServiceTests;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

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


    @Test
    public void saveCar_returnsCreated(){

        ResponseEntity response = carController.saveCar(List.of(mockCarDTO));
        Assertions.assertAll(
                () -> Assertions.assertEquals("{description=Database updated}", response.getBody().toString()),
                () -> Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode())
        );
    }

    @Test
    public void saveCar_returnsExceptionWhenListIsEmpty() {
        List<CarDTO> emptyList = new ArrayList<>();
        EmptyListException emptyListException = Assertions.assertThrows(EmptyListException.class, () -> carController.saveCar(emptyList));
        Assertions.assertEquals("{description=Empty List}", emptyListException.getCurrentMessage().toString());
    }


    @Test
    public void getAllCars(){
        HttpServletRequest request = mock(HttpServletRequest.class);
        Mockito.lenient().when(request.getParameter("brand")).thenReturn("VW");
        Mockito.lenient().when(request.getParameter("model")).thenReturn("Tiguan");
        Mockito.lenient().when(request.getParameter("year")).thenReturn("2005");
        Mockito.lenient().when(request.getParameter("price")).thenReturn("3500");
        Mockito.lenient().when(request.getParameter("mileage")).thenReturn("56000");
        Mockito.lenient().when(request.getParameter("color")).thenReturn("red");

        List<CarDTO> cars = new ArrayList<>();
        cars.add(new CarDTO("VW", "Tiguan", 2005, 3500, 56000, "red"));
        ResponseEntity<List<CarDTO>> expectedResponse = new ResponseEntity<>(cars, HttpStatus.OK);

        Mockito.lenient().when(carService.getCarsBy("VW", "Tiguan", 2005, 3500, 56000, "red")).thenReturn(cars);

        ResponseEntity<List<CarDTO>> response = carController.getCars(request, "VW", request.getParameter("model"), 2005, 3500, 56000, "red");

        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    public void getCarBy_throwsExceptionWhenParametersAreIncorrect() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Mockito.lenient().when(request.getParameter("brand")).thenReturn("VW");
        Mockito.lenient().when(request.getParameter("model")).thenReturn("Tiguan");
        Mockito.lenient().when(request.getParameter("year")).thenReturn("2005");
        Mockito.lenient().when(request.getParameter("price")).thenReturn("3500");
        Mockito.lenient().when(request.getParameter("mileage")).thenReturn("56000");
        Mockito.lenient().when(request.getParameter("colour")).thenReturn("red");

        Assertions.assertThrows(InvalidRequestException.class, () -> carController.getCars(request, "VW", "Tiguan", null, null, null, "red"));
    }

}
