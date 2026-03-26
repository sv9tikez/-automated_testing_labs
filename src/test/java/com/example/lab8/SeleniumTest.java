package com.example.lab8;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

class SeleniumTest {

    private WebDriver driver;
    private static final String URL = "https://jsonplaceholder.typicode.com";

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void openPostsPage() {
        driver.get(URL + "/posts");
        assertTrue(driver.getPageSource().contains("userId"));
    }

    @Test
    void checkPostIdExists() {
        driver.get(URL + "/posts/1");
        assertTrue(driver.getPageSource().contains("\"id\": 1"));
    }

    @Test
    void checkUsersPage() {
        driver.get(URL + "/users");
        assertTrue(driver.getPageSource().contains("username"));
    }
}

