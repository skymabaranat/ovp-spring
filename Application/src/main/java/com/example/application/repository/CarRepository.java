package com.example.application.repository;

import com.example.application.entities.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CarRepository extends MongoRepository<Car, String> {
//    public Car findA(String model);
    Optional<Car> findByBrand(String brand);

    Optional<Car> findCarByModelAndMileage(String Model, Integer mileage);

    Optional<Car> findFirstByBrandAndModelAndYearAndPriceAndMileageAndColour(String Brand, String Model, Integer Year, Integer Price, Integer Mileage, String Colour);
    Boolean existsByBrandAndModelAndYearAndPriceAndMileageAndColour(String Brand, String Model, Integer Year, Integer Price, Integer Mileage, String Colour);
//    https://www.baeldung.com/spring-data-exists-query
    Optional<Car> findFirstByModelAndMileage(String Model, Integer Mileage);

    Car findCarById(String s);
}
