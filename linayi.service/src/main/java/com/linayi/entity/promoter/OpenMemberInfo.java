package com.linayi.entity.promoter;

import java.util.Date;

public class OpenMemberInfo {
    private Integer openMemberInfoId;

    private Integer userId;

    private String memberLevel;

    private Date startTime;

    private Date endTime;

    private Integer orderManId;
    
    private Integer freeTimes;

    private Date createTime;
    
    private Integer openOrderManInfoId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel == null ? null : memberLevel.trim();
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

    public Integer getOrderManId() {
        return orderManId;
    }

    public void setOrderManId(Integer orderManId) {
        this.orderManId = orderManId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Integer getFreeTimes() {
		return freeTimes;
	}

	public void setFreeTimes(Integer freeTimes) {
		this.freeTimes = freeTimes;
	}

	public Integer getOpenMemberInfoId() {
		return openMemberInfoId;
	}

	public void setOpenMemberInfoId(Integer openMemberInfoId) {
		this.openMemberInfoId = openMemberInfoId;
	}

	public Integer getOpenOrderManInfoId() {
		return openOrderManInfoId;
	}

	public void setOpenOrderManInfoId(Integer openOrderManInfoId) {
		this.openOrderManInfoId = openOrderManInfoId;
	}
}