package com.example.cyberpegasus.news.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Cyber Pegasus on 7/27/2018.
 */

public class Data {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("lok_berita")
    @Expose
    private LokBerita lokBerita;
    @SerializedName("lok_pengirim")
    @Expose
    private LokPengirim lokPengirim;
    @SerializedName("date_pengirim")
    @Expose
    private Date datePengirim;
    @SerializedName("date_berita")
    @Expose
    private Date dateBerita;
    @SerializedName("pengirim")
    @Expose
    private String pengirim;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("isi")
    @Expose
    private String isi;
    @SerializedName("judul")
    @Expose
    private String judul;
    @SerializedName("__v")
    @Expose
    private Integer v;

    @SerializedName("file")
    @Expose
    private List<String> file = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LokBerita getLokBerita() {
        return lokBerita;
    }

    public void setLokBerita(LokBerita lokBerita) {
        this.lokBerita = lokBerita;
    }

    public LokPengirim getLokPengirim() {
        return lokPengirim;
    }

    public void setLokPengirim(LokPengirim lokPengirim) {
        this.lokPengirim = lokPengirim;
    }

    public Date getDatePengirim() {
        return datePengirim;
    }

    public void setDatePengirim(Date datePengirim) {
        this.datePengirim = datePengirim;
    }

    public Date getDateBerita() {
        return dateBerita;
    }

    @Override
    public String toString() {
        return "Data{" +
                "datePengirim=" + datePengirim +
                '}';
    }

    public void setDateBerita(Date dateBerita) {
        this.dateBerita = dateBerita;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public List<String> getFile() {
        return file;
    }

    public void setFile(List<String> file) {
        this.file = file;
    }

    public Data(LokBerita lokBerita, LokPengirim lokPengirim, Date datePengirim, Date dateBerita, String pengirim, String category, String isi, String judul, List<String> file) {
        this.lokBerita = lokBerita;
        this.lokPengirim = lokPengirim;
        this.datePengirim = datePengirim;
        this.dateBerita = dateBerita;
        this.pengirim = pengirim;
        this.category = category;
        this.isi = isi;
        this.judul = judul;
        this.file = file;
    }
}
