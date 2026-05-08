package com.foodtruckcompany.service;

import com.foodtruckcompany.entity.FoodTruck;

import java.util.List;

public interface FoodTruckService {

    FoodTruck createFoodTruck(FoodTruck foodTruck);

    List<FoodTruck> getAllFoodTrucks();

    FoodTruck getFoodTruckById(Long id);

    FoodTruck updateFoodTruck(Long id, FoodTruck foodTruck);

    void deleteFoodTruck(Long id);

    List<FoodTruck> getFoodTrucksByCuisine(String cuisineType);

    List<FoodTruck> getFoodTrucksByLocation(String location);
}