package com.example.cyberpegasus.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.example.cyberpegasus.news.Adapter.DashboardAdapter;
import com.example.cyberpegasus.news.activity.AppBaseActivity;
import com.example.cyberpegasus.news.model.Data;
import com.example.cyberpegasus.news.model.DataList;
import com.example.cyberpegasus.news.network.GetDataService;
import com.example.cyberpegasus.news.network.RetrofitInstance;


public class DashboardActivity extends AppBaseActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ImageButton imgButton;


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

        recyclerView.setAdapter(adapter);
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
                generateDataList(response.body().getDataList());
            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
