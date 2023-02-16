package com.example.application.repository;

import com.example.application.entities.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CarRepository extends MongoRepository<Car, String> {
//    public Car findByModel(String model);
    Optional<Car> findByBrand(String brand);

    Optional<Car> findCarByModelAndMileage(String Model, Integer mileage);
//    @Query(value = "SELECT car FROM car WHERE car.brand='BMW'")
//    Optional<Car> findCarByAllFields(String Brand, String Model, Integer Year, Integer Price, Integer Mileage, String Colour);

    Optional<Car> findFirstByBrandAndModelAndYearAndPriceAndMileageAndColour(String Brand, String Model, Integer Year, Integer Price, Integer Mileage, String Colour);

    Car findCarById(String s);
}
