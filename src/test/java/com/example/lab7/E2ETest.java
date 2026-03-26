package com.example.lab7;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.*;

class E2ETest {

    private static final String URL = "https://jsonplaceholder.typicode.com";

    @Test
    void checkCommentsForPost() {
        given()
                .pathParam("postId", 1)
                .when()
                .get(URL + "/posts/{postId}/comments")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", greaterThan(0))
                .body("[0].postId", equalTo(1))
                .body("[0].email", notNullValue());
    }

    @Test
    void filterPostsByUserId() {
        given()
                .queryParam("userId", 5)
                .when()
                .get(URL + "/posts")
                .then()
                .statusCode(200)
                .body("userId", everyItem(equalTo(5)))
                .body("size()", is(10));
    }

    @Test
    void getNonExistentPostReturns404() {
        int nonExistentId = 9999;

        given()
                .when()
                .get(URL + "/posts/" + nonExistentId)
                .then()
                .statusCode(404);
    }

    @Test
    void patchPostTitle() {
        String newTitle = "Partially Updated Title";

        given()
                .contentType("application/json")
                .body("{\"title\":\"" + newTitle + "\"}")
                .when()
                .patch(URL + "/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo(newTitle))
                .body("userId", notNullValue())
                .body("body", notNullValue());
    }

    @Test
    void postLifecycleTest() {
        String newURL = URL + "/posts/";
        int createdPostId = given()
                .contentType("application/json")
                .body("{\"title\":\"E2E Test\",\"body\":\"Description\",\"userId\":1}")
                .when()
                .post(newURL)
                .then()
                .statusCode(201)
                .extract().path("id");

        int idToOperate = 1;
        given()
                .contentType("application/json")
                .body("{\"id\":" + idToOperate + ",\"title\":\"Updated Title\",\"body\":\"Updated Body\",\"userId\":1}")
                .when()
                .put(newURL + idToOperate)
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"));

        given()
                .when()
                .delete(newURL + idToOperate)
                .then()
                .statusCode(200);
    }
}