package org.example.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.config.Config;

import static io.restassured.RestAssured.given;

public abstract class AbsBaseAPI<T> implements IApiService<T> {

    private final RequestSpecification spec;

    protected abstract String getPath();

    public AbsBaseAPI() {
        spec = given()
                .baseUri(Config.BASE_URL)
                .contentType(ContentType.JSON)
                .log().all();
    }

    @Override
    public Response get(Integer id) {
        return given(spec)
                .when()
                .get(getPath() + "/" + id)
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Override
    public Response post(T body) {
        return given(spec)
                .body(body)
                .when()
                .post(getPath())
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Override
    public Response put(T body, Integer id) {
        return given(spec)
                .body(body)
                .when()
                .put(getPath() + "/" + id)
                .then()
                .log().all()
                .extract().response();
    }

    @Override
    public Response path(T body, Integer id) {
        return given(spec)
                .body(body)
                .when()
                .patch(getPath() + "/" + id)
                .then().log().all()
                .extract()
                .response();
    }

    @Override
    public Response delete(Integer id) {
        return given(spec)
                .when()
                .delete(getPath() + "/" + id)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response get() {
        return given(spec)
                .when()
                .get(getPath())
                .then()
                .log().all()
                .extract()
                .response();
    }
}
