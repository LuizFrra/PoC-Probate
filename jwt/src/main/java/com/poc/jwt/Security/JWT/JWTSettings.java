package com.poc.jwt.Security.JWT;

public class JWTSettings {

    private JWTSettings() {

    }

    public static int TOKEN_EXPIRATION_TIME_IN_MINUTES = 60;

    public static String AUTHORIZATION_HEADER = "AUTHORIZATION";

    public static String TOKEN_HEADER_PREFIX = "BEARER ";

    public static String ISSUER = "myapi.com";

    public static String ROLES_CLAIM_NAME = "roles";

}
