package com.jyt.baseapp.bean;

/**
 * @author LinWei on 2017/11/14 10:52
 */
public class BrandBean {
    private String brandId;
    private String brandName;
    private String subClassId;//子品牌ID
    private String subClassName;//子品牌名称
    private boolean isCheck;

    public BrandBean(){

    }

    public BrandBean(String brandName , String brandId){
        this.brandId=brandId;
        this.brandName=brandName;
    }

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

    public BrandBean setChecks(boolean check){
        isCheck = check;
        return this;
    }

    public void TransData(){
        brandId=subClassId;
        brandName=subClassName;
    }

    public BrandBean TransDataThis(){
        subClassId=brandId;
        subClassName=brandName;
        return this;
    }


}
