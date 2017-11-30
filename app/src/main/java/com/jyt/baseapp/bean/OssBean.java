package com.jyt.baseapp.bean;

/**
 * @author LinWei on 2017/11/30 10:19
 */
public class OssBean {
    private String securityToken;
    private String accessKeySecret;
    private String accessKeyId;

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    @Override
    public String toString() {
        return "OssBean{" +
                "securityToken='" + securityToken + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                '}';
    }
}
