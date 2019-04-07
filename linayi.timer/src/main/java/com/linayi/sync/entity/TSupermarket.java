package com.linayi.sync.entity;

import com.linayi.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class TSupermarket extends BaseEntity {
    private Integer supermarketId;

    private String name;

    private String areaCode;
    
    private String areaName;

    private String address;

    private String status;

    private String logo;

    private Integer orderNo;
    
    private Date updateTime;

    private Date createTime;

    //采买员Id
    private Integer procurerId;

    private String type;

    private String goodsPrice;//商品价格

    private Integer userId;

    private String correctType;//按钮类型

    private Long correctId;//纠错id


    private List<Integer> supermarketIdList;
    /**
	 * 查询创建时间起
	 */
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date createTimeStart;

	/**
	 * 查询时间止
	 */
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date createTimeEnd;

	
    public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Integer getSupermarketId() {
        return supermarketId;
    }

    public void setSupermarketId(Integer supermarketId) {
        this.supermarketId = supermarketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
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

    public Integer getProcurerId() {
        return procurerId;
    }

    public void setProcurerId(Integer procurerId) {
        this.procurerId = procurerId;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

	public List<Integer> getSupermarketIdList() {
		return supermarketIdList;
	}

	public void setSupermarketIdList(List<Integer> supermarketIdList) {
		this.supermarketIdList = supermarketIdList;
	}

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    
    
    public String getCorrectType() {
		return correctType;
	}

	public void setCorrectType(String correctType) {
		this.correctType = correctType;
	}

	
	public Long getCorrectId() {
		return correctId;
	}

	public void setCorrectId(Long correctId) {
		this.correctId = correctId;
	}

	@Override
	public String toString() {
		return "Supermarket [supermarketId=" + supermarketId + ", name=" + name + ", areaCode=" + areaCode
				+ ", areaName=" + areaName + ", address=" + address + ", status=" + status + ", logo=" + logo
				+ ", orderNo=" + orderNo + ", updateTime=" + updateTime + ", createTime=" + createTime
                +", procurerId=" + procurerId + ", type=" + type + ", goodsPrice=" + goodsPrice
                + ", supermarketIdList=" + supermarketIdList + ", createTimeStart=" + createTimeStart
                + ", createTimeEnd=" + createTimeEnd + "]";
	}
    
}