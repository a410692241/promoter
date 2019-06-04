package com.linayi.entity.goods;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SkuClickNum {
    /**
    * [商品id]
    */
    private Integer goodsSkuId;

    /**
    * [点击日期]
    */
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date clickDate;
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date startTime;
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date endTime;

    /**
    * [点击量]
    */
    private Integer num;

    public Integer getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Integer goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public Date getClickDate() {
        return clickDate;
    }

    public void setClickDate(Date clickDate) {
        this.clickDate = clickDate;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}