package com.example.cyberpegasus.news.database;

/**
 * Created by Cyber Pegasus on 7/17/2018.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class DatabaseHelper extends SQLiteOpenHelper {

    //Constants for Database name, table name, and column names
    public static final String DB_NAME = "skm";
    public static final String TABLE_NAME = "berita";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PENGIRIM = "PENGIRIM";
    public static final String COLUMN_JUDUL = "JUDUL";
    public static final String COLUMN_DATE_BERITA ="WAKTUBERITA" ;
    public static final String COLUMN_CATEGORY = "KATAGORI";
    public static final String COLUMN_ISI = "ISI";
    public static final String COLUMN_DATE_PENGIRIM = "DATEPENGIRIM";
    public static final String COLUMN_LOK_PENGIRIM_LAN = "LANPENGIRIM";
    public static final String COLUMN_LOK_PENGIRIM_LNG = "LNGPENGIRIM";
    public static final String COLUMN_LOK_BERITA_LAN = "LANBERITA";
    public static final String COLUMN_LOK_BERITA_LNG = "LNGBERITA";
    public static final String COLUMN_FILE = "FILE";
    public static final String COLUMN_STATUS = "status";

    //database version
    private static final int DB_VERSION = 1;

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //Membuat database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_JUDUL + " VARCHAR,"
                + COLUMN_PENGIRIM + " VARCHAR,"
                + COLUMN_DATE_BERITA + " DATETIME,"
                + COLUMN_CATEGORY + " VARCHAR,"
                + COLUMN_ISI + " VARCHAR,"
                + COLUMN_DATE_PENGIRIM + " DATETIME,"
                + COLUMN_LOK_BERITA_LAN + " DOUBLE,"
                + COLUMN_LOK_BERITA_LNG + " DOUBLE,"
                + COLUMN_LOK_PENGIRIM_LAN + " DOUBLE,"
                + COLUMN_LOK_PENGIRIM_LNG + " DOUBLE,"
                + COLUMN_FILE + " VARCHAR,"
                + COLUMN_STATUS + " TINYINT);";
        db.execSQL(sql);
    }

    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Persons";
        db.execSQL(sql);
        onCreate(db);
    }

    /*
    * Metode ini mengambil dua argumen
    * Yang pertama adalah nama yang harus disimpan
    * yang kedua adalah status
    * 0 berarti nama disinkronkan dengan server
    * 1 artinya nama tidak disinkronkan dengan server
    * */
    public boolean addDataLokal(String judul, String pengirim, Date date_Pengirim, String category,
                           String isi, Date date_berita, Double lok_Pengirim_Lan, Double lok_Pengirim_Lng,
                           Double lok_Berita_Lan, Double lok_Berita_Lng, ArrayList<String> file, int status) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        boolean hasil = true;
        try {

            Date dDatePengirim = format.parse(String.valueOf(date_Pengirim));
            Date dDateBerita = format.parse(String.valueOf(date_berita));

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_JUDUL, judul);
            contentValues.put(COLUMN_PENGIRIM, pengirim);
            contentValues.put(COLUMN_DATE_PENGIRIM, String.valueOf(dDatePengirim));
            contentValues.put(COLUMN_CATEGORY, category);
            contentValues.put(COLUMN_ISI, isi);
            contentValues.put(COLUMN_DATE_BERITA, String.valueOf(dDateBerita));
            contentValues.put(COLUMN_LOK_PENGIRIM_LAN, lok_Pengirim_Lan);
            contentValues.put(COLUMN_LOK_PENGIRIM_LNG, lok_Pengirim_Lng);
            contentValues.put(COLUMN_LOK_BERITA_LAN, lok_Berita_Lan);
            contentValues.put(COLUMN_LOK_BERITA_LNG, lok_Berita_Lng);
            contentValues.put(COLUMN_FILE, String.valueOf(file));
            contentValues.put(COLUMN_STATUS, status);


        long result =  db.insert(TABLE_NAME, null, contentValues);

            if (result == -1){
                hasil = false;
            }else{
                hasil = true;
            }

            }catch (ParseException e) {
                e.printStackTrace();
            }
        //db.close();

        return hasil;
    }

    /*Metode ini mengambil dua argumen
    * Yang pertama adalah id dari nama untuk itu
    * kita harus memperbarui status sinkronisasi
    * dan yang kedua adalah status yang akan diubah
    * */

    public boolean updateDataStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }

    /*
    * metode ini akan memberi kita semua data yang disimpan dalam sqlite
    * */
    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    /*
    * metode ini untuk mendapatkan semua nama yang tidak disinkronkan
    * sehingga kita bisa menyinkronkannya dengan basis data
    * */
    public Cursor getDataHistori(String pengirim) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUS + " = "+ pengirim +"; ";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getUnsyncedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

}
