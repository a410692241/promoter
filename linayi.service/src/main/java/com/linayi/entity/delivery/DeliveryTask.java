package com.linayi.entity.delivery;

import java.util.Date;
import java.util.List;
import com.linayi.entity.BaseEntity;
import com.linayi.entity.goods.GoodsSku;


public class DeliveryTask extends BaseEntity{

    private Long deliveryTaskId;

    private Long ordersId;

    private Long deliveryBoxId;

    private Integer userId;

    private String status;

    private Integer communityId;

    private Date updateTime;

    private Date createTime;

    private String communityName; //社区名
    
    private String receiverName; //收货人
    /*创建开始时间*/
    private String createTimeStart;
    /*创建结束时间*/
    private String createTimeEnd;
    
    private Integer amount; //订单金额
    
    
    public Integer getAmount() {
		return amount;
	}

    
    /**顾客名称*/
    private String customerName;
    
    /**数量*/
    private Integer quantity;

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
    /**箱号*/
    private Long boxNo;
    
    /**顾客收货地址*/
    private String address;
    
    /**顾客收货电话*/
    private String mobile; 
    
    /**配送商品List*/
    private List<GoodsSku> listGoodsSku;
    
    public Long getDeliveryTaskId() {
        return deliveryTaskId;
    }

    public void setDeliveryTaskId(Long deliveryTaskId) {
        this.deliveryTaskId = deliveryTaskId;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public Long getDeliveryBoxId() {
        return deliveryBoxId;
    }

    public void setDeliveryBoxId(Long deliveryBoxId) {
        this.deliveryBoxId = deliveryBoxId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Integer getCommunityId() { return communityId; }

    public void setCommunityId(Integer communityId) { this.communityId = communityId; }

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(Long boxNo) {
		this.boxNo = boxNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<GoodsSku> getListGoodsSku() {
		return listGoodsSku;
	}

	public void setListGoodsSku(List<GoodsSku> listGoodsSku) {
		this.listGoodsSku = listGoodsSku;
	}
	
	/**配送人电话*/
	public String getDeliveryMobile() {
		return deliveryMobile;
	}

	public void setDeliveryMobile(String deliveryMobile) {
		this.deliveryMobile = deliveryMobile;
	}


	private String deliveryMobile;

	@Override
	public String toString() {
		return "{" +
				"deliveryTaskId=" + deliveryTaskId +
				", ordersId=" + ordersId +
				", deliveryBoxId=" + deliveryBoxId +
				", userId=" + userId +
				", status='" + status + '\'' +
				", communityId=" + communityId +
				", updateTime=" + updateTime +
				", createTime=" + createTime +
				", communityName='" + communityName + '\'' +
				", receiverName='" + receiverName + '\'' +
				", createTimeStart='" + createTimeStart + '\'' +
				", createTimeEnd='" + createTimeEnd + '\'' +
				", amount=" + amount +
				", customerName='" + customerName + '\'' +
				", quantity=" + quantity +
				", boxNo=" + boxNo +
				", address='" + address + '\'' +
				", mobile='" + mobile + '\'' +
				", listGoodsSku=" + listGoodsSku +
				", deliveryMobile='" + deliveryMobile + '\'' +
				'}';
	}
}