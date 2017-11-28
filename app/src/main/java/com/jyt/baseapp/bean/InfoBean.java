package com.jyt.baseapp.bean;

import java.io.Serializable;

/**
 * @author LinWei on 2017/11/28 16:48
 */
public class InfoBean implements Serializable {
    private String nickName;
    private String tel;
    private String departmentName;
    private String stationName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDeparementName() {
        return departmentName;
    }

    public void setDeparementName(String deparementName) {
        this.departmentName = deparementName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
