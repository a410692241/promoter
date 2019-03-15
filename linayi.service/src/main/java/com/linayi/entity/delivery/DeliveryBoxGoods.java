package com.linayi.entity.delivery;

import java.util.Date;

public class DeliveryBoxGoods {
    private Long deliveryBoxGoodsId;

    private Long deliveryBoxId;

    private Integer goodsSkuId;

    private Integer quantity;

    private Date updateTime;

    private Date createTime;

    private Integer supermarketId;

    private Integer price;

    public Integer getSupermarketId() { return supermarketId; }

    public void setSupermarketId(Integer supermarketId) { this.supermarketId = supermarketId; }

    public Integer getPrice() { return price; }

    public void setPrice(Integer price) { this.price = price; }

    public Long getDeliveryBoxGoodsId() {
        return deliveryBoxGoodsId;
    }

    public void setDeliveryBoxGoodsId(Long deliveryBoxGoodsId) {
        this.deliveryBoxGoodsId = deliveryBoxGoodsId;
    }

    public Long getDeliveryBoxId() {
        return deliveryBoxId;
    }

    public void setDeliveryBoxId(Long deliveryBoxId) {
        this.deliveryBoxId = deliveryBoxId;
    }

    public Integer getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Integer goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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