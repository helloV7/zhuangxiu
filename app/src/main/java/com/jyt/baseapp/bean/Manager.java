package com.jyt.baseapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 经理
 * Created by chenweiqi on 2017/12/8.
 */

public class Manager implements Parcelable{
    private String userId;
    private String nickName;

    protected Manager(Parcel in) {
        userId = in.readString();
        nickName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(nickName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Manager> CREATOR = new Creator<Manager>() {
        @Override
        public Manager createFromParcel(Parcel in) {
            return new Manager(in);
        }

        @Override
        public Manager[] newArray(int size) {
            return new Manager[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
