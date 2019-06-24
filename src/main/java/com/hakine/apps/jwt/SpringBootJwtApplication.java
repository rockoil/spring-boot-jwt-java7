package com.hakine.apps.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Spring Boot
 */
@SpringBootApplication
@EnableConfigurationProperties
public class SpringBootJwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootJwtApplication.class, args);
    }
}
