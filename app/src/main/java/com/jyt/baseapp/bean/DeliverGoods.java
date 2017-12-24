package com.jyt.baseapp.bean;

/**
 * Created by chenweiqi on 2017/12/8.
 */

public class DeliverGoods {
    private String logisticsName;//货物名称
    private String logisticsCompany;//物流公司
    private String logisticsTel;//对方物流公司电话
    private String logisticsNo;//物流单号
    private String logisticsTime;//发货时间
    private String arriveTime;//预计到货时间 yyyy-mm-dd hh:mm:ss

    private String constructionId;
    private String constructionTime;
    private String constructionArr;
    private String constructionStart;
    private String projectManId;
    private String mpersonalId;
    private String projectId;
    private String nickName;
    private String monitorName;


    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getLogisticsTel() {
        return logisticsTel;
    }

    public void setLogisticsTel(String logisticsTel) {
        this.logisticsTel = logisticsTel;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getLogisticsTime() {
        return logisticsTime;
    }

    public void setLogisticsTime(String logisticsTime) {
        this.logisticsTime = logisticsTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

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
