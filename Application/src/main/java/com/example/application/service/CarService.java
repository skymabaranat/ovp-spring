package com.example.application.service;


import com.example.application.entities.Car;
import com.example.application.entities.CarDTO;
import com.example.application.exceptions.CarAlreadyExistsException;
import com.example.application.exceptions.ResourceNotFoundException;
import com.example.application.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    private ModelMapper modelMapper;

    //constructor
    public CarService(CarRepository carRepository, ModelMapper modelMapper){
        super();
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    private Car convertToEntity(CarDTO cardto) {
        return this.modelMapper.map(cardto, Car.class);
    }

    private CarDTO convertToDto(Car car) {
        return this.modelMapper.map(car, CarDTO.class);
    }

    public String generateString() {
        return "OK";
    }


    public void addCar(List<CarDTO> cardto) throws CarAlreadyExistsException {
        List<Car> carExistsInDb =  new ArrayList<Car>();
        List<Car> cars = cardto.stream()
                    .map(this::convertToEntity)
                    .collect(Collectors.toList());
        cars.forEach(car -> {
            if (carRepository.existsByBrandAndModelAndYearAndPriceAndMileageAndColour(
                    car.getBrand(), car.getModel(), car.getYear(), car.getPrice(), car.getMileage(), car.getColour())){
                carExistsInDb.add(car);
                throw new CarAlreadyExistsException();
            }
        });
//        carRepository.saveAll(cars);
        if (carExistsInDb.isEmpty()) {
            carRepository.saveAll(cars);
        }
    }
    // https://www.baeldung.com/foreach-java
    // https://stackoverflow.com/questions/68201346/how-to-look-if-list-exists-in-db-in-springboot

    public List<CarDTO> getAllCars (){
        return this.carRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<Car> getCarByID(String id) {
        Car car =  this.carRepository.findCarById(id);
        if (car == null)
            throw new ResourceNotFoundException();
        return Optional.of(car);
    }
}

