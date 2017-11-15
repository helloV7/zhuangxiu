package com.jyt.baseapp.bean;

/**
 * @author LinWei on 2017/11/14 10:52
 */
public class BrandBean {
    private String brandId;
    private String brandName;
    private String subClassId;
    private String subClassName;
    private boolean isCheck;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getSubClassId() {
        return subClassId;
    }

    public void setSubClassId(String subClassId) {
        this.subClassId = subClassId;
    }

    public String getSubClassName() {
        return subClassName;
    }

    public void setSubClassName(String subClassName) {
        this.subClassName = subClassName;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public void TransData(){
        brandId=subClassId;
        brandName=subClassName;
    }
}
