package com.jyt.baseapp.bean;

/**
 * Created by chenweiqi on 2017/12/8.
 */

public class Construction {
    private String constructionId;//货到待施工id
    private String constructionTime;//预计到货日期
    private String constructionArr;//实际到货日期
    private String constructionStart;//预计到店时间
    private String projectManId;//施工人员（或者叫项目经理）id
    private String mpersonalId;//班长id
    private String projectId;//项目id
    private String nickName;//施工人员名称
    private String monitorName;//班长名称

    public String getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(String constructionId) {
        this.constructionId = constructionId;
    }

    public String getConstructionTime() {
        return constructionTime;
    }

    public void setConstructionTime(String constructionTime) {
        this.constructionTime = constructionTime;
    }

    public String getConstructionArr() {
        return constructionArr;
    }

    public void setConstructionArr(String constructionArr) {
        this.constructionArr = constructionArr;
    }

    public String getConstructionStart() {
        return constructionStart;
    }

    public void setConstructionStart(String constructionStart) {
        this.constructionStart = constructionStart;
    }

    public String getProjectManId() {
        return projectManId;
    }

    public void setProjectManId(String projectManId) {
        this.projectManId = projectManId;
    }

    public String getMpersonalId() {
        return mpersonalId;
    }

    public void setMpersonalId(String mpersonalId) {
        this.mpersonalId = mpersonalId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }
}
