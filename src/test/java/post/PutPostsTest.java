package post;

import io.restassured.response.Response;
import org.example.client.PostAPI;
import org.example.extension.ExtensionManager;
import org.example.model.Post;
import org.example.utils.ResponseValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.example.utils.TestDataConstant.*;
import static org.example.utils.TestDataGenerator.*;

@ExtendWith(ExtensionManager.class)
public class PutPostsTest {

    private final PostAPI service = new PostAPI();
    private final ResponseValidator responseValidator = new ResponseValidator();

    @Test
    @DisplayName("PUT-запрос. Полное обновление поста с валидными данными")
    public void testUpdatePostSuccessfully() {
        Post requestPost = createPostWithId2();
        Response response = service.putPost(requestPost);

        responseValidator.assertStatus(response, CODE_200);
        responseValidator.assertJsonSchema(response, JSON_SCHEMA);

        Post responcePost = response.as(Post.class);
        responseValidator.validatePostFields(responcePost, requestPost);

    }

    @Test
    @DisplayName("PUT-запрос. Обновление поста по несуществующему id")
    public void testUpdateInvalidIdPost() {
        Post requestPost = createPostWithInvalidId();
        Response response = service.putPost(requestPost);
        responseValidator.assertStatus(response, CODE_404);
        responseValidator.assertEmptyJson(response);
    }

    @Test
    @DisplayName("PUT-запрос. Обновление поста по полю body")
    public void testUpdateFieldTitlePost() {
        Post requestPost = updatePostsBody(2);
        Response response = service.putPost(requestPost);
        responseValidator.assertStatus(response, CODE_200);

        Post responcePost = response.as(Post.class);
        responseValidator.validateNullFields(responcePost, requestPost);
    }

    @Test
    @DisplayName("PUT-запрос. Обновление поста с некорректными данными")
    public void testUpdatePostWithInvalidData() {
        Post requestPost = updatePostWithInvalidData(2);

        Response response = service.putPost(requestPost);

        // jsonplaceholder всё равно вернёт 200, но контрактно проверяем что ожидаем ошибку
        responseValidator.assertStatus(response, CODE_400);
        responseValidator.assertJsonSchema(response, JSON_SCHEMA);
    }

    @Test
    @DisplayName("PATCH-запрос. Частичное обновление поста с валидным ID")
    public void testPartialUpdatePost() {
        Post requestPost = partialUpdatePostTitle();
        Response response = service.pathPost(requestPost);

        responseValidator.assertStatus(response, CODE_200);
        responseValidator.assertJsonSchema(response, JSON_SCHEMA);

        Post responcePost = response.as(Post.class);
        responseValidator.checkPartialUpdatePostTitle(responcePost, requestPost);
    }

    @Test
    @DisplayName("PATCH-запрос. Изменение поста по невалидному id")
    public void testInvalidIdPost() {
        Post requestPost = createPostWithInvalidId();
        Response response = service.pathPost(requestPost);
        responseValidator.assertStatus(response, CODE_404);
    }
}
