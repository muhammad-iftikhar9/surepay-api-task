package com.surepay.restclient;

import com.surepay.utils.ConfigurationManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HttpRestClient {
    private final String baseUrl;
    private RequestSpecification request;

    public HttpRestClient() {
        ConfigurationManager config = ConfigurationManager.getInstance();
        this.baseUrl = config.getApiBaseUrl();
        request = RestAssured.given();
        request.baseUri(baseUrl);
    }

    public Response get(String path, String paramName, String paramValue) {
        return request.param(paramName, paramValue).get(path);
    }

    public Response get(String path, String paramName, int paramValue) {
        return request.param(paramName, paramValue).get(path);
    }
}