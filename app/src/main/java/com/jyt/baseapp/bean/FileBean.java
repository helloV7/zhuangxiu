package com.jyt.baseapp.bean;

import java.io.Serializable;

/**共享文件
 * @author LinWei on 2017/11/28 11:24
 */
public class FileBean implements Serializable {
    private String shareId;
    private String shareName;
    private String shareSuffix;
    private String shareUrl;
    private String shareDate;
    private String isdel;

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public String getShareSuffix() {
        return shareSuffix;
    }

    public void setShareSuffix(String shareSuffix) {
        this.shareSuffix = shareSuffix;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareDate() {
        return shareDate;
    }

    public void setShareDate(String shareDate) {
        this.shareDate = shareDate;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }
}
