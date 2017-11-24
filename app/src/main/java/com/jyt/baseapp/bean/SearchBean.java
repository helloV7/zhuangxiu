package com.jyt.baseapp.bean;

import java.io.Serializable;

/**
 * @author LinWei on 2017/11/14 18:24
 */
public class SearchBean implements Serializable {
    private String projectId;
    private String brandName;
    private String subClassName;
    private String projectName;
    private String longitude;
    private String latitude;
    private String city;
    private String area;
    private String address;
    private String schedule;
    private String time;

    public String getProjectId() {
        return projectId;
    }



    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSubClassName() {
        return subClassName;
    }

    public void setSubClassName(String subClassName) {
        this.subClassName = subClassName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }


}
