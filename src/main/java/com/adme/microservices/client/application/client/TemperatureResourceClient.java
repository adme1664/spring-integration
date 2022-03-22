package com.adme.microservices.client.application.client;

import com.adme.microservices.client.application.domain.Temperature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

public class TemperatureResourceClient {
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/weather/temperature");
        Invocation.Builder builder = target.request();
        Temperature result = builder.get(Temperature.class);
        System.out.println(result);
        client.close();
    }
}
