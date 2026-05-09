package com.foodtruckcompany.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class SecurityConfigTest {

    @Test
    void classHasConfigurationAnnotation() {
        assertThat(SecurityConfig.class.isAnnotationPresent(Configuration.class)).isTrue();
    }

    @Test
    void declaresSecurityFilterChainBeanMethod() throws Exception {
        Method method = SecurityConfig.class.getDeclaredMethod("securityFilterChain", org.springframework.security.config.annotation.web.builders.HttpSecurity.class);

        assertThat(method.getReturnType()).isEqualTo(SecurityFilterChain.class);
    }
}

