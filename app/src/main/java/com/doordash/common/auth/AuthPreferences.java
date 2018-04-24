package com.doordash.common.auth;

import android.content.SharedPreferences;

public class AuthPreferences {

    private static final String KEY_TOKEN = "token";

    private final SharedPreferences mSharedPrefereces;


    public AuthPreferences(SharedPreferences sharedPreferences) {
        mSharedPrefereces = sharedPreferences;
    }

    // TODO Maybe helper method for token?
    public void updateToken(String token) {
        mSharedPrefereces.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getTokenString() {
        return mSharedPrefereces.getString(KEY_TOKEN, null);
    }

    public boolean hasToken() {
        // TODO Null
        return mSharedPrefereces.getString(KEY_TOKEN, null) != null;
    }
}
