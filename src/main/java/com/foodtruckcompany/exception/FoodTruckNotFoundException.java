package com.foodtruckcompany.exception;

public class FoodTruckNotFoundException extends RuntimeException {

    public FoodTruckNotFoundException(String message) {
        super(message);
    }
}