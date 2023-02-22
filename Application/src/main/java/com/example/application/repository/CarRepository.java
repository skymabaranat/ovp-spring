package com.example.application.repository;

import com.example.application.entities.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CarRepository extends MongoRepository<Car, String> {
d);

//    @Query(value = "{'brand' : ?0}")
    List<Car> findAllByBrand(String brand);
    Boolean existsByBrandAndModelAndYearAndPriceAndMileageAndColour(String Brand, String Model, Integer Year, Integer Price, Integer Mileage, String Colour);

    Car findCarById(String s);
}
