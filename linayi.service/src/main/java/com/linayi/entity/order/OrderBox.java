package com.linayi.entity.order;

import java.util.Date;

public class OrderBox {
    private Long orderBoxId;

    private Long ordersId;

    private String boxNo;

    private String image;

    private Date boxTime;

    public Long getOrderBoxId() {
        return orderBoxId;
    }

    public void setOrderBoxId(Long orderBoxId) {
        this.orderBoxId = orderBoxId;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo == null ? null : boxNo.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Date getBoxTime() {
        return boxTime;
    }

    public void setBoxTime(Date boxTime) {
        this.boxTime = boxTime;
    }
}