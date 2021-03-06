package com.project.dco_common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonHelper {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String convertObject2JsonString(Object obj) {
        String jsonString = "";
        try {
            if (obj != null) {
                jsonString = mapper.writeValueAsString(obj);
                return jsonString;
            }
        } catch (JsonProcessingException e) {
            System.out.println("Error while convert object to json!");
        }
        return null;
    }

    public static <T> T convertJson2Object(String jsonString, Class<T> clazz) {
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            System.out.println("Error while convert object to json!");
        }
        return null;
    }
}
