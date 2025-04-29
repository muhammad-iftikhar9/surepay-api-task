package com.surepay.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surepay.restclient.HttpRestClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class CommentService {
    private final HttpRestClient httpClient;

    public CommentService(HttpRestClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
    }

    @Step("Get Comments By Post Id")
    public Response getCommentsByPostId(int postId) throws Exception {
        return httpClient.get("/comments", "postId", postId);
    }
}