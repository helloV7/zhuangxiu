package com.jyt.baseapp.bean;

/**
 * Created by chenweiqi on 2017/12/4.
 */

public class ProjectFileBean{
    private String contentId;//主键
    private String contentUrl;//文件或者图片路径
    private String detailId;//详细的id
    private String userId;//上传的用户id
    private String contentName;//文件名
    private String contentSuffix;//文件后缀
    private String isdel;//是否已经删除

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentSuffix() {
        return contentSuffix;
    }

    public void setContentSuffix(String contentSuffix) {
        this.contentSuffix = contentSuffix;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public FileBean toFileBean(){
        FileBean fileBean = new FileBean();
        fileBean.setIsdel(getIsdel());
        fileBean.setShareDate(null);
        fileBean.setShareId(getContentId());
        fileBean.setShareName(getContentName());
        fileBean.setShareSuffix(getContentSuffix());
        fileBean.setShareUrl(getContentUrl());
        return fileBean;
    }
}
