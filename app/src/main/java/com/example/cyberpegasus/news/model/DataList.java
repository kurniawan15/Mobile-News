package com.example.cyberpegasus.news.model;


/**
 * Created by Cyber Pegasus on 7/23/2018.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataList {
    @SerializedName("data")
    private ArrayList<Data> dataList;

    @SerializedName("message")
    String Msg;


    public ArrayList<Data> getDataList() {
        return dataList;
    }

    public void setDataArrayList(ArrayList<Data> dataArrayList) {
        this.dataList = dataArrayList;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }



}
