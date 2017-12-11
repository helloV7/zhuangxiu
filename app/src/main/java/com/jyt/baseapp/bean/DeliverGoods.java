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
}
