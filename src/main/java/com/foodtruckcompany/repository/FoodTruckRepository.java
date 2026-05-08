package com.foodtruckcompany.repository;

import com.foodtruckcompany.entity.FoodTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodTruckRepository extends JpaRepository<FoodTruck, Long> {

    List<FoodTruck> findByCuisineTypeIgnoreCase(String cuisineType);

    List<FoodTruck> findByLocationIgnoreCase(String location);
}