package com.poc.jwt.Models;

import java.util.Date;

public class JWTResponse {

    public static String succesfullLogin(String token) {
        return "{" +
                "\"token\": \"" + token + "\"" +
                "}";
    }

    public static String badCredentials() {
        long date = new Date().getTime();
        return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Unauthorized\", "
                + "\"message\": \"Authentication failed: bad credentials\", "
                + "\"path\": \"/api/auth\"}";
    }

    public static String accessDenied() {
        long date = new Date().getTime();
        return "{\"timestamp\": " + date + ", "
                + "\"status\": 403, "
                + "\"error\": \"Forbidden\", "
                + "\"message\": \"Forbidden Acess: You haven't permission to access this endpoint.\"}";
    }

    public static String invalidToken() {
        long date = new Date().getTime();
        return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Unauthorized\", "
                + "\"message\": \"Unathourized Token: Your token is invalid.\"}";
    }
}
