package com.jyt.baseapp.bean;

import com.google.gson.annotations.SerializedName;

/**工种
 * @author LinWei on 2017/11/24 13:44
 */
public class WorkBean {
    @SerializedName("name")
    private String type;
    private boolean isCheck;
    private String id;
    private String subClassId;
    private String subClassName;

    public WorkBean(String type){
        this.type = type;
    }

    public WorkBean(String type,String id){
        this.type = type;
        this.id = id;
    }





    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WorkBean firstCheck(){
        isCheck=true;
        return this;
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
}
