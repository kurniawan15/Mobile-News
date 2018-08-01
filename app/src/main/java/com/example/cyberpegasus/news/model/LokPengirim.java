package com.example.cyberpegasus.news.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Cyber Pegasus on 8/1/2018.
 */

public class LokPengirim {

    @SerializedName("lan")
    @Expose
    private String lan;
    @SerializedName("long")
    @Expose
    private String _long;

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

}
