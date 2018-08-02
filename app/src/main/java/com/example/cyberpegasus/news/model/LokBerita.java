package com.example.cyberpegasus.news.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Cyber Pegasus on 8/1/2018.
 */

public class LokBerita {
    @SerializedName("lan")
    @Expose
    private Double lan;
    @SerializedName("long")
    @Expose
    private Double _long;

    public LokBerita(Double lan, Double _long) {
        this.setLan(lan);
        this.set_long(_long);
    }


    public Double getLan() {
        return lan;
    }

    public void setLan(Double lan) {
        this.lan = lan;
    }

    public Double get_long() {
        return _long;
    }

    public void set_long(Double _long) {
        this._long = _long;
    }
}
