package com.example.application.service;


import com.example.application.entities.Car;
import com.example.application.entities.CarDTO;
import com.example.application.exceptions.CarAlreadyExistsException;
import com.example.application.exceptions.ResourceNotFoundException;
import com.example.application.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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


    public void addCar(List<CarDTO> cardto) throws CarAlreadyExistsException{
        for (int i = 0; i < cardto.size(); i++) {
            CarDTO acar = cardto.get(i);
            Optional<Car> car = this.carRepository.findFirstByBrandAndModelAndYearAndPriceAndMileageAndColour(acar.getBrand(), acar.getModel(), acar.getYear(), acar.getPrice(), acar.getMileage(), acar.getColour());
            if (car.isPresent()) {
//                System.out.println("IS PRESENT");
                throw new CarAlreadyExistsException();
            } else {
                System.out.println("IS NOT PRESENT");
                List<Car> cars = cardto.stream()
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());

                carRepository.saveAll(cars);
            }
        }
    }

//        public void addCar(List<CarDTO> cardto) {
//            List<Car> cars = cardto.stream()
//                    .map(this::convertToEntity)
//                    .collect(Collectors.toList());
//            carRepository.saveAll(cars);
//        }

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

