package com.example.application.entities;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;


public class CarDTO {
    //add annotation to validate the fields

    @NotBlank(message = "Brand is mandatory")
    public String brand;
    @NotNull(message = "Model is mandatory")
    public String model;
    @NotNull(message = "Model is mandatory")
    @Min(1900)
    @Max(2023)
    @Digits(integer=4, fraction=0)
    public Integer year;
    @NotNull(message = "Price is mandatory")
    @Min(0)
    public Integer price;
    @NotNull(message = "Mileage is mandatory")
    @Min(0)
    public Integer mileage;
    @NotBlank(message = "Colour is mandatory")
    public String colour;

    //    no args constructor
    public CarDTO() {
    }

// all args constructor
    public CarDTO(String brand, String model, int year, int price, int mileage, String colour) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.colour = colour;
    }

//    Getters and setters

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
        return "CarDTO{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", mileage=" + mileage +
                ", colour='" + colour + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDTO carDTO = (CarDTO) o;
        return brand.equals(carDTO.brand) && model.equals(carDTO.model) && year.equals(carDTO.year) && price.equals(carDTO.price) && mileage.equals(carDTO.mileage) && colour.equals(carDTO.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, year, price, mileage, colour);
    }
}
