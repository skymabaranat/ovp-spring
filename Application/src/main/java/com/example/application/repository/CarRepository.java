package com.example.application.repository;

import com.example.application.entities.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<Car, String> {
    public Car findByModel(String model);
}
