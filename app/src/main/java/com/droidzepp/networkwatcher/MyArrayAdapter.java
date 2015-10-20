package com.droidzepp.networkwatcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<ListElement> {
    private final Context mContext;
    private ArrayList<ListElement> appList;
    DatabaseHandler db;

    public MyArrayAdapter(Context context, int textViewResourceId,
                          ArrayList<ListElement> appList, DatabaseHandler db) {
        super(context, textViewResourceId, appList);
        this.mContext = context;
        this.appList = new ArrayList<ListElement>();
        this.appList.addAll(appList);
        this.db = db;
    }

    private class ViewHolder {
        TextView appName;
        CheckBox wifi;
        CheckBox mobData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.entry, null);

            holder = new ViewHolder();
            holder.appName = (TextView) convertView.findViewById(R.id.txtAppName);
            holder.wifi = (CheckBox) convertView.findViewById(R.id.chkWifi);
            holder.mobData = (CheckBox) convertView.findViewById(R.id.chkMobile);
            convertView.setTag(holder);

            holder.wifi.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox wf = (CheckBox) v;
                    ListElement a = (ListElement) wf.getTag();
                    a.setWifiSelected(wf.isChecked());
                    if (db.getWatchedApp(a.getUID()) != null) {
                        db.updateWatchedApp(a);
                        if (a.isMobDataSelected() == false && a.isWifiSelected() == false)
                            db.deleteWatchedApp(a);
                    }
                    else
                        db.addAppToWatch(a);


                }
            });

            holder.mobData.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    CheckBox mb = (CheckBox) v;
                    ListElement a = (ListElement) mb.getTag();
                    a.setMobDataSelected(mb.isChecked());
                    if (db.getWatchedApp(a.getUID()) != null)
                        db.updateWatchedApp(a);
                    else
                        db.addAppToWatch(a);
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListElement application = appList.get(position);
        holder.appName.setText(application.getAppName());
        holder.wifi.setChecked(application.isWifiSelected());
        holder.mobData.setChecked(application.isMobDataSelected());
        holder.wifi.setTag(application);
        holder.mobData.setTag(application);

        return convertView;

    }

}