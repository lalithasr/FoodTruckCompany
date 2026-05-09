package com.foodtruckcompany.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleFoodTruckNotFoundException_returnsNotFoundResponse() {
        FoodTruckNotFoundException ex = new FoodTruckNotFoundException("Food truck not found with id: 1");

        ResponseEntity<GlobalExceptionHandler.ApiError> response = handler.handleFoodTruckNotFoundException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Food truck not found with id: 1");
        assertThat(response.getBody().errorCode()).isEqualTo("FOOD_TRUCK_NOT_FOUND");
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getBody().timestamp()).isNotNull();
    }

    @Test
    void handleValidationException_withFieldError_returnsBadRequestWithFieldMessage() throws Exception {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "foodTruck");
        bindingResult.addError(new FieldError("foodTruck", "truckName", "Truck name must not be blank"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(getSampleMethodParameter(), bindingResult);

        ResponseEntity<GlobalExceptionHandler.ApiError> response = handler.handleValidationException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Truck name must not be blank");
        assertThat(response.getBody().errorCode()).isEqualTo("VALIDATION_ERROR");
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody().timestamp()).isNotNull();
    }

    @Test
    void handleValidationException_withoutFieldError_returnsBadRequestWithFallbackMessage() throws Exception {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "foodTruck");

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(getSampleMethodParameter(), bindingResult);

        ResponseEntity<GlobalExceptionHandler.ApiError> response = handler.handleValidationException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Validation failed");
        assertThat(response.getBody().errorCode()).isEqualTo("VALIDATION_ERROR");
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody().timestamp()).isNotNull();
    }

    @Test
    void handleGlobalException_returnsInternalServerErrorResponse() {
        Exception ex = new Exception("Unexpected failure");

        ResponseEntity<GlobalExceptionHandler.ApiError> response = handler.handleGlobalException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Unexpected failure");
        assertThat(response.getBody().errorCode()).isEqualTo("INTERNAL_SERVER_ERROR");
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getBody().timestamp()).isNotNull();
    }

    private MethodParameter getSampleMethodParameter() throws NoSuchMethodException {
        Method method = GlobalExceptionHandlerTest.class.getDeclaredMethod("sampleMethod", String.class);
        return new MethodParameter(method, 0);
    }

    @SuppressWarnings("unused")
    private void sampleMethod(String value) {
        // Helper method used only for constructing MethodParameter in tests.
    }
}

