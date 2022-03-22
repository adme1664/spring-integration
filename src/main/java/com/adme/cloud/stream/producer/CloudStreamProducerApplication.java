package com.adme.cloud.stream.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CloudStreamProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStreamProducerApplication.class, args);
    }

}
