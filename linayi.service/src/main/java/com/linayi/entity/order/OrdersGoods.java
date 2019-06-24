package com.linayi.entity.order;

import com.linayi.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
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

    @ApiModelProperty(value = "价差率")
    private Double spreadRate;

    @ApiModelProperty(value = "最高价超市")
    private String  maxSupermarketName;


    @ApiModelProperty(value = "最低价超市")
    private String  minSupermarketName;

}