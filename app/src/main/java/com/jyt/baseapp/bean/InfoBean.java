package com.jyt.baseapp.bean;

import java.io.Serializable;

/**
 * @author LinWei on 2017/11/28 16:48
 */
public class InfoBean implements Serializable {

    private int state;
    private String projectId;
    private String projectName;//项目名
    //进度
    private String updateDate;//更新状态时间
    private String speedName;//节点名称
    //提示
    private String tabletimeName;//步骤名称
    private String tabletimeCode;//code
    private String bliu;//项目名
    private String bshiba;//当前系统时间
    private String finishDate;//完成时间
    private String asishiba;//更新状态时间
    //评价
    private String ktoptime;//店主对项目经理评价
    private String ktodtime;//店主对设计师评价
    private String ktomtime;//店主对测量人员评价
    private String ktobtime;//店主对预算员评价


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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


    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSpeedName() {
        return speedName;
    }

    public void setSpeedName(String speedName) {
        this.speedName = speedName;
    }
}
