package com.foodtruckcompany.service;

import com.foodtruckcompany.entity.FoodTruck;
import com.foodtruckcompany.exception.FoodTruckNotFoundException;
import com.foodtruckcompany.repository.FoodTruckRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodTruckServiceImplTest {

    @Mock
    private FoodTruckRepository foodTruckRepository;

    @InjectMocks
    private FoodTruckServiceImpl foodTruckService;

    @Test
    void createFoodTruck_savesAndReturnsEntity() {
        FoodTruck toCreate = buildFoodTruck(null, "Spice Route", "Lalit", "Boston", "Indian", 4.7);
        FoodTruck saved = buildFoodTruck(1L, "Spice Route", "Lalit", "Boston", "Indian", 4.7);
        when(foodTruckRepository.save(toCreate)).thenReturn(saved);

        FoodTruck result = foodTruckService.createFoodTruck(toCreate);

        assertThat(result.getId()).isEqualTo(1L);
        verify(foodTruckRepository).save(toCreate);
    }

    @Test
    void getAllFoodTrucks_returnsRepositoryResults() {
        List<FoodTruck> trucks = List.of(
                buildFoodTruck(1L, "Spice Route", "Lalit", "Boston", "Indian", 4.7),
                buildFoodTruck(2L, "Taco Run", "Ana", "Miami", "Mexican", 4.2)
        );
        when(foodTruckRepository.findAll()).thenReturn(trucks);

        List<FoodTruck> result = foodTruckService.getAllFoodTrucks();

        assertThat(result).hasSize(2);
        verify(foodTruckRepository).findAll();
    }

    @Test
    void getFoodTruckById_whenPresent_returnsEntity() {
        FoodTruck truck = buildFoodTruck(1L, "Spice Route", "Lalit", "Boston", "Indian", 4.7);
        when(foodTruckRepository.findById(1L)).thenReturn(Optional.of(truck));

        FoodTruck result = foodTruckService.getFoodTruckById(1L);

        assertThat(result.getTruckName()).isEqualTo("Spice Route");
        verify(foodTruckRepository).findById(1L);
    }

    @Test
    void getFoodTruckById_whenMissing_throwsNotFound() {
        when(foodTruckRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> foodTruckService.getFoodTruckById(99L))
                .isInstanceOf(FoodTruckNotFoundException.class)
                .hasMessage("Food truck not found with id: 99");
    }

    @Test
    void updateFoodTruck_whenPresent_updatesAndSavesEntity() {
        FoodTruck existing = buildFoodTruck(1L, "Old", "Owner", "Old City", "Thai", 3.5);
        FoodTruck updates = buildFoodTruck(null, "New", "New Owner", "Boston", "Indian", 4.9);
        FoodTruck saved = buildFoodTruck(1L, "New", "New Owner", "Boston", "Indian", 4.9);

        when(foodTruckRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(foodTruckRepository.save(existing)).thenReturn(saved);

        FoodTruck result = foodTruckService.updateFoodTruck(1L, updates);

        assertThat(result.getTruckName()).isEqualTo("New");
        assertThat(existing.getTruckName()).isEqualTo("New");
        assertThat(existing.getOwnerName()).isEqualTo("New Owner");
        assertThat(existing.getLocation()).isEqualTo("Boston");
        assertThat(existing.getCuisineType()).isEqualTo("Indian");
        assertThat(existing.getRating()).isEqualTo(4.9);
        verify(foodTruckRepository).save(existing);
    }

    @Test
    void updateFoodTruck_whenMissing_throwsNotFound() {
        when(foodTruckRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> foodTruckService.updateFoodTruck(1L, new FoodTruck()))
                .isInstanceOf(FoodTruckNotFoundException.class)
                .hasMessage("Food truck not found with id: 1");
    }

    @Test
    void deleteFoodTruck_whenPresent_deletesEntity() {
        FoodTruck existing = buildFoodTruck(1L, "Spice Route", "Lalit", "Boston", "Indian", 4.7);
        when(foodTruckRepository.findById(1L)).thenReturn(Optional.of(existing));

        foodTruckService.deleteFoodTruck(1L);

        verify(foodTruckRepository).delete(existing);
    }

    @Test
    void deleteFoodTruck_whenMissing_throwsNotFound() {
        when(foodTruckRepository.findById(7L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> foodTruckService.deleteFoodTruck(7L))
                .isInstanceOf(FoodTruckNotFoundException.class)
                .hasMessage("Food truck not found with id: 7");
    }

    @Test
    void getFoodTrucksByCuisine_delegatesToRepository() {
        List<FoodTruck> trucks = List.of(buildFoodTruck(1L, "Spice Route", "Lalit", "Boston", "Indian", 4.7));
        when(foodTruckRepository.findByCuisineTypeIgnoreCase("indian")).thenReturn(trucks);

        List<FoodTruck> result = foodTruckService.getFoodTrucksByCuisine("indian");

        assertThat(result).hasSize(1);
        verify(foodTruckRepository).findByCuisineTypeIgnoreCase("indian");
    }

    @Test
    void getFoodTrucksByLocation_delegatesToRepository() {
        List<FoodTruck> trucks = List.of(buildFoodTruck(1L, "Spice Route", "Lalit", "Boston", "Indian", 4.7));
        when(foodTruckRepository.findByLocationIgnoreCase("boston")).thenReturn(trucks);

        List<FoodTruck> result = foodTruckService.getFoodTrucksByLocation("boston");

        assertThat(result).hasSize(1);
        verify(foodTruckRepository).findByLocationIgnoreCase("boston");
    }

    private FoodTruck buildFoodTruck(Long id, String truckName, String ownerName, String location, String cuisineType, Double rating) {
        return new FoodTruck(id, truckName, ownerName, location, cuisineType, rating);
    }
}

