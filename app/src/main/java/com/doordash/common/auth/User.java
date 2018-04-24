package com.doordash.common.auth;

import com.google.gson.annotations.SerializedName;

/**
 * POJO for User
 */
public class User {

    @SerializedName("email")
    private String mEmail;

    @SerializedName("password")
    private String mPassword;


    public User(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }


}
