package com.foodtruckcompany.repository;

import com.foodtruckcompany.entity.FoodTruck;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class FoodTruckRepositoryTest {

    @Autowired
    private FoodTruckRepository foodTruckRepository;

    @Test
    void findByCuisineTypeIgnoreCase_returnsMatchingRows() {
        FoodTruck truck = new FoodTruck(null, "Korma King", "Raj", "Chicago", "Indian", 4.6);
        foodTruckRepository.save(truck);

        List<FoodTruck> result = foodTruckRepository.findByCuisineTypeIgnoreCase("indian");

        assertThat(result)
                .extracting(FoodTruck::getTruckName)
                .contains("Korma King");
    }

    @Test
    void findByLocationIgnoreCase_returnsMatchingRows() {
        FoodTruck truck = new FoodTruck(null, "Burger Wheels", "Alex", "Seattle", "American", 4.1);
        foodTruckRepository.save(truck);

        List<FoodTruck> result = foodTruckRepository.findByLocationIgnoreCase("SEATTLE");

        assertThat(result)
                .extracting(FoodTruck::getOwnerName)
                .contains("Alex");
    }
}

