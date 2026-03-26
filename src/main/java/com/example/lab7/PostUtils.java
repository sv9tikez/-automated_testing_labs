package com.example.lab7;

public class PostUtils {

    public static boolean isValidTitle(String title) {
        return title != null && title.length() > 3;
    }

    public static int calculateTitleLength(String title) {
        return title.length();
    }
}
