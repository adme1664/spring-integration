package com.example.cloudstreamconsumerproduct;

import com.example.cloudstreamconsumerproduct.models.Event;
import com.example.cloudstreamconsumerproduct.services.ProductProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
public class CloudStreamConsumerProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStreamConsumerProductApplication.class, args);
    }

    @Bean
    public Consumer<Event> productConsumer(final ProductProcessor productProcessor) {
        return productProcessor::processProduct;
    }
}
