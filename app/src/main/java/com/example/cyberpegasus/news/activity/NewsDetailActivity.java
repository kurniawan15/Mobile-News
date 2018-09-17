package com.example.cyberpegasus.news.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyberpegasus.news.R;
import com.example.cyberpegasus.news.fragment.MediaFragment;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewsDetailActivity extends AppBaseActivity {
    MyPageAdapter pageAdapter;
    TextView judul, kategori, isi, tanggalBerita, waktuBerita, lokBerita;

    //URL gambar dan video yang akan diambil untuk ditampilkan pada aplikasi
    //final String URL = "http://192.168.1.114/restAPIBear/public/file/";
    final String URL = "http://192.168.1.241/restAPICoba/public/file/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        judul = (TextView) findViewById(R.id.judulBerita);
        kategori = (TextView) findViewById(R.id.kategoriBerita);
        isi = (TextView) findViewById(R.id.isiBerita);
        tanggalBerita = (TextView) findViewById(R.id.textDate);
        waktuBerita = (TextView) findViewById(R.id.textTime);
        lokBerita = (TextView) findViewById(R.id.textLatLngBerita);

        Bundle b = this.getIntent().getExtras();

        judul.setText(b.getString("JUDUL"));
        kategori.setText(b.getString("KATEGORI"));
        isi.setText(b.getString("ISI"));
        lokBerita.setText(String.valueOf(b.getDouble("LAT_BERITA")) + ", " + String.valueOf(b.getDouble("LONG_BERITA")));

        Calendar cal = Calendar.getInstance();
        cal.setTime((Date) b.getSerializable("TANGGAL"));
        String formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
        String formatedTime = cal.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", cal.get(Calendar.MINUTE));
        System.out.println("formatedDate : " + formatedDate);

        tanggalBerita.setText(formatedDate);
        waktuBerita.setText(formatedTime);
        ArrayList<String> file;
        if(b.getStringArrayList("FILE") != null){
            file = b.getStringArrayList("FILE");
        }else {
            file = new ArrayList<>();
        }



        //file.clear();

        //Data file hardcoded diambil dari API
        //file.add("1534388388803-tangkubanperahu.jpg");
        //file.add("1534387593532-batucinta.jpg");
        //file.add("1534411732341-test.mp4");
        ArrayList<String> tempList = new ArrayList<>();


        Boolean status = cekKoneksi();
        if(status == true ){
            for(int i = 0; i < file.size(); i++) {
                String fileName = file.get(i).toString().substring(file.get(i).toString().lastIndexOf("file") + 5);
                System.out.println(fileName);
                String replacement = URL + fileName;
                tempList.add(replacement);
            }
        }else{

        }

//        file.clear();
        file = tempList;
        List<Fragment> fragments = getFragments(file);

        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(pageAdapter);
    }

    private List<Fragment> getFragments(ArrayList<String> files){
        List<Fragment> fList = new ArrayList<Fragment>();

        for(int i = 0; i < files.size(); i++) {
            String type = files.get(i).toString().substring(files.get(i).toString().length() - 3).toLowerCase();
            System.out.println(type);
            if (type.equals("3gp") || type.equals("mp4")) {
                fList.add(MediaFragment.newInstance(files.get(i).toString(), false));
            }else {
                fList.add(MediaFragment.newInstance(files.get(i).toString(), true));
            }
        }
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

    public boolean cekKoneksi() {
        boolean status;
        final ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnectedOrConnecting () || mobile.isConnectedOrConnecting () ) {
            status = true;
        }
        else {
            Toast.makeText(this, "No Network ", Toast.LENGTH_LONG).show();
            status = false;
        }
        return status;
    }

}
