package com.foodtruckcompany.controller;

import com.foodtruckcompany.entity.FoodTruck;
import com.foodtruckcompany.service.FoodTruckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food-trucks")
@Tag(name = "Food Trucks", description = "Endpoints for food truck management")
public class FoodTruckController {

    private final FoodTruckService foodTruckService;

    // Constructor Injection: Spring injects FoodTruckService here
    public FoodTruckController(FoodTruckService foodTruckService) {
        this.foodTruckService = foodTruckService;
    }

    // POST - Create food truck
    @PostMapping
    @Operation(summary = "Create food truck")
    public ResponseEntity<FoodTruck> createFoodTruck(@Valid @RequestBody FoodTruck foodTruck) {
        FoodTruck savedFoodTruck = foodTruckService.createFoodTruck(foodTruck);
        return new ResponseEntity<>(savedFoodTruck, HttpStatus.CREATED);
    }

    // GET - Fetch all food trucks
    @GetMapping
    @Operation(summary = "Get all food trucks")
    public ResponseEntity<List<FoodTruck>> getAllFoodTrucks() {
        List<FoodTruck> foodTrucks = foodTruckService.getAllFoodTrucks();
        return ResponseEntity.ok(foodTrucks);
    }

    // GET - Fetch food truck by id
    @GetMapping("/{id}")
    @Operation(summary = "Get food truck by id")
    public ResponseEntity<FoodTruck> getFoodTruckById(@PathVariable Long id) {
        FoodTruck foodTruck = foodTruckService.getFoodTruckById(id);
        return ResponseEntity.ok(foodTruck);
    }

    // PUT - Update food truck
    @PutMapping("/{id}")
    @Operation(summary = "Update food truck")
    public ResponseEntity<FoodTruck> updateFoodTruck(
            @PathVariable Long id,
            @Valid @RequestBody FoodTruck foodTruck) {

        FoodTruck updatedFoodTruck = foodTruckService.updateFoodTruck(id, foodTruck);
        return ResponseEntity.ok(updatedFoodTruck);
    }

    // DELETE - Delete food truck
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete food truck")
    public ResponseEntity<String> deleteFoodTruck(@PathVariable Long id) {
        foodTruckService.deleteFoodTruck(id);
        return ResponseEntity.ok("Food truck deleted successfully with id: " + id);
    }

    // GET - Find by cuisine
    @GetMapping("/cuisine/{cuisineType}")
    @Operation(summary = "Get food trucks by cuisine")
    public ResponseEntity<List<FoodTruck>> getFoodTrucksByCuisine(@PathVariable String cuisineType) {
        List<FoodTruck> foodTrucks = foodTruckService.getFoodTrucksByCuisine(cuisineType);
        return ResponseEntity.ok(foodTrucks);
    }

    // GET - Find by location
    @GetMapping("/location/{location}")
    @Operation(summary = "Get food trucks by location")
    public ResponseEntity<List<FoodTruck>> getFoodTrucksByLocation(@PathVariable String location) {
        List<FoodTruck> foodTrucks = foodTruckService.getFoodTrucksByLocation(location);
        return ResponseEntity.ok(foodTrucks);
    }
}