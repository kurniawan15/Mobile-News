package com.example.cyberpegasus.news.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JWTToken {
    @SerializedName("token")
    @Expose

    public String token;

    public JWTToken() {
    }


        public String getToken(){
            return token;
        }

        public void setToken(){
            this.token=token;

    }
}
