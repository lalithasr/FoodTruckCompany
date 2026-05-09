package com.foodtruckcompany.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodtruckcompany.entity.FoodTruck;
import com.foodtruckcompany.exception.FoodTruckNotFoundException;
import com.foodtruckcompany.exception.GlobalExceptionHandler;
import com.foodtruckcompany.service.FoodTruckService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FoodTruckController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class FoodTruckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FoodTruckService foodTruckService;

    @Test
    void createFoodTruck_returnsCreated() throws Exception {
        FoodTruck request = buildFoodTruck(null, "Spice Route", "Lalit", "Boston", "Indian", 4.7);
        FoodTruck response = buildFoodTruck(1L, "Spice Route", "Lalit", "Boston", "Indian", 4.7);
        when(foodTruckService.createFoodTruck(any(FoodTruck.class))).thenReturn(response);

        mockMvc.perform(post("/food-trucks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.truckName").value("Spice Route"));
    }

    @Test
    void createFoodTruck_withInvalidPayload_returnsBadRequest() throws Exception {
        FoodTruck invalid = buildFoodTruck(null, "", "Lalit", "Boston", "Indian", 4.7);

        mockMvc.perform(post("/food-trucks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"));
    }

    @Test
    void getAllFoodTrucks_returnsOk() throws Exception {
        List<FoodTruck> trucks = List.of(
                buildFoodTruck(1L, "Spice Route", "Lalit", "Boston", "Indian", 4.7),
                buildFoodTruck(2L, "Taco Run", "Ana", "Miami", "Mexican", 4.2)
        );
        when(foodTruckService.getAllFoodTrucks()).thenReturn(trucks);

        mockMvc.perform(get("/food-trucks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void getFoodTruckById_whenFound_returnsOk() throws Exception {
        FoodTruck truck = buildFoodTruck(1L, "Spice Route", "Lalit", "Boston", "Indian", 4.7);
        when(foodTruckService.getFoodTruckById(1L)).thenReturn(truck);

        mockMvc.perform(get("/food-trucks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getFoodTruckById_whenMissing_returnsNotFound() throws Exception {
        when(foodTruckService.getFoodTruckById(99L))
                .thenThrow(new FoodTruckNotFoundException("Food truck not found with id: 99"));

        mockMvc.perform(get("/food-trucks/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("FOOD_TRUCK_NOT_FOUND"));
    }

    @Test
    void updateFoodTruck_returnsOk() throws Exception {
        FoodTruck request = buildFoodTruck(null, "New Name", "Lalit", "Boston", "Indian", 4.9);
        FoodTruck response = buildFoodTruck(1L, "New Name", "Lalit", "Boston", "Indian", 4.9);
        when(foodTruckService.updateFoodTruck(eq(1L), any(FoodTruck.class))).thenReturn(response);

        mockMvc.perform(put("/food-trucks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.truckName").value("New Name"));
    }

    @Test
    void deleteFoodTruck_returnsOk() throws Exception {
        doNothing().when(foodTruckService).deleteFoodTruck(1L);

        mockMvc.perform(delete("/food-trucks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Food truck deleted successfully with id: 1"));
    }

    @Test
    void deleteFoodTruck_whenMissing_returnsNotFound() throws Exception {
        doThrow(new FoodTruckNotFoundException("Food truck not found with id: 1"))
                .when(foodTruckService).deleteFoodTruck(1L);

        mockMvc.perform(delete("/food-trucks/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("FOOD_TRUCK_NOT_FOUND"));
    }

    @Test
    void getFoodTrucksByCuisine_returnsOk() throws Exception {
        when(foodTruckService.getFoodTrucksByCuisine("Indian"))
                .thenReturn(List.of(buildFoodTruck(1L, "Spice Route", "Lalit", "Boston", "Indian", 4.7)));

        mockMvc.perform(get("/food-trucks/cuisine/Indian"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cuisineType").value("Indian"));
    }

    @Test
    void getFoodTrucksByLocation_returnsOk() throws Exception {
        when(foodTruckService.getFoodTrucksByLocation("Boston"))
                .thenReturn(List.of(buildFoodTruck(1L, "Spice Route", "Lalit", "Boston", "Indian", 4.7)));

        mockMvc.perform(get("/food-trucks/location/Boston"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].location").value("Boston"));
    }

    private FoodTruck buildFoodTruck(Long id, String truckName, String ownerName, String location, String cuisineType, Double rating) {
        return new FoodTruck(id, truckName, ownerName, location, cuisineType, rating);
    }
}

