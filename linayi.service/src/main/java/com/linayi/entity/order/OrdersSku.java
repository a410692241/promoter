package com.linayi.entity.order;

import com.linayi.entity.BaseEntity;

import java.util.Date;


public class OrdersSku  extends BaseEntity {

    private Integer ordersId; //订单id

    private Integer communityId; //小区id

    private String goodsName;   //商品名

    private String receiveOption; //收付状态

    private Integer price; //价格

    private String supermarketName; //超市名称

    private Date createTime; //下单时间

    private Integer quantity; //数量

    private String supermarketList;

    private Long ordersGoodsId;

    private String procureStatus;//[采买状态] ：待采买：WAIT_PROCURE  缺货：NO_GOODS  价高未买：PRICE_HIGH  已买：BOUGHT

    private String communityStatus;

    private Integer goodsSkuId;

    private String image;

    private Long procurementTaskId;

    private String fullName;

    private Integer procureQuantity;

    public Long getProcurementTaskId() {
        return procurementTaskId;
    }

    public void setProcurementTaskId(Long procurementTaskId) {
        this.procurementTaskId = procurementTaskId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Integer goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public Long getOrdersGoodsId() {
        return ordersGoodsId;
    }

    public void setOrdersGoodsId(Long ordersGoodsId) {
        this.ordersGoodsId = ordersGoodsId;
    }

    public String getSupermarketList() {
        return supermarketList;
    }

    public void setSupermarketList(String supermarketList) {
        this.supermarketList = supermarketList;
    }

    public String getCommunityStatus() {
        return communityStatus;
    }

    public void setCommunityStatus(String communityStatus) {
        this.communityStatus = communityStatus;
    }

    public String getProcureStatus() {
        return procureStatus;
    }

    public void setProcureStatus(String procureStatus) {
        this.procureStatus = procureStatus;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getReceiveOption() {
        return receiveOption;
    }

    public void setReceiveOption(String receiveOption) {
        this.receiveOption = receiveOption;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSupermarketName() {
        return supermarketName;
    }

    public void setSupermarketName(String supermarketName) {
        this.supermarketName = supermarketName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getProcureQuantity() {
        return procureQuantity;
    }

    public void setProcureQuantity(Integer procureQuantity) {
        this.procureQuantity = procureQuantity;
    }
}
