package com.example.application.repository;

import com.example.application.entities.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CarRepository extends MongoRepository<Car, String> {
//    public Car findByModel(String model);
    Optional<Car> findByBrand(String brand);

    Car findCarById(String s);
}
