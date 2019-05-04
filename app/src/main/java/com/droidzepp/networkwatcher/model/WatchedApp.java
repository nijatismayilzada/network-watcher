package com.droidzepp.networkwatcher.model;

import java.util.Objects;

/**
 * Created by nijat on 30/09/15.
 */
public class WatchedApp {

    private int uid;
    private String appName;
    private boolean wifiSelected;
    private boolean mobDataSelected;

    public WatchedApp() {

    }

    public WatchedApp(int uid, String appName, boolean wifiSelected, boolean mobDataSelected) {
        this.uid = uid;
        this.appName = appName;
        this.wifiSelected = wifiSelected;
        this.mobDataSelected = mobDataSelected;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public boolean isWifiSelected() {
        return wifiSelected;
    }

    public void setWifiSelected(boolean wifiSelected) {
        this.wifiSelected = wifiSelected;
    }

    public boolean isMobDataSelected() {
        return mobDataSelected;
    }

    public void setMobDataSelected(boolean mobDataSelected) {
        this.mobDataSelected = mobDataSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WatchedApp that = (WatchedApp) o;
        return uid == that.uid &&
                wifiSelected == that.wifiSelected &&
                mobDataSelected == that.mobDataSelected &&
                Objects.equals(appName, that.appName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, appName, wifiSelected, mobDataSelected);
    }

    @Override
    public String toString() {
        return "WatchedApp{" +
                "uid=" + uid +
                ", appName='" + appName + '\'' +
                ", wifiSelected=" + wifiSelected +
                ", mobDataSelected=" + mobDataSelected +
                '}';
    }
}
