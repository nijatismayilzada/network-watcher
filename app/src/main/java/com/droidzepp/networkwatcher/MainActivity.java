package com.droidzepp.networkwatcher;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);

        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> listApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        ArrayList<ListElement> apps = new ArrayList<>();

        for (ApplicationInfo app : listApps) {
            if (db.getWatchedApp(app.uid) != null)
                apps.add(new ListElement(app.uid, app.packageName, db.getWatchedApp(app.uid).isWifiSelected(), db.getWatchedApp(app.uid).isMobDataSelected()));
            else
                apps.add(new ListElement(app.uid, app.packageName, false, false));
        }

        MyArrayAdapter adapter = new MyArrayAdapter(this, R.layout.entry, apps, db);
        setListAdapter(adapter);

        Intent i = new Intent(MainActivity.this, WatcherService.class);
        i.putExtra("database", "aaa");
        MainActivity.this.startService(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
