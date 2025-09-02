package org.example.utils;

import org.example.model.Post;

import java.util.Random;

import static org.example.utils.TestDataConstant.BODY_USER_TWO;

public class TestDataGenerator {

    private static final Random random = new Random();
    private static final int INVALID_ID = -1;
    private static final String EMPTY_STRING = "";

    public static Post createValidPost() {
        return Post.builder()
                .userId(random.nextInt(1000) + 1)
                .title("Valid title test")
                .body("Valid body content")
                .build();
    }

    public static Post createPostWithId2() {
        return Post.builder()
                .id(2)
                .userId(1)
                .title("qui est esse")
                .body(BODY_USER_TWO)
                .build();
    }

    public static Post createPostWithInvalidId() {
        return Post.builder()
                .id(INVALID_ID)
                .title("Valid title test")
                .body("Body without title")
                .build();
    }

    public static Post partialUpdatePostTitle() {
        return Post.builder()
                .id(2)
                .title("Valid title test")
                .build();
    }

    public static Post updatePostsBody(Integer id) {
        return Post.builder()
                .id(id)
                .body("Body without title")
                .build();
    }

    public static Post invalidPost() {
        return Post.builder()
                .title("Body without title")
                .build();
    }

    public static Post updatePostWithInvalidData(Integer id) {
        return Post.builder()
                .id(id)
                .userId(INVALID_ID)
                .title(EMPTY_STRING)
                .body(EMPTY_STRING)
                .build();
    }
}