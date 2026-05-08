package com.foodtruckcompany.service;

import com.foodtruckcompany.entity.FoodTruck;
import com.foodtruckcompany.exception.FoodTruckNotFoundException;
import com.foodtruckcompany.repository.FoodTruckRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodTruckServiceImpl implements FoodTruckService {

    private final FoodTruckRepository foodTruckRepository;

    // Constructor Injection: Spring injects FoodTruckRepository here
    public FoodTruckServiceImpl(FoodTruckRepository foodTruckRepository) {
        this.foodTruckRepository = foodTruckRepository;
    }

    @Override
    public FoodTruck createFoodTruck(FoodTruck foodTruck) {
        return foodTruckRepository.save(foodTruck);
    }

    @Override
    public List<FoodTruck> getAllFoodTrucks() {
        return foodTruckRepository.findAll();
    }

    @Override
    public FoodTruck getFoodTruckById(Long id) {
        return foodTruckRepository.findById(id)
                .orElseThrow(() -> new FoodTruckNotFoundException("Food truck not found with id: " + id));
    }

    @Override
    public FoodTruck updateFoodTruck(Long id, FoodTruck updatedFoodTruck) {
        FoodTruck existingFoodTruck = foodTruckRepository.findById(id)
                .orElseThrow(() -> new FoodTruckNotFoundException("Food truck not found with id: " + id));

        existingFoodTruck.setTruckName(updatedFoodTruck.getTruckName());
        existingFoodTruck.setOwnerName(updatedFoodTruck.getOwnerName());
        existingFoodTruck.setLocation(updatedFoodTruck.getLocation());
        existingFoodTruck.setCuisineType(updatedFoodTruck.getCuisineType());
        existingFoodTruck.setRating(updatedFoodTruck.getRating());

        return foodTruckRepository.save(existingFoodTruck);
    }

    @Override
    public void deleteFoodTruck(Long id) {
        FoodTruck existingFoodTruck = foodTruckRepository.findById(id)
                .orElseThrow(() -> new FoodTruckNotFoundException("Food truck not found with id: " + id));

        foodTruckRepository.delete(existingFoodTruck);
    }

    @Override
    public List<FoodTruck> getFoodTrucksByCuisine(String cuisineType) {
        return foodTruckRepository.findByCuisineTypeIgnoreCase(cuisineType);
    }

    @Override
    public List<FoodTruck> getFoodTrucksByLocation(String location) {
        return foodTruckRepository.findByLocationIgnoreCase(location);
    }
}