package com.foodtruckcompany.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigTest {

    @Test
    void foodTruckOpenApi_containsExpectedMetadata() {
        OpenApiConfig config = new OpenApiConfig();

        OpenAPI openAPI = config.foodTruckOpenApi();

        assertThat(openAPI.getInfo()).isNotNull();
        assertThat(openAPI.getInfo().getTitle()).isEqualTo("Food Truck Management API");
        assertThat(openAPI.getInfo().getVersion()).isEqualTo("v1");
        assertThat(openAPI.getInfo().getDescription()).isEqualTo("CRUD APIs for managing food trucks");
        assertThat(openAPI.getInfo().getContact().getName()).isEqualTo("FoodTruck Team");
        assertThat(openAPI.getInfo().getLicense().getName()).isEqualTo("Apache 2.0");
    }
}

