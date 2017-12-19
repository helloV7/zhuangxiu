package com.jyt.baseapp.bean;

import java.io.Serializable;

/**
 * @author LinWei on 2017/11/28 16:48
 */
public class InfoBean implements Serializable {
    private int state;
    private String getworktip;
    private String tabletimeName;
    private String projectId;
    private String tabletimeCode;
    private String bliu;
    private String bshiba;//当前系统时间
    private String finishDate;//完成时间
    private String asishiba;//更新状态时间

    private String ktoptime;
    private String ktodtime;
    private String ktomtime;
    private String ktobtime;


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getGetworktip() {
        return getworktip;
    }

    public void setGetworktip(String getworktip) {
        this.getworktip = getworktip;
    }

    public String getTabletimeName() {
        return tabletimeName;
    }

    public void setTabletimeName(String tabletimeName) {
        this.tabletimeName = tabletimeName;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTabletimeCode() {
        return tabletimeCode;
    }

    public void setTabletimeCode(String tabletimeCode) {
        this.tabletimeCode = tabletimeCode;
    }

    public String getBliu() {
        return bliu;
    }

    public void setBliu(String bliu) {
        this.bliu = bliu;
    }

    public String getBshiba() {
        return bshiba;
    }

    public void setBshiba(String bshiba) {
        this.bshiba = bshiba;
    }

    public String getAsishiba() {
        return asishiba;
    }

    public void setAsishiba(String asishiba) {
        this.asishiba = asishiba;
    }

    public String getKtoptime() {
        return ktoptime;
    }

    public void setKtoptime(String ktoptime) {
        this.ktoptime = ktoptime;
    }

    public String getKtodtime() {
        return ktodtime;
    }

    public void setKtodtime(String ktodtime) {
        this.ktodtime = ktodtime;
    }

    public String getKtomtime() {
        return ktomtime;
    }

    public void setKtomtime(String ktomtime) {
        this.ktomtime = ktomtime;
    }

    public String getKtobtime() {
        return ktobtime;
    }

    public void setKtobtime(String ktobtime) {
        this.ktobtime = ktobtime;
    }
}
