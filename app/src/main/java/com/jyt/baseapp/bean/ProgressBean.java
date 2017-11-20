package com.jyt.baseapp.bean;

/**
 * @author LinWei on 2017/11/16 18:37
 */
public class ProgressBean {
    private String speedId;
    private String projectId;
    private String speedName;
    private int speedCode;
    private String isfinish;
    private int speedUpCode;
    private String finishTime;
    private int PermissionState; //0无权限

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
