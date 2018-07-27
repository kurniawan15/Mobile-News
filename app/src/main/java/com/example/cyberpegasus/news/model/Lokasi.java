package com.example.cyberpegasus.news.model;

/**
 * Created by Cyber Pegasus on 7/27/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Lokasi {

    @SerializedName("latitude")
    @Expose
    private Float latitude;
    @SerializedName("longitude")
    @Expose
    private Float longitude;


    public Lokasi(Float latitude, Float longitude) {
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }



    @Override
    public String toString() {
        return "Lokasi{" +
                "latitude=" + getLatitude() +
                ", longitude=" + getLongitude() +
                '}';
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}