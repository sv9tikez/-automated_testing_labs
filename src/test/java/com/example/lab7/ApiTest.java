package com.example.lab7;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

class ApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    void getAllPosts() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200);
    }

    @Test
    void getSinglePost() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    void createPost() {
        given()
                .body("{\"title\":\"test\",\"body\":\"body\",\"userId\":1}")
                .when()
                .post("/posts")
                .then()
                .statusCode(201);
    }

    @Test
    void updatePost() {
        given()
                .body("{\"title\":\"updated\"}")
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200);
    }

    @Test
    void deletePost() {
        when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }
}
