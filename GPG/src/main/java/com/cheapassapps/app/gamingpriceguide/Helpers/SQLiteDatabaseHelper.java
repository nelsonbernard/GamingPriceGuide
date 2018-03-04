package com.cheapassapps.app.gamingpriceguide.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.cheapassapps.app.gamingpriceguide.Objects.Console;
import com.cheapassapps.app.gamingpriceguide.Objects.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Nelson on 8/29/2017.
 */

public class SQLiteDatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "mycollection.db";
    public static final String CONSOLE_TABLE_NAME = "CONSOLE";
    public static final String GAME_TABLE_NAME = "GAMES";


    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CONSOLE_TABLE_NAME + " (ID INTEGER PRIMARY KEY, NAME TEXT, IMAGEPATH TEXT, GIANTBOMBID TEXT)");
        db.execSQL("CREATE TABLE " + GAME_TABLE_NAME + "(ID INTEGER PRIMARY KEY, NAME TEXT, IMAGEPATH TEXT, LOOSE TEXT, CIB TEXT, NEW TEXT, GIANTBOMBID TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONSOLE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GAME_TABLE_NAME);
        onCreate(db);
    }
}
