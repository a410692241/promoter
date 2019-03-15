package com.linayi.entity.promoter;

import org.springframework.format.annotation.DateTimeFormat;

import com.linayi.entity.BaseEntity;

import java.util.Date;

public class OrderManMember extends BaseEntity{
    private Integer orderManMemberId;

    private Integer orderManId;

    private Integer memberId;

    private Integer userId;

    private Date updateTime;

    private Date createTime;

    private String headImage;	//头像

    private String nickname;	//昵称

    private Integer openOrderManInfoId;

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm" )
    private Date openMemberTime;	//会员开通时间

    private Integer numberOfOrders;	//订单数

    private Integer totalSum;	//订单合计金额

    private Integer receiveAddressId;	//收货地址id

    private String mobile;	//收货联系电话
    
    private String date;
    private String type;

	/*创建开始时间*/
	private String createTimeStart;
	/*创建结束时间*/
	private String createTimeEnd;

    public Integer getOrderManMemberId() {
        return orderManMemberId;
    }

    public void setOrderManMemberId(Integer orderManMemberId) {
        this.orderManMemberId = orderManMemberId;
    }

    public Integer getOrderManId() {
        return orderManId;
    }

    public void setOrderManId(Integer orderManId) {
        this.orderManId = orderManId;
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

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getOpenMemberTime() {
		return openMemberTime;
	}

	public void setOpenMemberTime(Date openMemberTime) {
		this.openMemberTime = openMemberTime;
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

	public Integer getReceiveAddressId() {
		return receiveAddressId;
	}

	public void setReceiveAddressId(Integer receiveAddressId) {
		this.receiveAddressId = receiveAddressId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getOpenOrderManInfoId() {
		return openOrderManInfoId;
	}

	public void setOpenOrderManInfoId(Integer openOrderManInfoId) {
		this.openOrderManInfoId = openOrderManInfoId;
	}


}