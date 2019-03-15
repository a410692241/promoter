package com.linayi.entity.promoter;

import java.util.Date;

public class Promoter {
    private Integer promoterId;

    private String name;

    private String mobile;

    private String qq;

    private String email;

    private String promoterLevel;

    private Date updateTime;

    private Date createTime;

    public Integer getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(Integer promoterId) {
        this.promoterId = promoterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPromoterLevel() {
        return promoterLevel;
    }

    public void setPromoterLevel(String promoterLevel) {
        this.promoterLevel = promoterLevel == null ? null : promoterLevel.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}