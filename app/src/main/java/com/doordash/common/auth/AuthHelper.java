package com.doordash.common.auth;

public class AuthHelper {

    private static final String TOKEN_PREFIX = "JWT ";

    public static String getJWTTokenString(Token token) {
        return TOKEN_PREFIX + token.getTokenString();
    }
}
