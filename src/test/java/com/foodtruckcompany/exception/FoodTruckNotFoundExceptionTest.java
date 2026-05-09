package com.foodtruckcompany.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FoodTruckNotFoundExceptionTest {

    @Test
    void messageIsPreserved() {
        FoodTruckNotFoundException exception = new FoodTruckNotFoundException("Not found");

        assertThat(exception.getMessage()).isEqualTo("Not found");
    }
}

