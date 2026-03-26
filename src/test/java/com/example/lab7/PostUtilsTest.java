package com.example.lab7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostUtilsTest {

    @Test
    void validTitle() {
        assertTrue(PostUtils.isValidTitle("Hello"));
    }

    @Test
    void shortTitle() {
        assertFalse(PostUtils.isValidTitle("Hi"));
    }

    @Test
    void nullTitle() {
        assertFalse(PostUtils.isValidTitle(null));
    }

    @Test
    void titleLength() {
        assertEquals(5, PostUtils.calculateTitleLength("Hello"));
    }

    @Test
    void emptyTitleLength() {
        assertEquals(0, PostUtils.calculateTitleLength(""));
    }
}
