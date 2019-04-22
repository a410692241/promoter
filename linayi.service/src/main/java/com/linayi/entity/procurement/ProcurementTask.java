package com.linayi.entity.procurement;

import com.linayi.entity.BaseEntity;
import com.linayi.util.DateUtil;
import io.swagger.models.auth.In;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


public class ProcurementTask extends BaseEntity{




    @Override
    public String toString() {
        return "ProcurementTask [procurementTaskId=" + procurementTaskId + ", ordersId=" + ordersId + ", ordersGoodsId="
                + ordersGoodsId + ", supermarketId=" + supermarketId + ", goodsSkuId=" + goodsSkuId + ", price=" + price
                + ", quantity=" + quantity + ", procureStatus=" + procureStatus + ", receiveStatus=" + receiveStatus
                + ", supermarketList=" + supermarketList + ", maxSupermarketId=" + maxSupermarketId + ", maxPrice="
                + maxPrice + ", actualQuantity=" + actualQuantity + ", status=" + status + ", communityId="
                + communityId + ", userId=" + userId + ", updateTime=" + updateTime + ", createTime=" + createTime
                + ", goodsSkuName=" + goodsSkuName + ", communityName=" + communityName + ", buyUserName=" + buyUserName
                + ", supermarketName=" + supermarketName + ", image=" + image + ", serviceFeeString=" + serviceFeeString
                + "]";
    }

    private String procurementTaskIdList;

    private Long procurementTaskId;

    private Long ordersId;

    private Long ordersGoodsId;

    private Integer supermarketId;

    private Integer goodsSkuId;

    private Date accessTime;//调用接口的日期

    //商品全称
    private String fullName;

    private Integer price;

    private Integer quantity;

    private String procureStatus;

    private String receiveStatus;

    private String supermarketList;

    private Integer maxSupermarketId;

    private Integer maxPrice;

    private Integer actualQuantity;

    //总数量
    private Integer totalQuantity;

    private String status;

    private Integer communityId;

    private Integer userId;

    private Date updateTime;

    private Date createTime;

    private Date procureTime;

    //采买发货时间
    private Date procureOutTime;

    private String  procureTimeStr;

    private Date receiveTime;

    private String goodsSkuName;//商品名

    private String goodsImage;//商品图片

    private String communityName;//社区名字

    private String totalPrice;

    private String boxNo;

    private Integer procureQuantity;

    private Integer receiveQuantity;
    //总采买数量
    private Integer totalProcureQuantity;
    //总采买金额
    private Integer totalProcurePrice;

    private Integer boxQuantity;

    private String boxStatus;

    private Long deliveryBoxId;

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm" )
    private Date orderCreateTime;//订单下单时间

    private String receiverName;//收货人姓名

    private String receiverAddress;//收货地址

    private String buyUserName; //采买员姓名

    private String supermarketName; //超市名

    private String image; //商品图片

    private Integer serviceFeeString; //服务费

    private Integer counts; //天数

    private Date flowReceiveTime;//[流转中心收货时间]

    private Date flowOutTime;//[流转中心发货时间]

    private String barcode;//商品条形码

    private String deliveryWaveTime;//配送波次

    private String startTime;//开始时间

    private String endTime;//结束时间

    private Date wavePickingStartTime;//配送波次开始时间

    private Date wavePickingEndTime;//配送波次结束时间

    public Date getFlowOutTime() {
        return flowOutTime;
    }

    public void setFlowOutTime(Date flowOutTime) {
        this.flowOutTime = flowOutTime;
    }

    public Date getFlowReceiveTime() {
        return flowReceiveTime;
    }

    public void setFlowReceiveTime(Date flowReceiveTime) {
        this.flowReceiveTime = flowReceiveTime;
    }

    public Integer getServiceFeeString() {
        return serviceFeeString;
    }

    public void setServiceFeeString(Integer serviceFeeString) {
        this.serviceFeeString = serviceFeeString;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBuyUserName() {
        return buyUserName;
    }

    public void setBuyUserName(String buyUserName) {
        this.buyUserName = buyUserName;
    }

    public String getProcureStatus() {
        return procureStatus;
    }

    public void setProcureStatus(String procureStatus) {
        this.procureStatus = procureStatus;
    }

    public String getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(String receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public String getSupermarketList() {
        return supermarketList;
    }

    public void setSupermarketList(String supermarketList) {
        this.supermarketList = supermarketList;
    }

    public Integer getMaxSupermarketId() {
        return maxSupermarketId;
    }

    public void setMaxSupermarketId(Integer maxSupermarketId) {
        this.maxSupermarketId = maxSupermarketId;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Long getProcurementTaskId() {
        return procurementTaskId;
    }

    public void setProcurementTaskId(Long procurementTaskId) {
        this.procurementTaskId = procurementTaskId;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public Long getOrdersGoodsId() {
        return ordersGoodsId;
    }

    public void setOrdersGoodsId(Long ordersGoodsId) {
        this.ordersGoodsId = ordersGoodsId;
    }

    public Integer getSupermarketId() {
        return supermarketId;
    }

    public void setSupermarketId(Integer supermarketId) {
        this.supermarketId = supermarketId;
    }

    public Integer getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Integer goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(Integer actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
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

    public String getGoodsSkuName() {
        return goodsSkuName;
    }

    public void setGoodsSkuName(String goodsSkuName) {
        this.goodsSkuName = goodsSkuName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Date getProcureTime() {
		return procureTime;
	}

	public void setProcureTime(Date procureTime) {
		this.procureTime = procureTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }


    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public Integer getProcureQuantity() {
        return procureQuantity;
    }

    public void setProcureQuantity(Integer procureQuantity) {
        this.procureQuantity = procureQuantity;
    }

    public Integer getReceiveQuantity() {
        return receiveQuantity;
    }

    public void setReceiveQuantity(Integer receiveQuantity) {
        this.receiveQuantity = receiveQuantity;
    }

    public Integer getBoxQuantity() {
        return boxQuantity;
    }

    public void setBoxQuantity(Integer boxQuantity) {
        this.boxQuantity = boxQuantity;
    }

    public String getBoxStatus() {
        return boxStatus;
    }

    public void setBoxStatus(String boxStatus) {
        this.boxStatus = boxStatus;
    }

    public Long getDeliveryBoxId() {
        return deliveryBoxId;
    }

    public void setDeliveryBoxId(Long deliveryBoxId) {
        this.deliveryBoxId = deliveryBoxId;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getTotalProcureQuantity() {
        return totalProcureQuantity;
    }

    public void setTotalProcureQuantity(Integer totalProcureQuantity) {
        this.totalProcureQuantity = totalProcureQuantity;
    }

    public Integer getTotalProcurePrice() {
        return totalProcurePrice;
    }

    public void setTotalProcurePrice(Integer totalProcurePrice) {
        this.totalProcurePrice = totalProcurePrice;
    }

    public String getProcureTimeStr() {
        return procureTimeStr;
    }

    public void setProcureTimeStr(String procureTimeStr) {
        this.procureTimeStr = procureTimeStr;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public Date getProcureOutTime() {
        return procureOutTime;
    }

    public void setProcureOutTime(Date procureOutTime) {
        this.procureOutTime = procureOutTime;
    }

    public String getSupermarketName() {
        return supermarketName;
    }

    public void setSupermarketName(String supermarketName) {
        this.supermarketName = supermarketName;
    }

    public String getProcurementTaskIdList() {
        return procurementTaskIdList;
    }

    public void setProcurementTaskIdList(String procurementTaskIdList) {
        this.procurementTaskIdList = procurementTaskIdList;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDeliveryWaveTime() {
        return deliveryWaveTime;
    }

    public void setDeliveryWaveTime(String deliveryWaveTime) {
        this.deliveryWaveTime = deliveryWaveTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getWavePickingStartTime() {
        return wavePickingStartTime;
    }

    public void setWavePickingStartTime(Date wavePickingStartTime) {
        this.wavePickingStartTime = wavePickingStartTime;
    }
    public Date getWavePickingEndTime() {
        return wavePickingEndTime;
    }

    public void setWavePickingEndTime(Date wavePickingEndTime) {
        this.wavePickingEndTime = wavePickingEndTime;
    }

}
