package com.example.cyberpegasus.news;

/**
 * Created by Cyber Pegasus on 7/17/2018.
 */


        import android.app.Activity;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.util.Log;
        import android.widget.Toast;

public class databaseHandler {
    SQLiteDatabase database;
    Activity activity;

    public databaseHandler(Activity activity) {
        this.activity = activity;
        database = activity.openOrCreateDatabase("NEWS", activity.MODE_PRIVATE, null);
        createTable();
    }

    public void createTable()
    {
        //Pembuatan database SQLITE News
        try {
            String qu = "CREATE TABLE IF NOT EXISTS JUDUL(name varchar(100)," +
                    "ISI_NEWS varchar(1000), " +
                    "KODE_NEWS varchar(100) primary key, WAKTU_EVENT datetime,WAKTU_PENGIRIM datetime default current_timestamp," +
                    "LAN_PENGIRIM varchar(100),LONG_PENGIRIM varchar(100)," +
                    "LAN_EVENT varchar(100),LONG_EVENT varchar(100),KATAGORI varchar(100),);";
            database.execSQL(qu);
        }catch (Exception e)
        {
            Toast.makeText(activity,"Error Occured for create table",Toast.LENGTH_LONG).show();
        }

    }

    public boolean execAction(String qu)
    {
        Log.i("databaseHandler", qu);
        try {
            database.execSQL(qu);
        }catch (Exception e)
        {
            Log.e("databaseHandler", qu);
            Toast.makeText(activity,"Error Occured for execAction",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    Cursor execQuery(String qu)
    {
        try {
            return database.rawQuery(qu,null);
        }catch (Exception e)
        {
            Log.e("databaseHandler", qu);
//            Toast.makeText(activity,"Error Occured for execAction",Toast.LENGTH_LONG).show();
        }
        return null;
    }
}

