package com.herokuapp.restfulbooker.autotests.rest;

import com.herokuapp.restfulbooker.autotests.exception.AutotestException;
import com.herokuapp.restfulbooker.autotests.model.AuthRequest;
import com.herokuapp.restfulbooker.autotests.model.AuthResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Component
public class RestAssuredHttpClient implements HttpClient {

    @Value("${booker.auth.username}")
    private String username;

    @Value("${booker.auth.password}")
    private String password;

    private String token;

    @Value("${booker.url}")
    private String baseUrl;

    @Override
    public <T> T doGetRequest(String endpoint, Class<T> responseClass, Map<String, Object> requestParams) {
        return doRequest(endpoint, null, responseClass, requestParams, "GET", null, 200);
    }

    @Override
    public <T> T doPostRequest(String endpoint, Object body, Class<T> responseClass, Map<String, Object> requestParams) {
        return doRequest(endpoint, body, responseClass, requestParams, "POST", null, 200);
    }


    @Override
    public <T> T doPatchRequest(String endpoint, Object body, Class<T> responseClass, Map<String, Object> requestParams) {
        return doRequest(endpoint, body, responseClass, requestParams, "PATCH", getToken(), 200);
    }

    @Override
    public <T> T doPutRequest(String endpoint, Object body, Class<T> responseClass, Map<String, Object> requestParams) {
        return doRequest(endpoint, body, responseClass, requestParams, "PUT", getToken(), 200);
    }

    @Override
    public <T> T doDeleteRequest(String endpoint, Object body, Class<T> responseClass, Map<String, Object> requestParams) {
        return doRequest(endpoint, body, responseClass, requestParams, "DELETE", getToken(), 201);
    }

    public String getToken() {
        if (!StringUtils.hasLength(token)) {
            var endpoint = "/auth";
            var body = AuthRequest.builder().password(password).username(username).build();
            token = doPostRequest(endpoint, body, AuthResponse.class, Map.of()).getToken();
            if (!StringUtils.hasLength(token)) {
                throw new AutotestException("token is null");
            }
        }
        return token;
    }

    public <T> String doErrorRequest(String endpoint,
                                     Object body,
                                     Map<String, Object> requestParams,
                                     String method,
                                     String token,
                                     int expectedStatusCode) {
        requestParams.values().removeAll(Collections.singleton(null));
        var url = baseUrl + endpoint;
        log.info("{} {}, body: {}", method, url, body);

        var spec = createSpec(body, requestParams, token, url);

        return RestAssured
                .given().spec(spec)
                .when().request(method)
                .then().log().ifError()
                .statusCode(expectedStatusCode)
                .extract().body().asPrettyString();
    }

    private RequestSpecification createSpec(Object body, Map<String, Object> requestParams, String token, String url) {
        var spec = new RequestSpecBuilder();
        spec.addHeader("Accept", "application/json");
        spec.setContentType(ContentType.JSON);
        spec.setBaseUri(url);
        spec.addQueryParams(requestParams);
        if (body != null) {
            spec.setBody(body);
        }
        if (token != null) {
            spec.addHeader("Cookie", "token=" + getToken());
        }
        return spec.build();
    }

    public <T> T doRequest(String endpoint,
                           Object body,
                           Class<T> responseClass,
                           Map<String, Object> requestParams,
                           String method,
                           String token,
                           int expectedStatusCode) {
        requestParams.values().removeAll(Collections.singleton(null));
        var url = baseUrl + endpoint;
        log.info("{} {}, body: {}", method, url, body);

        var spec = createSpec(body, requestParams, token, url);

        return RestAssured
                .given().spec(spec)
                .when().request(method)
                .then().log().ifError().contentType("application/json")
                .statusCode(expectedStatusCode)
                .extract().body().as(responseClass);
    }
}
