package com.example.cyberpegasus.news.model;

/**
 * Created by Cyber Pegasus on 7/27/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Lokasi {

    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;


    public Lokasi(Double latitude, Double longitude) {
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}