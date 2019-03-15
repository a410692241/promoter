package com.linayi.entity.goods;

import java.util.Date;

public class GoodsAttrValue {
    private Integer goodsAttrValueId;

    private Integer goodsSkuId;

    private Integer attrValueId;

    private Date updateTime;

    private Date createTime;

    public Integer getGoodsAttrValueId() {
        return goodsAttrValueId;
    }

    public void setGoodsAttrValueId(Integer goodsAttrValueId) {
        this.goodsAttrValueId = goodsAttrValueId;
    }

    public Integer getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Integer goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public Integer getAttrValueId() {
        return attrValueId;
    }

    public void setAttrValueId(Integer attrValueId) {
        this.attrValueId = attrValueId;
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