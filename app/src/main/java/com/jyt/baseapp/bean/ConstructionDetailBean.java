package com.jyt.baseapp.bean;

/**
 * @author LinWei on 2017/12/11 14:52
 */
public class ConstructionDetailBean {
    private String constructionId;//图片id
    private String constructionName;//文件名
    private String constructionSuffix;//后缀
    private String constructionUrl;//文件路径
    private String constructionType;//类型 1每天上传2最后上传的
    private String isdel;//是否删除的标志
    private String projectId;//项目id
    private String constructionDate;//上传时间
    private String constructionUser;//上传人id
    private String constructionNickName;//上传人昵称

    public String getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(String constructionId) {
        this.constructionId = constructionId;
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

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getConstructionDate() {
        return constructionDate;
    }

    public void setConstructionDate(String constructionDate) {
        this.constructionDate = constructionDate;
    }

    public String getConstructionUser() {
        return constructionUser;
    }

    public void setConstructionUser(String constructionUser) {
        this.constructionUser = constructionUser;
    }

    public String getConstructionNickName() {
        return constructionNickName;
    }

    public void setConstructionNickName(String constructionNickName) {
        this.constructionNickName = constructionNickName;
    }
}
