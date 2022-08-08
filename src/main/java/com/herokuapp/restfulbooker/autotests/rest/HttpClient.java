package com.herokuapp.restfulbooker.autotests.rest;

import java.util.Map;

public interface HttpClient {

    <T> T doGetRequest(String endpoint,
                       Class<T> responseClass,
                       Map<String, Object> requestParams);

    <T> T doPostRequest(String endpoint,
                        Object body,
                        Class<T> responseClass,
                        Map<String, Object> requestParams);

    <T> T doPutRequest(String endpoint,
                       Object body,
                       Class<T> responseClass,
                       Map<String, Object> requestParams);

    <T> T doPatchRequest(String endpoint,
                         Object body,
                         Class<T> responseClass,
                         Map<String, Object> requestParams);

    <T> T doDeleteRequest(String endpoint,
                          Object body,
                          Class<T> responseClass,
                          Map<String, Object> requestParams);

    public <T> T doRequest(String endpoint,
                           Object body,
                           Class<T> responseClass,
                           Map<String, Object> requestParams,
                           String method,
                           String token,
                           int expectedStatusCode);
}
