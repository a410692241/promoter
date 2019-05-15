package com.linayi.entity.promoter;

import java.util.Date;

public class OpenOrderManInfo {
    private Integer openOrderManInfoId;

    private Integer promoterId;

    private Integer orderManId;

    private String identity;

    private String orderManLevel;

    private Date startTime;

    private Date endTime;

    private Date updateTime;

    private Date createTime;

    private String parentType;

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public Integer getOpenOrderManInfoId() {
        return openOrderManInfoId;
    }

    public void setOpenOrderManInfoId(Integer openOrderManInfoId) {
        this.openOrderManInfoId = openOrderManInfoId;
    }

    public Integer getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(Integer promoterId) {
        this.promoterId = promoterId;
    }

    public Integer getOrderManId() {
        return orderManId;
    }

    public void setOrderManId(Integer orderManId) {
        this.orderManId = orderManId;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public String getOrderManLevel() {
        return orderManLevel;
    }

    public void setOrderManLevel(String orderManLevel) {
        this.orderManLevel = orderManLevel == null ? null : orderManLevel.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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