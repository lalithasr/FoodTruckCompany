package com.foodtruckcompany.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FoodTruckTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void allArgsConstructorAndGetters_workAsExpected() {
        FoodTruck truck = new FoodTruck(1L, "Spice Route", "Lalit", "Boston", "Indian", 4.7);

        assertThat(truck.getId()).isEqualTo(1L);
        assertThat(truck.getTruckName()).isEqualTo("Spice Route");
        assertThat(truck.getOwnerName()).isEqualTo("Lalit");
        assertThat(truck.getLocation()).isEqualTo("Boston");
        assertThat(truck.getCuisineType()).isEqualTo("Indian");
        assertThat(truck.getRating()).isEqualTo(4.7);
    }

    @Test
    void noArgsConstructorAndSetters_workAsExpected() {
        FoodTruck truck = new FoodTruck();
        truck.setId(2L);
        truck.setTruckName("Taco Run");
        truck.setOwnerName("Ana");
        truck.setLocation("Miami");
        truck.setCuisineType("Mexican");
        truck.setRating(4.3);

        assertThat(truck.getId()).isEqualTo(2L);
        assertThat(truck.getTruckName()).isEqualTo("Taco Run");
        assertThat(truck.getOwnerName()).isEqualTo("Ana");
        assertThat(truck.getLocation()).isEqualTo("Miami");
        assertThat(truck.getCuisineType()).isEqualTo("Mexican");
        assertThat(truck.getRating()).isEqualTo(4.3);
    }

    @Test
    void validationFailsForBlankAndInvalidValues() {
        FoodTruck invalid = new FoodTruck(null, "", "", "", "", -1.0);

        Set<ConstraintViolation<FoodTruck>> violations = validator.validate(invalid);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .extracting(v -> v.getPropertyPath().toString())
                .contains("truckName", "ownerName", "location", "cuisineType", "rating");
    }
}

