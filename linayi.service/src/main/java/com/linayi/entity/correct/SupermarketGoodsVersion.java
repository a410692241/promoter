package com.linayi.entity.correct;

public class SupermarketGoodsVersion {
    private Long supermarketGoodsId;

    private Integer supermarketId;

    private Integer goodsSkuId;

    private Integer version;

    public Long getSupermarketGoodsId() {
        return supermarketGoodsId;
    }

    public void setSupermarketGoodsId(Long supermarketGoodsId) {
        this.supermarketGoodsId = supermarketGoodsId;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}