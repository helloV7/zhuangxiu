package com.jyt.baseapp.bean;

import java.util.List;

/**
 * @author LinWei on 2017/12/25 12:51
 */
public class FinishBean {
    private String constructionDate;
    private String constructionNickName;
    private String stationName;
    private List<Data> lolist;

    public static class Data{
        private String constructionId;
        private String constructionName;
        private String constructionSuffix;
        private String constructionUrl;
        private String constructionType;
        private String constructionNickName;
        private String isdel;


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

        public String getConstructionNickName() {
            return constructionNickName;
        }

        public void setConstructionNickName(String constructionNickName) {
            this.constructionNickName = constructionNickName;
        }
    }

    public String getConstructionDate() {
        return constructionDate;
    }

    public void setConstructionDate(String constructionDate) {
        this.constructionDate = constructionDate;
    }

    public String getConstructionNickName() {
        return constructionNickName;
    }

    public void setConstructionNickName(String constructionNickName) {
        this.constructionNickName = constructionNickName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public List<Data> getLolist() {
        return lolist;
    }

    public void setLolist(List<Data> lolist) {
        this.lolist = lolist;
    }
}
