package com.linayi.entity.order;

import java.util.Date;

import com.linayi.entity.BaseEntity;

public class SelfOrderMessage extends BaseEntity{
    private Long messageId;

    private Long selfOrderId;

    private String status;

    private Integer userId;
    
    private String viewStatus;

    private Date updateTime;

    private Date createTime;
    
    /*self_order表的字段*/
    private String goodsName;
    
    private String brandName;
    
    private String attrValue;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getSelfOrderId() {
        return selfOrderId;
    }

    public void setSelfOrderId(Long selfOrderId) {
        this.selfOrderId = selfOrderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
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
 
    public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	
	
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	



	public SelfOrderMessage(Long messageId, Long selfOrderId, String status, Integer userId, String viewStatus,
			Date updateTime, Date createTime, String goodsName, String brandName, String attrValue) {
		super();
		this.messageId = messageId;
		this.selfOrderId = selfOrderId;
		this.status = status;
		this.userId = userId;
		this.viewStatus = viewStatus;
		this.updateTime = updateTime;
		this.createTime = createTime;
		this.goodsName = goodsName;
		this.brandName = brandName;
		this.attrValue = attrValue;
	}

	public SelfOrderMessage() {
		super();
	}

	public SelfOrderMessage(Long selfOrderId, String status, Integer userId, Date updateTime, Date createTime) {
        setSelfOrderId(selfOrderId);
        setStatus(status);
        setUserId(userId);
        setUpdateTime(updateTime);
        setCreateTime(createTime);
    }
}