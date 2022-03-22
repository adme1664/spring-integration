package com.adme.cloud.stream.producer.controllers;

import com.adme.cloud.stream.producer.models.Event;
import com.adme.cloud.stream.producer.models.Product;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final StreamBridge streamBridge;

    public ProductController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping("/")
    public boolean sendProduct(@RequestBody Product product){
        Event event= Event.builder()
                .eventName("product")
                .eventObject(product).build();
        return streamBridge.send("admeEvent",
                MessageBuilder.withPayload(event)
                .setHeader("product",true).build()
        );
    }
}
