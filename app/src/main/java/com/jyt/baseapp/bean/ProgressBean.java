package com.jyt.baseapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author LinWei on 2017/11/16 18:37
 */
public class ProgressBean implements Parcelable{
    private String speedId;
    private String projectId;
    private String speedName;
    private int speedCode;
    private String isfinish;
    private int speedUpCode;
    private String finishTime;
    private int PermissionState; //0无权限

    protected ProgressBean(Parcel in) {
        speedId = in.readString();
        projectId = in.readString();
        speedName = in.readString();
        speedCode = in.readInt();
        isfinish = in.readString();
        speedUpCode = in.readInt();
        finishTime = in.readString();
        PermissionState = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(speedId);
        dest.writeString(projectId);
        dest.writeString(speedName);
        dest.writeInt(speedCode);
        dest.writeString(isfinish);
        dest.writeInt(speedUpCode);
        dest.writeString(finishTime);
        dest.writeInt(PermissionState);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProgressBean> CREATOR = new Creator<ProgressBean>() {
        @Override
        public ProgressBean createFromParcel(Parcel in) {
            return new ProgressBean(in);
        }

        @Override
        public ProgressBean[] newArray(int size) {
            return new ProgressBean[size];
        }
    };

    public String getSpeedId() {
        return speedId;
    }

    public void setSpeedId(String speedId) {
        this.speedId = speedId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSpeedName() {
        return speedName;
    }

    public void setSpeedName(String speedName) {
        this.speedName = speedName;
    }

    public int getSpeedCode() {
        return speedCode;
    }

    public void setSpeedCode(int speedCode) {
        this.speedCode = speedCode;
    }

    public String getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(String isfinish) {
        this.isfinish = isfinish;
    }

    public int getSpeedUpCode() {
        return speedUpCode;
    }

    public void setSpeedUpCode(int speedUpCode) {
        this.speedUpCode = speedUpCode;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public int getPermissionState() {
        return PermissionState;
    }

    public void setPermissionState(int permissionState) {
        PermissionState = permissionState;
    }
}
