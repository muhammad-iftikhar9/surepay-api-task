package com.surepay.apitests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surepay.model.Comment;
import com.surepay.model.Post;
import com.surepay.model.User;
import com.surepay.restclient.HttpRestClient;
import com.surepay.service.CommentService;
import com.surepay.service.PostService;
import com.surepay.service.UserService;
import com.surepay.utils.ValidationUtils;
import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(AllureJunit5.class)
public class ValidateCommentEmailTest {
    private HttpRestClient httpClient;
    private ObjectMapper objectMapper;
    private UserService userContext;
    private PostService postContext;
    private CommentService commentContext;
    private SoftAssertions softAssertions;

    @BeforeEach
    void setUp() {
        httpClient = new HttpRestClient();
        objectMapper = new ObjectMapper();
        userContext = new UserService(httpClient, objectMapper);
        postContext = new PostService(httpClient, objectMapper);
        commentContext = new CommentService(httpClient, objectMapper);
        softAssertions = new SoftAssertions();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Delphine"})
    @DisplayName("Validate comment emails for user's posts")
    @Description("Verifies that the email addresses in the comments on posts made by a specific user are correctly formatted")
    @Tag("EmailValidation")
    void validateCommentEmailsForUser(String userName) throws Exception {
        Response response = userContext.findUserByUsername(userName);
        assertThat(response.getStatusCode()).as("User API should return 200 status code").isEqualTo(200);
        List<User> userResponse = objectMapper.readValue(response.getBody().asString(), new TypeReference<List<User>>() {
        });
        assertThat(userResponse.isEmpty()).as("User " + userName + " not found").isFalse();
        int userId = userResponse.get(0).getId();
        response = postContext.getPostsByUserId(userId);
        assertThat(response.getStatusCode()).as("Posts API should return 200 status code").isEqualTo(200);
        List<Post> posts = objectMapper.readValue(response.getBody().asString(), new TypeReference<List<Post>>() {
        });
        assertThat(posts.isEmpty()).as("No posts found for " + userName).isFalse();
        for (Post post : posts) {
            response = commentContext.getCommentsByPostId(post.getId());
            assertThat(response.getStatusCode()).as("Comments API should return 200 status code").isEqualTo(200);
            List<Comment> comments = objectMapper.readValue(response.getBody().asString(), new TypeReference<List<Comment>>() {
            });
            for (Comment comment : comments) {
                softAssertions.assertThat(ValidationUtils.isValidEmailFormat(comment.getEmail()))
                        .as("Invalid email format in comment: " + comment.getEmail())
                        .isTrue();
            }
        }
        softAssertions.assertAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {"TestUser12"})
    @DisplayName("Verify empty list for a user that does not exist.")
    @Description("Ensures that the API returns an empty list when requesting for a user that does not exist")
    void verifyNonExistentUser(String userName) throws Exception {
        Response response = userContext.findUserByUsername(userName);
        assertThat(response.getStatusCode()).as("User API should return 200 status code").isEqualTo(200);
        List<User> users = objectMapper.readValue(response.getBody().asString(), new TypeReference<List<User>>() {
        });
        assertThat(users.isEmpty()).as("Expected empty list for user that does not exist").isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {22})
    @DisplayName("Verify empty posts list for user with no posts")
    @Description("Verify that when a user with no posts is queried then the API returns an empty list")
    void verifyUserWithNoPosts(int userId) throws Exception {
        Response response = postContext.getPostsByUserId(userId);
        assertThat(response.getStatusCode()).as("Posts API should return 200 status code").isEqualTo(200);
        List<Post> posts = objectMapper.readValue(response.getBody().asString(), new TypeReference<List<Post>>() {
        });
        assertThat(posts.isEmpty()).as("Expected empty list for non-existent userId").isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {888})
    @DisplayName("Verify empty comment list for post with no comments")
    @Description("Ensures that the API returns an empty list when querying for comments on a post with no comments")
    void verifyPostWithNoComments(int postId) throws Exception {
        Response response = commentContext.getCommentsByPostId(postId);
        assertThat(response.getStatusCode()).as("Comments API should return 200 status code").isEqualTo(200);
        List<Comment> comments = objectMapper.readValue(response.getBody().asString(), new TypeReference<List<Comment>>() {
        });
        assertThat(comments.isEmpty()).as("Expected empty list for non-existent postId").isTrue();
    }
} 