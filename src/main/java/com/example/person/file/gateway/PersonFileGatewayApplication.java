package com.example.person.file.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class PersonFileGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonFileGatewayApplication.class, args);
    }
}
