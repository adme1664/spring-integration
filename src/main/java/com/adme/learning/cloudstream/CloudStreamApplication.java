package com.adme.learning.cloudstream;

import com.adme.learning.cloudstream.models.Event;
import com.adme.learning.cloudstream.services.ConsumerPersonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SpringBootApplication
public class CloudStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStreamApplication.class, args);
    }

    @Bean
    public Consumer<Event> personConsumer(final ConsumerPersonService consumerPersonService) {
        return consumerPersonService::processEvent;
    }

    @Bean
    public Supplier<Event> personSupplier(final ConsumerPersonService consumerPersonService){
        return consumerPersonService::processEventAndSend;
    }
}
