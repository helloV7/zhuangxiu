package com.jyt.baseapp.bean;

import java.util.List;

/**
 * @author LinWei on 2017/12/11 14:52
 */
public class ConstructionBean {
    private String finishTime;
    private List<ConstructionDetailBean> dataList;

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public List<ConstructionDetailBean> getConstructionList() {
        return dataList;
    }

    public void setConstructionList(List<ConstructionDetailBean> constructionId) {
        this.dataList = constructionId;
    }
}
