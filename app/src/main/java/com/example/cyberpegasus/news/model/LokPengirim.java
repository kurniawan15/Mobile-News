package com.example.cyberpegasus.news.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Cyber Pegasus on 8/1/2018.
 */

public class LokPengirim {

    @SerializedName("lan")
    @Expose
    private Double lan;
    @SerializedName("long")
    @Expose
    private Double _long;

    public LokPengirim(Double lan, Double _long) {
        this.lan = lan;
        this._long = _long;
    }

    public Double getLan() {
        return lan;
    }

    public Double get_long() {
        return _long;
    }

    public void setLan(Double lan) {
        this.lan = lan;
    }

    public void set_long(Double _long) {
        this._long = _long;
    }
}
