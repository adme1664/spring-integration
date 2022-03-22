package com.example.cloudstreamconsumerproduct.services;

import com.example.cloudstreamconsumerproduct.models.Event;
import com.example.cloudstreamconsumerproduct.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductProcessor {

    private final ObjectMapper objectMapper;

    public ProductProcessor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void processProduct(Event event){
        if (event.getEventName().equals("product")) {
            Product product = objectMapper.convertValue(event.getEventObject(),Product.class);
            log.info("Product received: {}", product.toString());
        }

    }
}
