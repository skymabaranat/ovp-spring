package com.example.application.entities;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Car {
    @Id
    public String id;
    public String brand;
    public String model;
    public int year;
    public int price;
    public int mileage;
    public String colour;

    public Car() {
    }

    public Car(String brand, String model, int year, int price, int mileage, String colour) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.colour = colour;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", mileage=" + mileage +
                ", colour='" + colour + '\'' +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Car car = (Car) o;
//        return year == car.year && price == car.price && mileage == car.mileage && Objects.equals(id, car.id) && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && Objects.equals(colour, car.colour);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, brand, model, year, price, mileage, colour);
//    }

}