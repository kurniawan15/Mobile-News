package com.example.cyberpegasus.news.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.cyberpegasus.news.R;
import com.example.cyberpegasus.news.fragment.MediaFragment;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewsDetailActivity extends AppBaseActivity {
    MyPageAdapter pageAdapter;
    TextView judul, kategori, isi, tanggalBerita, waktuBerita;

    //URL gambar dan video yang akan diambil untuk ditampilkan pada aplikasi
    final String URL = "http://192.168.1.99/restAPIBear/public/file/";
    //final String URL = "http://192.168.1.241/restAPI/public/file/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        judul = (TextView) findViewById(R.id.judulBerita);
        kategori = (TextView) findViewById(R.id.kategoriBerita);
        isi = (TextView) findViewById(R.id.isiBerita);
        tanggalBerita = (TextView) findViewById(R.id.textDate);
        waktuBerita = (TextView) findViewById(R.id.textTime);

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
        file.clear();

        //Data file hardcoded diambil dari API
        file.add("1534388388803-tangkubanperahu.jpg");
        file.add("1534387593532-batucinta.jpg");
        file.add("1534411732341-test.mp4");
        ArrayList<String> tempList = new ArrayList<>();

        //Memeriksa apakah list terkirim dari DashboardAdapter
        for(int i = 0; i < file.size(); i++) {
            String replacement = URL + file.get(i).toString();
            tempList.add(replacement);
        }
        file.clear();
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

}
