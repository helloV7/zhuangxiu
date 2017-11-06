package com.jyt.baseapp.bean;

/**
 * @author LinWei on 2017/11/3 15:42
 */
public class ProjectBean {
    private String shopName;//商店信息
    private String shopMsg;//商店信息
    private String station;//商店状态
    private String location;//商店位置
    private boolean isStop;
    private boolean isNext;

    public ProjectBean(String shopName, String shopMsg, String station, String location) {
        this.shopName = shopName;
        this.shopMsg = shopMsg;
        this.station = station;
        this.location = location;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopMsg() {
        return shopMsg;
    }

    public void setShopMsg(String shopMsg) {
        this.shopMsg = shopMsg;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public boolean isNext(){
        return isNext;
    }

    public void setNext(boolean next){
        isNext=next;
    }
}
