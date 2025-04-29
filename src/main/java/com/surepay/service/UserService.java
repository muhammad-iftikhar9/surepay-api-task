package com.surepay.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surepay.restclient.HttpRestClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserService {
    private final HttpRestClient httpClient;

    public UserService(HttpRestClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
    }

    @Step("Find User By User Name")
    public Response findUserByUsername(String username) throws Exception {
        return httpClient.get("/users", "username", username);
    }
} 