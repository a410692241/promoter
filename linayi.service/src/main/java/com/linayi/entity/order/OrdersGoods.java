package com.linayi.entity.order;

import com.linayi.entity.BaseEntity;

import java.util.Date;

public class OrdersGoods extends BaseEntity {
    private Long ordersGoodsId;

    private Long ordersId;

    private Integer supermarketId;

    private Integer goodsSkuId;

    private Integer price;

    private Integer quantity;

    private Integer actualQuantity;

    private String supermarketList;

    private Integer maxSupermarketId;

    private String procureStatus;

    private Integer maxPrice;
    /*采买状态*/
    private String status;

    private Date updateTime;

    private Date createTime;

    private String totalPrice;//合计价格

    private Date endTime;//截止时间

    private String goodsSkuName;//商品名

    private String image;//图片

    private String communityName;//网点名字

    private String twoStatus;

    private String supermarketName;

    public String getSupermarketName() {
        return supermarketName;
    }

    public void setSupermarketName(String supermarketName) {
        this.supermarketName = supermarketName;
    }

    public String getTwoStatus() {
        return twoStatus;
    }

    public void setTwoStatus(String twoStatus) {
        this.twoStatus = twoStatus;
    }

    public Long getOrdersGoodsId() {
        return ordersGoodsId;
    }

    public void setOrdersGoodsId(Long ordersGoodsId) {
        this.ordersGoodsId = ordersGoodsId;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
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

    public String getSupermarketList() {
        return supermarketList;
    }

    public void setSupermarketList(String supermarketList) {
        this.supermarketList = supermarketList == null ? null : supermarketList.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getGoodsSkuName() {
        return goodsSkuName;
    }

    public void setGoodsSkuName(String goodsSkuName) {
        this.goodsSkuName = goodsSkuName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getProcureStatus() {
        return procureStatus;
    }

    public void setProcureStatus(String procureStatus) {
        this.procureStatus = procureStatus;
    }
}