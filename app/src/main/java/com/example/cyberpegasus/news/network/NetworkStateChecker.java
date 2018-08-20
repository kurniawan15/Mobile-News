package com.example.cyberpegasus.news.network;

/**
 * Created by Cyber Pegasus on 7/17/2018.
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cyberpegasus.news.activity.BodyReportActivity;
import com.example.cyberpegasus.news.activity.MainActivity;
import com.example.cyberpegasus.news.database.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class NetworkStateChecker extends BroadcastReceiver {

    //context and database helper object
    private Context context;
    private DatabaseHelper db;
    BodyReportActivity bodyReportActivity = new BodyReportActivity();
    Integer NAME_SYNCED_WITH_SERVER = 1;



    @Override
    public void onReceive(Context context, Intent intent) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss", Locale.US);
        this.context = context;

        db = new DatabaseHelper(context);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //jika ada jaringan
        if (activeNetwork != null) {
            //jika terhubung ke wifi atau paket data seluler
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                //mendapatkan semua nama yang tidak disinkronkan
                Cursor cursor = db.getUnsyncedNames();
                if (cursor.moveToFirst()) {
                    do {
                        Integer id =cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                        String sdatePengirim = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_BERITA));
                        String sdateBerita = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_PENGIRIM));
                        String sFile = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FILE));
                        ArrayList<String> listFile = new ArrayList<>(Arrays.asList(sFile.split(",")));
                        try {
                            Date dDatePengirim = format.parse(sdatePengirim);
                            Date dDateBerita = format.parse(sdateBerita);

                        //memanggil metode untuk menyimpan nama yang tidak disinkronkan ke MySQL

                        bodyReportActivity.addToAPI(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOK_PENGIRIM_LAN)),
                                cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOK_PENGIRIM_LNG)),
                                cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOK_BERITA_LAN)),
                                cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOK_BERITA_LNG)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PENGIRIM)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JUDUL)),
                                dDatePengirim,
                                dDateBerita,
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ISI)),
                                listFile
                                );

                        db.updateDataStatus(id,NAME_SYNCED_WITH_SERVER);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } while (cursor.moveToNext());
                }
            }
        }
    }


}

