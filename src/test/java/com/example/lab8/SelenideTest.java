package com.example.lab8;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

class SelenideTest {

    private static final String URL = "https://jsonplaceholder.typicode.com";

    @Test
    void openPosts() {
        open(URL + "/posts");
        $("body").shouldHave(text("userId"));
    }

    @Test
    void checkSinglePost() {
        open(URL + "/posts/1");
        $("body").shouldHave(text("\"id\": 1"));
    }

    @Test
    void checkComments() {
        open(URL + "/comments");
        $("body").shouldHave(text("email"));
    }
}