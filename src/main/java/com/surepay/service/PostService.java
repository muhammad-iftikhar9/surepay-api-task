package com.surepay.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surepay.restclient.HttpRestClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class PostService {
    private final HttpRestClient httpClient;

    public PostService(HttpRestClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
    }

    @Step("Get Posts By User Id")
    public Response getPostsByUserId(int userId) throws Exception {
        return httpClient.get("/posts", "userId", userId);
    }
} 