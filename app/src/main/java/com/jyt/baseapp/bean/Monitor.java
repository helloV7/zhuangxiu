package com.jyt.baseapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 班长
 * Created by chenweiqi on 2017/12/8.
 */

public class Monitor implements Parcelable{
    private String monitorId;//班长id
    private String monitorName;//班长名称
    private String monitorTel;//班长联系方式
    private String monitorUpId;//上司Id

    protected Monitor(Parcel in) {
        monitorId = in.readString();
        monitorName = in.readString();
        monitorTel = in.readString();
        monitorUpId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(monitorId);
        dest.writeString(monitorName);
        dest.writeString(monitorTel);
        dest.writeString(monitorUpId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Monitor> CREATOR = new Creator<Monitor>() {
        @Override
        public Monitor createFromParcel(Parcel in) {
            return new Monitor(in);
        }

        @Override
        public Monitor[] newArray(int size) {
            return new Monitor[size];
        }
    };

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getMonitorTel() {
        return monitorTel;
    }

    public void setMonitorTel(String monitorTel) {
        this.monitorTel = monitorTel;
    }

    public String getMonitorUpId() {
        return monitorUpId;
    }

    public void setMonitorUpId(String monitorUpId) {
        this.monitorUpId = monitorUpId;
    }
}
