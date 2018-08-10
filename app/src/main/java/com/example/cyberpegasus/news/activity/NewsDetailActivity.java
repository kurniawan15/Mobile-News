package com.example.cyberpegasus.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyberpegasus.news.R;
import com.example.cyberpegasus.news.model.Data;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsDetailActivity extends AppBaseActivity {
    TextView judul, kategori, isi, tanggalBerita;
    ImageView foto;

    //URL gambar yang akan diambil untuk ditampilkan pada aplikasi
    final String URL = "http://192.168.1.241/restAPI/public/file/";
    String urlFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        judul = (TextView) findViewById(R.id.judulBerita);
        kategori = (TextView) findViewById(R.id.kategoriBerita);
        isi = (TextView) findViewById(R.id.isiBerita);
        tanggalBerita = (TextView) findViewById(R.id.textDate);
        foto = (ImageView) findViewById(R.id.fotoBerita);

        Bundle b = this.getIntent().getExtras();

        judul.setText(b.getString("JUDUL"));
        kategori.setText(b.getString("KATEGORI"));
        isi.setText(b.getString("ISI"));

        /*String stringTanggal = b.getString("TANGGAL").substring(0, Math.min);
        String[] parts = stringTanggal.split("-");
        String tahun = parts[0];
        String bulan = parts[1];
        String tanggal = parts[2];

        stringTanggal = tanggal + "/" + bulan + "/" + tahun;*/

        tanggalBerita.setText(b.getString("TANGGAL"));
        ArrayList<String> file = b.getStringArrayList("FILE");

        //Memeriksa apakah list terkirim dari DashboardAdapter
        for(String fileName : file) {
            urlFile = null;
            urlFile = URL + fileName;
            //System.out.println(fileName + "\n");
        }

        loadImageFromUrl(urlFile);

        /*//Menggunakan Serializeable
        Data data = (Data) getIntent().getSerializableExtra("BERITA");

        judul.setText(data.getJudul());
        kategori.setText(data.getCategory());
        isi.setText(data.getIsi());
        tanggal.setText(data.getDateBerita().toString());*/
    }

    public void loadImageFromUrl(String url) {
        Picasso.with(this).load(url)
                .into(foto,new com.squareup.picasso.Callback() {

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
