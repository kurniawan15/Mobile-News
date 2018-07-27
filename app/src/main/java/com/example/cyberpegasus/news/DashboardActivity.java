package com.example.cyberpegasus.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.example.cyberpegasus.news.Adapter.DashboardAdapter;
import com.example.cyberpegasus.news.activity.AppBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppBaseActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ImageButton imgButton;
    Toolbar toolbar;

    private List<Name> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        for(int i=1; i<=10; i++){
            Name item = new Name(
                    "Judul Berita " + i, 0, "Lorem ipsum dolor sit amet, sit regione alterum eligendi ne, vix at possit incorrupte dissentiunt, alia iusto persequeris no duo. Vel an sanctus dignissim, ius ignota mollis vivendum ea, cum no torquatos honestatis. An eius salutandi definiebas mel, elitr fabulas mei ex. Eos solum maluisset in, est nobis affert delicata at. Vim luptatum postulant ei, ne eam solum graeco partiendo."
            );
            listItems.add(item);
        }
        adapter = new DashboardAdapter(listItems, this);
        recyclerView.setAdapter(adapter);

        imgButton = (ImageButton) findViewById(R.id.imageButton);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formIntent = new Intent(DashboardActivity.this, FormActivity.class);
                startActivity(formIntent);
            }
        });
    }
}
