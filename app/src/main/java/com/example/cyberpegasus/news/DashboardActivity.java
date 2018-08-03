package com.example.cyberpegasus.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cyberpegasus.news.Adapter.DashboardAdapter;
import com.example.cyberpegasus.news.activity.AppBaseActivity;
import com.example.cyberpegasus.news.model.Data;
import com.example.cyberpegasus.news.model.DataList;
import com.example.cyberpegasus.news.network.GetDataService;
import com.example.cyberpegasus.news.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends AppBaseActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ImageButton imgButton;

    ArrayList<Data> list;

    public static boolean active = false;

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        imgButton = (ImageButton) findViewById(R.id.imageButton);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formIntent = new Intent(DashboardActivity.this, FormActivity.class);
                startActivity(formIntent);
            }
        });
    }

    private void generateDataList(ArrayList<Data> empDataList) {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        adapter = new DashboardAdapter(empDataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DashboardActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        if(AppBaseActivity.kategori != null) {
            System.out.println("ayo Terpanggil");
            filter(AppBaseActivity.kategori);
        }else {
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
        /*Call the method with parameter in the interface to get the data*/
        Call<DataList> call = service.getData();

        /*Log the URL called*/
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<DataList>() {
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                list = response.body().getDataList();
                generateDataList(list);
            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    void filter(String kategori) {
        ArrayList<Data> filtered = new ArrayList<>();
        kategori = kategori.toString().toUpperCase();
        for(int i = 0; i < list.size(); i++) {
            if (list.get(i).getCategory().toUpperCase().contains(kategori)) {
                filtered.add(list.get(i));
            }
        }
        adapter = new DashboardAdapter(filtered);
        recyclerView.setAdapter(adapter);
    }


}
