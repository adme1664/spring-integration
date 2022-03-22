package com.adme.cloud.stream.producer.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product implements Serializable {
    int id;
    UUID uuid;
    String productName;
    double price;
    int quantity;

    @Override
    public String toString() {
        return "Product{" +
                "uuid=" + uuid +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

