package com.linayi.entity.user;

import java.util.Date;

import com.linayi.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

public class ReceiveAddress extends BaseEntity{
    private Integer receiveAddressId;

    private Integer userId;

    private String receiverName;

    private String sex;

    private String mobile;

    private Integer addressOne;

    private String addressTwo;

    private String status;

    private Date updateTime;

    private Date createTime;
    
    private Integer numberOfOrders;	//订单数

    private Integer totalSum;	//订单合计金额
    /***
     * 小区名字
     */
    private String smallName;
    
    /**地址类型*/
    private String addressType;
    
    private Integer memberId;

    /*创建开始时间*/
    private String createTimeStart;
    /*创建结束时间*/
    private String createTimeEnd;

    public String getSmallName() {
        return smallName;
    }

    public void setSmallName(String smallName) {
        this.smallName = smallName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /***
     * 地区名字
     */
    private String areaName;

    public Integer getReceiveAddressId() {
        return receiveAddressId;
    }

    public void setReceiveAddressId(Integer receiveAddressId) {
        this.receiveAddressId = receiveAddressId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(Integer addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo == null ? null : addressTwo.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getNumberOfOrders() {
		return numberOfOrders;
	}

	public void setNumberOfOrders(Integer numberOfOrders) {
		this.numberOfOrders = numberOfOrders;
	}

	public Integer getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(Integer totalSum) {
		this.totalSum = totalSum;
	}

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }
}