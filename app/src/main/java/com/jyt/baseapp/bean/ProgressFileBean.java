package com.jyt.baseapp.bean;

import java.util.Date;

/**
 * @author LinWei on 2017/12/12 11:32
 */
public class ProgressFileBean {
    private String constructionName;
    private String constructionSuffix;
    private String constructionUrl;
    private String constructionType;

    public ProgressFileBean(String constructionUrl, String constructionType) {
        constructionName = new Date().getTime()+constructionUrl.substring(constructionUrl.lastIndexOf("/")+1,constructionUrl.lastIndexOf("."));
        constructionSuffix = constructionUrl.substring(constructionUrl.lastIndexOf(".")+1);
        this.constructionUrl = constructionUrl;
        this.constructionType = constructionType;
    }

    public String getConstructionName() {
        return constructionName;
    }

    public void setConstructionName(String constructionName) {
        this.constructionName = constructionName;
    }

    public String getConstructionSuffix() {
        return constructionSuffix;
    }

    public void setConstructionSuffix(String constructionSuffix) {
        this.constructionSuffix = constructionSuffix;
    }

    public String getConstructionUrl() {
        return constructionUrl;
    }

    public void setConstructionUrl(String constructionUrl) {
        this.constructionUrl = constructionUrl;
    }

    public String getConstructionType() {
        return constructionType;
    }

    public void setConstructionType(String constructionType) {
        this.constructionType = constructionType;
    }
}
