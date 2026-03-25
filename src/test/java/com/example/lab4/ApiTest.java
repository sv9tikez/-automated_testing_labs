package com.example.lab4;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiTest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    // GET всі записи
    @Test
    @Order(1)
    void getAllPosts_statusCode200() {
        given()
                .when().get("/posts")
                .then().statusCode(200);
    }

    @Test
    @Order(2)
    void getAllPosts_responseIsJsonArray() {
        given()
                .when().get("/posts")
                .then()
                .contentType(ContentType.JSON)
                .body("$", instanceOf(java.util.List.class));
    }

    @Test
    @Order(3)
    void getAllPosts_returns100Items() {
        given()
                .when().get("/posts")
                .then()
                .body("size()", equalTo(100));
    }

    @Test
    @Order(4)
    void getAllPosts_firstItemHasRequiredFields() {
        given()
                .when().get("/posts")
                .then()
                .body("[0].id",     notNullValue())
                .body("[0].userId", notNullValue())
                .body("[0].title",  notNullValue())
                .body("[0].body",   notNullValue());
    }

    @Test
    @Order(5)
    void getAllPosts_idsArePositiveIntegers() {
        given()
                .when().get("/posts")
                .then()
                .body("id", everyItem(greaterThan(0)));
    }

    // GET (Конкретний запис)
    @Test
    @Order(6)
    void getPostById_statusCode200() {
        given()
                .when().get("/posts/1")
                .then().statusCode(200);
    }

    @Test
    @Order(7)
    void getPostById_returnsCorrectId() {
        given()
                .when().get("/posts/1")
                .then()
                .body("id", equalTo(1));
    }

    @Test
    @Order(8)
    void getPostById_notFound_returns404() {
        given()
                .when().get("/posts/9999")
                .then().statusCode(404);
    }

    @Test
    @Order(9)
    void getPostById_responseContainsTitle() {
        given()
                .when().get("/posts/1")
                .then()
                .body("title", not(emptyOrNullString()));
    }

    @Test
    @Order(10)
    void getPostById_userIdIsPositive() {
        given()
                .when().get("/posts/1")
                .then()
                .body("userId", greaterThan(0));
    }

    // POST
    @Test
    @Order(11)
    void createPost_statusCode201() {
        String body = """
                {
                  "title": "Test Post",
                  "body": "Integration test content",
                  "userId": 10
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/posts")
                .then().statusCode(201);
    }

    @Test
    @Order(12)
    void createPost_responseContainsGeneratedId() {
        String body = """
                {
                  "title": "Test Post",
                  "body": "Integration test content",
                  "userId": 10
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/posts")
                .then()
                .body("id", notNullValue());
    }

    @Test
    @Order(13)
    void createPost_echoesTitle() {
        String body = """
                {
                  "title": "My Title",
                  "body": "some body",
                  "userId": 1
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/posts")
                .then()
                .body("title", equalTo("My Title"));
    }

    @Test
    @Order(14)
    void createPost_echoesUserId() {
        String body = """
                {
                  "title": "T",
                  "body": "B",
                  "userId": 42
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/posts")
                .then()
                .body("userId", equalTo(42));
    }

    @Test
    @Order(15)
    void createPost_responseIsJson() {
        String body = """
                {
                  "title": "T",
                  "body": "B",
                  "userId": 1
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/posts")
                .then()
                .contentType(ContentType.JSON);
    }

    //PUT
    @Test
    @Order(16)
    void updatePost_statusCode200() {
        String body = """
                {
                  "id": 1,
                  "title": "Updated Title",
                  "body": "Updated body",
                  "userId": 1
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().put("/posts/1")
                .then().statusCode(200);
    }

    @Test
    @Order(17)
    void updatePost_echoesUpdatedTitle() {
        String body = """
                {
                  "id": 1,
                  "title": "New Title",
                  "body": "New body",
                  "userId": 1
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().put("/posts/1")
                .then()
                .body("title", equalTo("New Title"));
    }

    @Test
    @Order(18)
    void updatePost_responseContainsId() {
        String body = """
                {
                  "id": 5,
                  "title": "T",
                  "body": "B",
                  "userId": 1
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().put("/posts/5")
                .then()
                .body("id", equalTo(5));
    }

    @Test
    @Order(19)
    void updatePost_responseIsJson() {
        String body = """
                {
                  "id": 1,
                  "title": "T",
                  "body": "B",
                  "userId": 1
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().put("/posts/1")
                .then()
                .contentType(ContentType.JSON);
    }

    @Test
    @Order(20)
    void updatePost_echoesUserId() {
        String body = """
                {
                  "id": 1,
                  "title": "T",
                  "body": "B",
                  "userId": 7
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().put("/posts/1")
                .then()
                .body("userId", equalTo(7));
    }

    // DELETE
    @Test
    @Order(21)
    void deletePost_statusCode200() {
        given()
                .when().delete("/posts/1")
                .then().statusCode(200);
    }
    @Test
    @Order(22)
    void deletePost_responseIsEmptyBody() {
        Response response = given()
                .when().delete("/posts/1")
                .then().extract().response();
        String body = response.getBody().asString().replaceAll("\\s", "");
        assertTrue(body.equals("{}") || body.isEmpty(),
                "Expected empty or {} body, got: " + body);
    }
    @Test
    @Order(23)
    void deletePost_differentId_returns200() {
        given()
                .when().delete("/posts/50")
                .then().statusCode(200);
    }
    @Test
    @Order(24)
    void deletePost_responseTimeUnder3Seconds() {
        given()
                .when().delete("/posts/1")
                .then()
                .time(lessThan(3000L));
    }
    @Test
    @Order(25)
    void deletePost_nonExistentId_returns200() {
        given()
                .when().delete("/posts/9999")
                .then().statusCode(200);
    }
}
