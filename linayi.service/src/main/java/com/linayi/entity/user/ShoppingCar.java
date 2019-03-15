package com.linayi.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "购物车实体类", description = "购物车对象")
public class ShoppingCar {
    @ApiModelProperty(value = "购物车Id")
    private Long shoppingCarId;

    private Integer userId;
    @ApiModelProperty(value = "商品Id")
    private Integer goodsSkuId;
    @ApiModelProperty(value = "商品数量")
    private Integer quantity;
    @ApiModelProperty(value = "收货地址Id")
    private Integer receiveAddressId;

    private String selectStatus;//已选中：SELECTED 未选中：NOT_SELECT
    @ApiModelProperty(value = "商品图片信息")
    private String GoodsSkuImage;//商品图片信息
    @ApiModelProperty(value = "商品名字")
    private String GoodsName;//商品名字
    @ApiModelProperty(value = "商品最低价")
    private String minPrice;//商品最低价
    @ApiModelProperty(value = "商品最高价")
    private String maxPrice;//商品最高价
    @ApiModelProperty(value = "商品最低价超市")
    private String minSupermarketName;//商品最低价超市
    @ApiModelProperty(value = "商品最高价超市")
    private String maxSupermarketName;//商品最高价超市
    @ApiModelProperty(value = "购物车商品合计")
    private String heJiPrice;//购物车商品合计
    @ApiModelProperty(value = "差价率")
    private String spreadRate;//差价率
    @ApiModelProperty(value = "订单商品状态")
    private String status;//订单商品状态

    public Long getShoppingCarId() {
        return shoppingCarId;
    }

    public void setShoppingCarId(Long shoppingCarId) {
        this.shoppingCarId = shoppingCarId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getReceiveAddressId() {
        return receiveAddressId;
    }

    public void setReceiveAddressId(Integer receiveAddressId) {
        this.receiveAddressId = receiveAddressId;
    }

    public String getSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(String selectStatus) {
        this.selectStatus = selectStatus == null ? null : selectStatus.trim();
    }

    public String getGoodsSkuImage() {
        return GoodsSkuImage;
    }

    public void setGoodsSkuImage(String goodsSkuImage) {
        GoodsSkuImage = goodsSkuImage;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMinSupermarketName() {
        return minSupermarketName;
    }

    public void setMinSupermarketName(String minSupermarketName) {
        this.minSupermarketName = minSupermarketName;
    }

    public String getMaxSupermarketName() {
        return maxSupermarketName;
    }

    public void setMaxSupermarketName(String maxSupermarketName) {
        this.maxSupermarketName = maxSupermarketName;
    }

    public String getHeJiPrice() {
        return heJiPrice;
    }

    public void setHeJiPrice(String heJiPrice) {
        this.heJiPrice = heJiPrice;
    }

    public String getSpreadRate() {
        return spreadRate;
    }

    public void setSpreadRate(String spreadRate) {
        this.spreadRate = spreadRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}