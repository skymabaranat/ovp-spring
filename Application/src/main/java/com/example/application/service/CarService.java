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
import java.util.Comparator;
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

    public List<CarDTO> getCarsByBrand(String brand){
        return this.carRepository.findAllByBrand(brand).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CarDTO> getCarsBy(String brand, String model, Integer year, Integer price, Integer mileage, String colour){
        List<CarDTO> carDTOS = this.carRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        if (brand != null){
            carDTOS = carDTOS.stream()
                    .filter(carDTO -> carDTO.getBrand().equals(brand))
                    .sorted(Comparator.comparing(CarDTO::getBrand))
                    .collect(Collectors.toList());
        }
        if (model != null){
            carDTOS = carDTOS.stream()
                    .filter(carDTO -> carDTO.getModel().equals(model))
                    .sorted(Comparator.comparing(CarDTO::getBrand))
                    .collect(Collectors.toList());
        }
        if (year != null){
            carDTOS = carDTOS.stream()
                    .filter(carDTO -> carDTO.getYear() == year)
                    .sorted(Comparator.comparing(CarDTO::getBrand))
                    .collect(Collectors.toList());

        }
        if (price != null){
            carDTOS = carDTOS.stream()
                    .filter(carDTO -> carDTO.getPrice() == price)
                    .sorted(Comparator.comparing(CarDTO::getBrand))
                    .collect(Collectors.toList());

        }
        if (mileage != null){
            carDTOS = carDTOS.stream()
                    .filter(carDTO -> carDTO.getMileage() == mileage)
                    .sorted(Comparator.comparing(CarDTO::getBrand))
                    .collect(Collectors.toList());
        }
        if (colour != null){
            carDTOS = carDTOS.stream()
                    .filter(carDTO -> carDTO.getColour().equals(colour))
                    .sorted(Comparator.comparing(CarDTO::getBrand))
                    .collect(Collectors.toList());
        }
        return carDTOS;
    }

//    public void updateCarDTO(String brand, Integer mileage, CarDTO newCarDTO) {
//        Car updateCarDTO = carRepository.findByBrandAndMileage(brand, mileage)
//                .orElseThrow(ResourceNotFoundException::new);
//        if (carRepository.existsByBrandAndModelAndYearAndPriceAndMileageAndColour(
//                newCarDTO.getBrand(), newCarDTO.getModel(), newCarDTO.getYear(), newCarDTO.getPrice(), newCarDTO.getMileage(), newCarDTO.getColour())) {
//            throw new CarAlreadyExistsException();
//        } else {
//            updateCarDTO.setBrand(newCarDTO.getBrand());
//            updateCarDTO.setModel(newCarDTO.getModel());
//            updateCarDTO.setYear(newCarDTO.getYear());
//            updateCarDTO.setPrice(newCarDTO.getPrice());
//            updateCarDTO.setMileage(newCarDTO.getMileage());
//            updateCarDTO.setColour(newCarDTO.getColour());
//            carRepository.save(updateCarDTO);
//        }
//    }
//
        public void updateCar (String id, CarDTO newCarDTO) {
        Car updateCarDTO = carRepository.findCarById(id);
        if (updateCarDTO == null)
            throw new ResourceNotFoundException();
        if (carRepository.existsByBrandAndModelAndYearAndPriceAndMileageAndColour(
                newCarDTO.getBrand(), newCarDTO.getModel(), newCarDTO.getYear(), newCarDTO.getPrice(), newCarDTO.getMileage(), newCarDTO.getColour())){
            throw new CarAlreadyExistsException();
        } else {
            updateCarDTO.setBrand(newCarDTO.getBrand());
            updateCarDTO.setModel(newCarDTO.getModel());
            updateCarDTO.setYear(newCarDTO.getYear());
            updateCarDTO.setPrice(newCarDTO.getPrice());
            updateCarDTO.setMileage(newCarDTO.getMileage());
            updateCarDTO.setColour(newCarDTO.getColour());
            carRepository.save(updateCarDTO);
        }

    }
    public void deleteCar(String id) {
        Car car = carRepository.findCarById(id);
        if (car == null) {
            throw new ResourceNotFoundException();
        }
        carRepository.delete(car);
    }


}


