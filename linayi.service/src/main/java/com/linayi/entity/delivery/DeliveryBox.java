package com.linayi.entity.delivery;

import java.util.Date;

public class DeliveryBox {
    private Long deliveryBoxId;

    private Long boxNo;

    private String image; 
    
    private Date updateTime;

    private Date createTime;

    public Long getDeliveryBoxId() {
        return deliveryBoxId;
    }

    public void setDeliveryBoxId(Long deliveryBoxId) {
        this.deliveryBoxId = deliveryBoxId;
    }

    public Long getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(Long boxNo) {
        this.boxNo = boxNo;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}