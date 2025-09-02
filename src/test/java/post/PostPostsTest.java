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
public class PostPostsTest {

    private final PostAPI service = new PostAPI();
    private final ResponseValidator responseValidator = new ResponseValidator();

    @Test
    @DisplayName("POST-запрос. Создание поста с валидными данными")
    public void testCreatePost() {

        Post requestPost = createValidPost();

        Response response = service.createPost(requestPost);
        responseValidator.assertStatus(response, CODE_201);
        responseValidator.assertJsonSchema(response, JSON_SCHEMA);

        Post responsePost = response.as(Post.class);
        responseValidator.validatePostFields(responsePost, requestPost);
    }

    @Test
    @DisplayName("POST-запрос. Попытка создать пост без обязательных полей")
    public void testCreatePostWithMissingFields() {
        Post invalidPost = invalidPost();
        Response response = service.post(invalidPost);

        // jsonplaceholder всегда возвращает 201, но для демонстрации теста проверяем контракт
        responseValidator.assertStatus(response, CODE_422);
    }
}
