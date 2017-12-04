package com.jyt.baseapp.bean;

import java.util.List;

/**
 * @author LinWei on 2017/12/4 15:48
 */
public class PicBean {
    private String shareDate;
    private List<Data> shareImage;
    public static class Data{

        public String shareId;
        public String shareName;
        public String shareSuffix;
        public String shareUrl;
    }

    public String getShareDate() {
        return shareDate;
    }

    public void setShareDate(String shareDate) {
        this.shareDate = shareDate;
    }

    public List<Data> getShareImage() {
        return shareImage;
    }

    public void setShareImage(List<Data> shareImage) {
        this.shareImage = shareImage;
    }
}
