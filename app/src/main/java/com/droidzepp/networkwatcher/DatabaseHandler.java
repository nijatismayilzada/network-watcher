package com.droidzepp.networkwatcher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nijat on 01/10/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "watcher";

    // Contacts table name
    private static final String TABLE_WATCHEDAPPS = "watchedApps";

    // Contacts Table Columns names
    private static final String KEY_UID = "uid";
    private static final String KEY_NAME = "name";
    private static final String KEY_WIFI = "wifi";
    private static final String KEY_MOBDATA = "mobdata";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_WATCHEDAPPS_TABLE = "CREATE TABLE " + TABLE_WATCHEDAPPS + "("
                + KEY_UID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_WIFI + " TEXT," + KEY_MOBDATA + " TEXT" + ")";
        db.execSQL(CREATE_WATCHEDAPPS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATCHEDAPPS);

        // Create tables again
        onCreate(db);
    }

    void addAppToWatch(ListElement app){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UID, app.getUID());
        values.put(KEY_NAME, app.getAppName());
        values.put(KEY_WIFI, app.isWifiSelected() ? "1" : "0");
        values.put(KEY_MOBDATA, app.isMobDataSelected() ? "1" : "0");

        // Inserting Row
        db.insert(TABLE_WATCHEDAPPS, null, values);
        db.close(); // Closing database connection
    }

    ListElement getWatchedApp(int uid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WATCHEDAPPS, new String[] { KEY_UID,
                        KEY_NAME, KEY_WIFI, KEY_MOBDATA }, KEY_UID + "=?",
                new String[] { String.valueOf(uid) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.getCount() == 0) {
            ListElement watchedAppp = null;
            return watchedAppp;
        }

        ListElement watchedApp = new ListElement(cursor.getInt(0), cursor.getString(1), strToBool(cursor.getString(2)), strToBool(cursor.getString(3)));
        // return contact
        return watchedApp;
    }

    boolean strToBool(String str){
        if (str.equals("1"))
                return true;
        else return false;

    }

    // Getting All Contacts
    public List<ListElement> getAllWatchedApps() {
        List<ListElement> appList = new ArrayList<ListElement>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WATCHEDAPPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() == 0)
            return appList;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ListElement watchedApp = new ListElement();
                watchedApp.setUID(cursor.getInt(0));
                watchedApp.setAppName(cursor.getString(1));
                if (cursor.getString(2).equals("1"))
                    watchedApp.setWifiSelected(true);
                else watchedApp.setWifiSelected(false);
                if (cursor.getString(3).equals("1"))
                    watchedApp.setMobDataSelected(true);
                else watchedApp.setMobDataSelected(false);
                // Adding contact to list
                appList.add(watchedApp);
            } while (cursor.moveToNext());
        }

        // return contact list
        return appList;
    }

    // Updating single contact
    public int updateWatchedApp(ListElement watchedApp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, watchedApp.getAppName());
        values.put(KEY_WIFI, watchedApp.isWifiSelected() ? "1" : "0");
        values.put(KEY_MOBDATA, watchedApp.isMobDataSelected() ? "1" : "0");

        // updating row
        return db.update(TABLE_WATCHEDAPPS, values, KEY_UID + " = ?",
                new String[] { String.valueOf(watchedApp.getUID()) });
    }

    // Deleting single contact
    public void deleteWatchedApp(ListElement watchedApp) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WATCHEDAPPS, KEY_UID + " = ?",
                new String[] { String.valueOf(watchedApp.getUID()) });
        db.close();
    }

}
