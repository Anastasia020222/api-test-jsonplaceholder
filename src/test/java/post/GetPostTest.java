package post;

import io.restassured.response.Response;
import org.example.client.PostAPI;
import org.example.extension.ExtensionManager;
import org.example.model.Post;
import org.example.utils.ResponseValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.example.utils.TestDataConstant.*;
import static org.example.utils.TestDataGenerator.createPostWithId2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ExtensionManager.class)
public class GetPostTest {

    private final PostAPI service = new PostAPI();
    private final ResponseValidator responseValidator = new ResponseValidator();

    @Test
    @DisplayName("GET-Запрос. Получение списка постов")
    public void testGetAllPosts() {
        Response response = service.getAllPost();
        responseValidator.assertStatus(response, CODE_200);
        responseValidator.assertJsonSchema(response, JSON_LIST_SCHEMA);

        List<Post> list = response.jsonPath().getList("", Post.class);
        assertEquals(EXPECTED_POST_COUNT, list.size(), "Размер списка не равен 100");
    }

    @Test
    @DisplayName("GET-запрос. Получение поста по валидному ID")
    public void testGetPostById() {
        Response response = service.getPostById(2);

        responseValidator.assertStatus(response, CODE_200);
        responseValidator.assertJsonSchema(response, JSON_SCHEMA);
        Post post = response.as(Post.class);
        responseValidator.validatePostFields(post, createPostWithId2());
    }

    @Test
    @DisplayName("Получение поста с несуществующим id")
    public void testGetByInvalidIdReturns404() {
        Response response = service.getPostById(101);
        responseValidator.assertStatus(response, CODE_404);
        responseValidator.assertEmptyJson(response);
    }

    @Test
    @DisplayName("GET-запрос. Получение поста с ID = 0")
    public void testGetZeroIdReturns404() {
        Response response = service.getPostById(0);
        responseValidator.assertStatus(response, CODE_404);
        responseValidator.assertEmptyJson(response);
    }
}
