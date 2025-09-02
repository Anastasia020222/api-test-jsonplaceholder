package post;

import io.restassured.response.Response;
import org.example.client.PostAPI;
import org.example.extension.ExtensionManager;
import org.example.utils.ResponseValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.example.utils.TestDataConstant.*;

@ExtendWith(ExtensionManager.class)
public class DeletePostsTest {

    private final PostAPI service = new PostAPI();
    private final ResponseValidator responseValidator = new ResponseValidator();

    @Test
    @DisplayName("DELETE-запрос. Удаление поста с существующим ID")
    public void testDeletePost() {
        Response response = service.deletePost(1);
        responseValidator.assertStatus(response, CODE_200);
        responseValidator.assertEmptyJson(response);
    }

    @Test
    @DisplayName("Проверка идемпотентности: повторное удаление того же id")
    public void testDeleteIdempotency() {
        Response response = service.deletePost(2);
        responseValidator.assertStatus(response, CODE_200);
        Response responseTwo = service.deletePost(2);
        responseValidator.assertStatus(responseTwo, CODE_404);
    }
}