package com.example.cyberpegasus.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyberpegasus.news.R;
import com.example.cyberpegasus.news.fragment.MediaFragment;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewsDetailActivity extends AppBaseActivity {
    MyPageAdapter pageAdapter;
    TextView judul, kategori, isi, tanggalBerita, waktuBerita;
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
        waktuBerita = (TextView) findViewById(R.id.textTime);
        //foto = (ImageView) findViewById(R.id.fotoBerita);

        Bundle b = this.getIntent().getExtras();

        judul.setText(b.getString("JUDUL"));
        kategori.setText(b.getString("KATEGORI"));
        isi.setText(b.getString("ISI"));

        Calendar cal = Calendar.getInstance();
        cal.setTime((Date) b.getSerializable("TANGGAL"));
        String formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
        String formatedTime = cal.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", cal.get(Calendar.MINUTE));
        System.out.println("formatedDate : " + formatedDate);

        tanggalBerita.setText(formatedDate);
        waktuBerita.setText(formatedTime);

        ArrayList<String> file = b.getStringArrayList("FILE");

        //Memeriksa apakah list terkirim dari DashboardAdapter
        for(String fileName : file) {
            urlFile = null;
            urlFile = URL + fileName;
            //System.out.println(fileName + "\n");
        }

        //loadImageFromUrl(urlFile);
        List<Fragment> fragments = getFragments();

        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        //viewPager.setAdapter(new SwipeAdapter(this));
        viewPager.setAdapter(pageAdapter);

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

    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(MediaFragment.newInstance(R.drawable.berita_dummy, true));
        fList.add(MediaFragment.newInstance(R.drawable.berita_dummy, true));
        fList.add(MediaFragment.newInstance(R.drawable.berita_dummy, true));
        fList.add(MediaFragment.newInstance(R.raw.test, false));
        return fList;
    }



    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        private int[] mResources;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments ) {
            super(fm);
            this.fragments = fragments;
        }
        @Override
        public Fragment getItem(int position)
        {
            return this.fragments.get(position);
        }

        @Override
        public int getCount()
        {
            return this.fragments.size();
        }
    }

}
