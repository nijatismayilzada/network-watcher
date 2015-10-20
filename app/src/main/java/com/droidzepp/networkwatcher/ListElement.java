package com.droidzepp.networkwatcher;

/**
 * Created by nijat on 30/09/15.
 */
public class ListElement {

    String appName = null;
    int uid = 0;
    boolean wifi = false;
    boolean mobData = false;

    public ListElement(){

    }
    public ListElement(int uid, String appName, boolean wifi, boolean mobData) {
        super();
        this.uid= uid;
        this.appName = appName;
        this.wifi = wifi;
        this.mobData = mobData;
    }

    public int getUID(){
        return uid;
    }

    public void setUID(int uid){
        this.uid = uid;
    }

    public String getAppName() {
        return appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean isWifiSelected() {
        return wifi;
    }
    public void setWifiSelected(boolean wifi) {
        this.wifi = wifi;
    }
    public boolean isMobDataSelected() {
        return mobData;
    }
    public void setMobDataSelected(boolean mobData) {
        this.mobData = mobData;
    }
}
