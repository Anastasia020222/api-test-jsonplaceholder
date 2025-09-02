package org.example.client;

import io.restassured.response.Response;

public interface IApiService<T> {

    Response get(Integer id);

    Response post(T body);

    Response put(T body, Integer id);

    Response path(T body, Integer id);

    Response delete(Integer id);
}
