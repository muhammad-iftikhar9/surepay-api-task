package com.surepay.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ConfigurationManager {
    private static final String CONFIG_FILE = "/configuration/configuration.json";
    private static ConfigurationManager instance;
    private final JsonNode config;

    private ConfigurationManager() {
        try (InputStream inputStream = getClass().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new RuntimeException("Configuration file not found: " + CONFIG_FILE);
            }
            ObjectMapper mapper = new ObjectMapper();
            config = mapper.readTree(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration: " + e.getMessage(), e);
        }
    }

    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    public String getApiBaseUrl() {
        return config.path("api").path("baseUrl").asText();
    }
} 