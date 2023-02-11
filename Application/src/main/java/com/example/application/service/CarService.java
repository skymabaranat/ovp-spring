package com.example.application.service;


import com.example.application.entities.Car;
import com.example.application.entities.CarDTO;
import com.example.application.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
//    @Autowired
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
    public String generateString() {
        return "OK";
    }

//    public void addCar(List<CarDTO> car){
//        this.carRepository.
////        return null;
    public void addCar(List<CarDTO> cardto) {
        List<Car> cars = cardto.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        carRepository.saveAll(cars);
    }

    public List<Car> getAllCars (){
        return this.carRepository.findAll();
    }
}
