package com.linayi.entity.recode;

import java.util.Date;

public class SupermarketGoodsRecord {
    private Integer supermarketGoodsRecordId;

    private Integer goodsSkuId;

    private Integer supermarketId;

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSupermarketGoodsRecordId() {
        return supermarketGoodsRecordId;
    }

    public void setSupermarketGoodsRecordId(Integer supermarketGoodsRecordId) {
        this.supermarketGoodsRecordId = supermarketGoodsRecordId;
    }

    public Integer getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Integer goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public Integer getSupermarketId() {
        return supermarketId;
    }

    public void setSupermarketId(Integer supermarketId) {
        this.supermarketId = supermarketId;
    }
}