package com.foodtruckcompany;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

class FoodTruckCompanyApplicationTest {

    @Test
    void classIsAnnotatedAsSpringBootApplication() {
        assertThat(FoodTruckCompanyApplication.class.isAnnotationPresent(SpringBootApplication.class)).isTrue();
    }

    @Test
    void mainMethodExistsAndIsStatic() throws Exception {
        Method mainMethod = FoodTruckCompanyApplication.class.getDeclaredMethod("main", String[].class);

        assertThat(Modifier.isStatic(mainMethod.getModifiers())).isTrue();
    }
}

