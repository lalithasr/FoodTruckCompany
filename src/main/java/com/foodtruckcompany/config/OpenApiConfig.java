package com.foodtruckcompany.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI foodTruckOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Food Truck Management API")
                        .version("v1")
                        .description("CRUD APIs for managing food trucks")
                        .contact(new Contact().name("FoodTruck Team"))
                        .license(new License().name("Apache 2.0"))
                );
    }
}

