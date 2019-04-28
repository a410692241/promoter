package com.linayi.dto;

public class WxSignatureDto {
    private String timestamp;

    private String Noncestr;

    private String appid;

    private String signature;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return Noncestr;
    }

    public void setNoncestr(String noncestr) {
        Noncestr = noncestr;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
