package com.example.lab8;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PlaywrightTest {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;
    private static final String URL = "https://jsonplaceholder.typicode.com";

    @BeforeAll
    static void launch() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @BeforeEach
    void setup() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void teardown() {
        context.close();
    }

    @Test
    void openPosts() {
        page.navigate(URL + "/posts");
        assertTrue(page.content().contains("userId"));
    }

    @Test
    void checkPost() {
        page.navigate(URL + "/posts/1");
        assertTrue(page.content().contains("\"id\": 1"));
    }

    @Test
    void checkAlbums() {
        page.navigate(URL + "/albums");
        assertTrue(page.content().contains("title"));
    }
}
