package com.example.gallery;


public class AuthorizationHelper {
    private static String userName;

    public AuthorizationHelper(String userName) {
        this.userName = userName;
    }

    public static String getUserName() {
        return userName;
    }
}
