package com.linayi.entity.goods;

import java.util.Date;

public class CommunityGoods {
    private Long communityGoodsId;

    private Integer communityId;

    private Integer goodsSkuId;

    private Integer minSupermarketId;

    private Integer minPrice;

    private Integer maxSupermarketId;

    private Integer maxPrice;

    private Date createTime;

    private Date updateTime;

    public Long getCommunityGoodsId() {
        return communityGoodsId;
    }

    public void setCommunityGoodsId(Long communityGoodsId) {
        this.communityGoodsId = communityGoodsId;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public Integer getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Integer goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public Integer getMinSupermarketId() {
        return minSupermarketId;
    }

    public void setMinSupermarketId(Integer minSupermarketId) {
        this.minSupermarketId = minSupermarketId;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
}