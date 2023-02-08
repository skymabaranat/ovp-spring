package com.example.application.entities;
import org.springframework.data.annotation.Id;


public class Car {
    @Id
    public String id;
    public String brand;
    public String model;
    public int year;
    public int price;
    public int mileage;
    public String colour;

    public Car(String brand, String model, int year, int price, int mileage, String colour) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.colour = colour;
    }


}
