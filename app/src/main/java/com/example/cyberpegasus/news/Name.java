package com.example.cyberpegasus.news;

/**
 * Created by Cyber Pegasus on 7/17/2018.
 */

public class Name {
    private String name;
    private int status;
    private String body;

    public String getBody() {
        return body;
    }

    public Name(String name, int status, String body) {
        this.name = name;
        this.status = status;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }
}

