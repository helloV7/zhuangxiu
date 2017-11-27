package com.jyt.baseapp.bean;

/**
 * @author LinWei on 2017/11/24 13:44
 */
public class WorkBean {
    private String type;
    private boolean isCheck;

    public WorkBean(String type){
        this.type = type;
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
}
