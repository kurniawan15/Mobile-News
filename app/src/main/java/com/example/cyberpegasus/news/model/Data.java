package com.example.cyberpegasus.news.model;

/**
 * Created by Cyber Pegasus on 7/27/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class Data {
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("video")
    @Expose
    private String video;

    @SerializedName("foto")
    @Expose
    private Object foto;

    @SerializedName("pesan")
    @Expose
    private String laporan;

    @SerializedName("pesan")
    @Expose
    private String pesan;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("date")
    @Expose
    private Date date;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("dari")
    @Expose
    private String dari;

    @SerializedName("__v")
    @Expose
    private Integer v;

    @SerializedName("lokasi")
    @Expose
    private Lokasi lokasi;

    public Data(String pesan, String category, Date date, String type, String dari,Lokasi lokasi) {
        this.laporan = pesan;
        this.category = category;
        this.date = date;
        this.type = type;
        this.dari = dari;
        this.lokasi = lokasi;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", video='" + video + '\'' +
                ", foto=" + foto +
                ", laporan='" + laporan + '\'' +
                ", category='" + category + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", dari='" + dari + '\'' +
                ", v=" + v +
                ", lokasi=" + lokasi +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Object getFoto() {
        return foto;
    }

    public void setFoto(Object foto) {
        this.foto = foto;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getCategory() {
        return category;
    }

    public String getLaporan() {
        return laporan;
    }

    public void setLaporan(String laporan) {
        this.laporan = laporan;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDari() {
        return dari;
    }

    public void setDari(String dari) {
        this.dari = dari;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Lokasi getLokasi() {
        return lokasi;
    }

    public void setLokasi(Lokasi lokasi) {
        this.lokasi = lokasi;
    }
}
