package com.doordash.common.auth;

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("token")
    private String mToken;


    public String getTokenString() {
        return mToken;
    }
}
