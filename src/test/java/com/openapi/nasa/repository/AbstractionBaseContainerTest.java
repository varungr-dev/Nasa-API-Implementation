package com.openapi.nasa.repository;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public class AbstractionBaseContainerTest {

    public static final MySQLContainer<?> mySQLContainer;

    static {
        mySQLContainer = new MySQLContainer<>("mysql:latest")
                .withUsername("springstudent")
                .withPassword("springstudent");

        mySQLContainer.start();
    }

    @DynamicPropertySource
    public static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.security.user.name", () -> "tester"); // database role
        registry.add("spring.security.user.password", () -> "{bcrypt}$2a$10$wuKNzWHaSNudbnAnQ2K8yeXdeQAQayaaD53T32olL8XUXB4DWIgdK"); // database password
    }
}
