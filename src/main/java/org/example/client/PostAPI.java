package org.example.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.Post;

public class PostAPI extends AbsBaseAPI<Post> {

    private static final String PATH = "/posts";

    @Override
    protected String getPath() {
        return PATH;
    }

    @Step("Отправление POST-запроса для создания нового поста")
    public Response createPost(Post post) {
        return post(post);
    }

    @Step("Отправление GET-запроса для получения списка постов")
    public Response getAllPost() {
        return get();
    }

    @Step("Отправление GET-запроса для получения поста с ID {id}")
    public Response getPostById(Integer id) {
        return get(id);
    }

    @Step("Отправление PUT-запроса для полного обновления поста с ID {id}")
    public Response putPost(Post body) {
        return put(body, body.getId());
    }

    @Step("Отправление PATCH-запроса для частичного обновления поста с ID {id}")
    public Response pathPost(Post post) {
        return path(post, post.getId());
    }

    @Step("Отправление DELETE-запроса для удаления поста с ID {id}")
    public Response deletePost(Integer id) {
        return delete(id);
    }
}
