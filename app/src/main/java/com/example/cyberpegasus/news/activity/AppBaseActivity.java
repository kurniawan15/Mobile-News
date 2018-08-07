package com.example.cyberpegasus.news.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.cyberpegasus.news.R;

/**
 * Created by USER on 7/22/2018.
 */

public abstract class AppBaseActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.app_base_layout);// The base layout that contains your navigation drawer.
        view_stub = (FrameLayout) findViewById(R.id.view_stub);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerMenu = navigation_view.getMenu();
        for(int i = 0; i < drawerMenu.size(); i++) {
            drawerMenu.getItem(i).setOnMenuItemClickListener(this);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* Override all setContentView methods to put the content view to the FrameLayout view_stub
     * so that, we can make other activity implementations looks like normal activity subclasses.
     */
    @Override
    public void setContentView(int layoutResID) {
        if (view_stub != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, view_stub, false);
            view_stub.addView(stubView, lp);
        }
    }

    @Override
    public void setContentView(View view) {
        if (view_stub != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view_stub.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (view_stub != null) {
            view_stub.addView(view, params);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }else {
            switch (item.getItemId()) {
                //Ketika menekan menu filter
                case R.id.filterMenu:
                    //Jika sedang tidak membuka activity Dashboard maka pindah ke Dashboard
                    if (!DashboardActivity.active) {
                        Intent dashboardIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(dashboardIntent);
                        DashboardActivity.filterOpen = true;
                        finish();
                    }else {
                        //Jika layout filter masih tertutup maka lakukan animasi buka
                        if (DashboardActivity.filterOpen == false) {
                            Animation animationLayout = AnimationUtils.loadAnimation(this, R.anim.animation);
                            DashboardActivity a = new DashboardActivity();
                            RelativeLayout re = (RelativeLayout) findViewById(R.id.filterLayout);
                            DashboardActivity.btnFinishFilter = (Button) findViewById(R.id.buttonFinishFilter);
                            DashboardActivity.wktDari = (EditText) findViewById(R.id.waktuDari);
                            DashboardActivity.wktSampai = (EditText) findViewById(R.id.waktuSampai);
                            a.animateOpen(animationLayout,re);
                            DashboardActivity.filterOpen = true;
                        }else {
                        //Jika layout filter sudah terbuka maka lakukan animasi tutup
                            Animation animationLayout = AnimationUtils.loadAnimation(this, R.anim.animation_close);
                            DashboardActivity a = new DashboardActivity();
                            RelativeLayout re = (RelativeLayout) findViewById(R.id.filterLayout);
                            a.animateClose(animationLayout,re);
                            DashboardActivity.filterOpen = false;
                        }
                    }
                break;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history:
                Intent dashboardIntent = new Intent(AppBaseActivity.this, DashboardActivity.class);
                startActivity(dashboardIntent);
                finish();
            break;
            case R.id.logout:
                Intent logoutIntent = new Intent(AppBaseActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
                finish();
            break;
            case R.id.about:
                Intent aboutIntent = new Intent(AppBaseActivity.this, About.class);
                startActivity(aboutIntent);
                finish();
            break;
        }
        return false;
    }

}