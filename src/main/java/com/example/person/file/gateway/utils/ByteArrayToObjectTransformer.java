package com.example.person.file.gateway.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.integration.transformer.PayloadTypeConvertingTransformer;

import java.io.IOException;

public class ByteArrayToObjectTransformer <T> extends PayloadTypeConvertingTransformer<byte[],T> {
    public ByteArrayToObjectTransformer(ObjectMapper om, Class<T> classToMap) {
        setConverter(source -> {
            try{
                return om.readValue(source,classToMap);
            }
            catch (IOException ex){
                throw new RuntimeException("Unable to read file {}",ex);
            }
        });
    }
}
