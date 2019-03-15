package com.linayi.enums;

/**
 * author:quanzh
 * time:2019-01-23
 */
public enum OrderStatus {
//    PUBLISHING_PROCUREMENT("正在发布采买任务"),
//    PROCURING("商品采买中"),
//    PROCUREMENT_FINISHED("采买完成"),
//    DELIVERING("配送中");
//采买中：PROCURING   采买完成：PROCURE_FINISHED   全部已收货（社区端）：RECEIVED   已装箱：PACKED  配送中：DELIVERING   配送完成：DELIVER_FINISHED
    PROCURING("采买中"),
    PROCURE_FINISHED("采买完成"),
    RECEIVED("全部已收货（社区端）"),
    PACKED("已装箱"),
    DELIVERING("配送中"),
    DELIVER_FINISHED("配送完成"),
    CANCELED("取消");

    private String orderTypeName;

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    OrderStatus(String orderTypeName){
        this.orderTypeName=orderTypeName;
    }

}
