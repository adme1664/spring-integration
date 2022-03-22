package com.example.cloudstreamconsumerproduct.models;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
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
