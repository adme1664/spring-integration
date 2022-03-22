package com.adme.microservices.client.application.domain;

import lombok.Data;

@Data
public class Temperature {
    private double temperature;
    private TemperatureScale temperatureScale;

}


