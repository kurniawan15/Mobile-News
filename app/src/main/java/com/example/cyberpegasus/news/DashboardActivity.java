package com.example.cyberpegasus.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

    boolean open = false;
    Button btnFinishFilter;

    public static boolean filterOpen = false;

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

        final RelativeLayout filter = (RelativeLayout) findViewById(R.id.filterLayout);

        if (filterOpen) {
            Animation animationLayout = AnimationUtils.loadAnimation(this, R.anim.animation);
            //Toast.makeText(this, "You clicked filter open", Toast.LENGTH_LONG).show();
            //Untuk animasi filter
            animateOpen(animationLayout, filter);
        }

        final Spinner kategoriSpinner = findViewById(R.id.dropdownFilterKategori);

        ArrayAdapter<CharSequence> adapterKategori = ArrayAdapter.createFromResource(this, R.array.kategori,
                android.R.layout.simple_spinner_item);
        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategoriSpinner.setAdapter(adapterKategori);
        kategoriSpinner.setSelection(0);

        final Spinner urutanSpinner = findViewById(R.id.dropdownFilterUrutan);

        ArrayAdapter<CharSequence> adapterUrutan = ArrayAdapter.createFromResource(this, R.array.urutan,
                android.R.layout.simple_spinner_item);
        adapterUrutan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        urutanSpinner.setAdapter(adapterUrutan);
        urutanSpinner.setSelection(0);

        btnFinishFilter = (Button) findViewById(R.id.buttonFinishFilter);
        btnFinishFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animationLayout = AnimationUtils.loadAnimation(view.getContext(), R.anim.animation_close);
                animateClose(animationLayout, filter);

                //Untuk memeriksa apakah data dari spinner terambil
                kategori = kategoriSpinner.getSelectedItem().toString();
                if (kategori != null) {
                    if (kategori.equals("Semua Kategori")) {
                        adapter = new DashboardAdapter(list);
                        recyclerView.setAdapter(adapter);
                        ((DashboardAdapter) recyclerView.getAdapter()).notifyDataSetChanged();
                    }else {
                        ArrayList<Data> filtered = new ArrayList<>();
                        filtered.addAll(filter(kategori, list));
                        if (filtered == null) {
                            Toast.makeText(DashboardActivity.this, "Tidak Ditemukan !", Toast.LENGTH_SHORT).show();
                        }else {
                            adapter = new DashboardAdapter(filtered);
                            recyclerView.setAdapter(adapter);
                            ((DashboardAdapter) recyclerView.getAdapter()).notifyDataSetChanged();
                        }
                    }
                }
                filterOpen = false;
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

    public ArrayList<Data> filter(String kategori, ArrayList<Data> arrayList) {
        kategori = kategori.toUpperCase();
        ArrayList<Data> filtered = new ArrayList<>();
        for(Data data : arrayList){
            String kategoriDicari = data.getCategory().toUpperCase();
            if(kategoriDicari.contains(kategori))
                filtered.add(data);
        }
        return filtered;
    }

    public void animateClose(Animation animationLayout, RelativeLayout filter) {
        //Toast.makeText(this, "You clicked filter open", Toast.LENGTH_LONG).show();
        //Untuk animasi filter
        filter.setVisibility(LinearLayout.INVISIBLE);
        animationLayout.setDuration(300);
        filter.setAnimation(animationLayout);
        filter.animate();
        animationLayout.start();
    }

    public void animateOpen(Animation animationLayout, RelativeLayout filter) {
        //Toast.makeText(this, "You clicked filter open", Toast.LENGTH_LONG).show();
        //Untuk animasi filter
        filter.setVisibility(LinearLayout.VISIBLE);
        animationLayout.setDuration(300);
        filter.setAnimation(animationLayout);
        filter.animate();
        animationLayout.start();
    }
}
