package com.example.cyberpegasus.news;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
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
                    "heading " + i, 2
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

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
