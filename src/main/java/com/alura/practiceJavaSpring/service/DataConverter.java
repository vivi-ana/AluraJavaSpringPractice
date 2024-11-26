package com.alura.practiceJavaSpring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Converts JSON data to specified class.
 */
public class DataConverter implements IDataConverter{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> toClass) {
        try {
            return mapper.readValue(json.toString(), toClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
