package com.example.lab7;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UiTest {

    WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://jsonplaceholder.typicode.com/posts");
    }

    @Test
    void pageLoaded() {
        assertTrue(driver.getPageSource().contains("userId"));
    }

    @Test
    void containsPostId() {
        assertTrue(driver.getPageSource().contains("\"id\": 1"));
    }

    @Test
    void containsTitle() {
        assertTrue(driver.getPageSource().contains("title"));
    }

    @Test
    void containsBody() {
        assertTrue(driver.getPageSource().contains("body"));
    }

    @Test
    void pageNotEmpty() {
        assertFalse(driver.getPageSource().isEmpty());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}