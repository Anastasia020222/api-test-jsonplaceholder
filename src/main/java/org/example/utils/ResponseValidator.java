package org.example.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.Post;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.example.utils.TestDataConstant.BODY_USER_TWO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ResponseValidator {

    @Step("Проверяем статус код ответа")
    public void assertStatus(Response response, int expected) {
        assertEquals(expected, response.getStatusCode(),
                String.format("Ожидался статус %d, но пришел %d. Тело ответа: %s",
                        expected, response.getStatusCode(), response.getBody().asString()));
    }

    @Step("Проверяем, что вернулся пустой json")
    public void assertEmptyJson(Response response) {
        assertEquals("{}", response.getBody().asString().trim(),
                "Ожидался пустой JSON при запросе поста с ID = 0");
    }

    @Step("Проверка ответа на соответствие JSON-схеме {schemaFile}")
    public void assertJsonSchema(Response response, String schemaFile) {
        assertAll(
                "Проверка JSON ответа на соответствие схеме",
                () -> assertThat(
                        "Структура ответа не соответствует " + schemaFile,
                        response.getBody().asString(),
                        matchesJsonSchemaInClasspath(schemaFile)
                )
        );
    }

    @Step("Проверка полей в json ответа")
    public void validatePostFields(Post response, Post request) {
        assertAll("Проверка полей созданного поста",
                () -> assertNotNull(response.getId(), "ID должен быть присвоен"),
                () -> assertEquals(request.getUserId(), response.getUserId(), "userID поста неверный"),
                () -> assertEquals(request.getTitle(), response.getTitle(), "Title поста неверный"),
                () -> assertEquals(request.getBody(), response.getBody(), "Body поста неверный")
        );
    }

    public void validateNullFields(Post response, Post request) {
        assertAll("Проверка полей созданного поста",
                () -> assertEquals(request.getId(), response.getId(), "Поле 'body' должно отсутствовать"),
                () -> assertNull(response.getTitle(), "Поле 'title' должно отсутствовать"),
                () -> assertNull(response.getUserId(), "Поле 'userId' должно отсутствовать"),
                () -> assertEquals(request.getBody(), response.getBody(), "Поле 'body' неверное")
        );
    }

    public void checkPartialUpdatePostTitle(Post response, Post request) {
        assertEquals(request.getTitle(), response.getTitle(), "Title не обновился и не соответствует: " + request.getTitle());
        assertEquals(BODY_USER_TWO, response.getBody(), "Body неверное");
        assertEquals(request.getId(), response.getId(), "ID не соответствует: " + request.getId());
    }
}
