package com.droidzepp.networkwatcher.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.droidzepp.networkwatcher.model.WatchedApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nijat on 01/10/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NetworkWatcher";

    private static final String CREATE_WATCHEDAPPS_TABLE = "CREATE TABLE WATCHED_APPS " +
            "(UID INTEGER PRIMARY KEY, APP_NAME TEXT, WIFI_SELECTED TEXT, MOBDATA_SELECTED TEXT)";
    private static final String SELECT_WATCHED_APP = "SELECT UID, APP_NAME, WIFI_SELECTED, MOBDATA_SELECTED FROM WATCHED_APPS WHERE UID = ?";
    private static final String SELECT_ALL_WATCHED_APPS = "SELECT  UID, APP_NAME, WIFI_SELECTED, MOBDATA_SELECTED FROM WATCHED_APPS";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WATCHEDAPPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addAppToWatch(WatchedApp app) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("UID", app.getUid());
        values.put("APP_NAME", app.getAppName());
        values.put("WIFI_SELECTED", app.isWifiSelected() ? "1" : "0");
        values.put("MOBDATA_SELECTED", app.isMobDataSelected() ? "1" : "0");

        db.insert("WATCHED_APPS", null, values);
        db.close();
    }

    public WatchedApp getWatchedApp(int uid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_WATCHED_APP, new String[]{uid + ""});

        if (cursor.getCount() == 0) {
            return null;
        }

        WatchedApp watchedApp = generateWatchedApp(cursor);
        cursor.close();
        db.close();
        return watchedApp;
    }

    public void updateWatchedApp(WatchedApp watchedApp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("APP_NAME", watchedApp.getAppName());
        values.put("WIFI_SELECTED", watchedApp.isWifiSelected() ? "1" : "0");
        values.put("MOBDATA_SELECTED", watchedApp.isMobDataSelected() ? "1" : "0");

        db.update("WATCHED_APPS", values, "UID = ?",
                new String[]{String.valueOf(watchedApp.getUid())});
    }

    public void deleteWatchedApp(WatchedApp watchedApp) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("WATCHED_APPS", "UID = ?",
                new String[]{String.valueOf(watchedApp.getUid())});
        db.close();
    }

    private WatchedApp generateWatchedApp(Cursor cursor) {
        WatchedApp watchedApp = new WatchedApp();
        watchedApp.setUid(cursor.getInt(0));
        watchedApp.setAppName(cursor.getString(1));
        watchedApp.setWifiSelected(strToBool(cursor.getString(2)));
        watchedApp.setMobDataSelected(strToBool(cursor.getString(3)));
        return watchedApp;
    }

    private boolean strToBool(String str) {
        return "1".equals(str);
    }

    public List<WatchedApp> getAllWatchedApps() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL_WATCHED_APPS, null);

        List<WatchedApp> appList = new ArrayList<>();
        if (cursor.getCount() == 0)
            return appList;

        if (cursor.moveToFirst()) {
            do {
                WatchedApp watchedApp = generateWatchedApp(cursor);
                appList.add(watchedApp);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return appList;
    }

}
