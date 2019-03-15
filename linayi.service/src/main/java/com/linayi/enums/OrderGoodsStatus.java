package com.linayi.enums;

public enum  OrderGoodsStatus {
    WAIT_PROCURE("待采买"),
    NO_GOODS("缺货"),
    PRICE_HIGH("价高未买"),
    BOUGHT("已买");

    private String orderGoodsStatusName;

    OrderGoodsStatus(String orderGoodsStatusName) {
        this.orderGoodsStatusName = orderGoodsStatusName;
    }

    /*[采买状态] ：待采买：WAIT_PROCURE  缺货：NO_GOODS  价高未买：PRICE_HIGH  已买：BOUGHT*/

    public String getOrderGoodsStatusName() {
        return orderGoodsStatusName;
    }

    public void setOrderGoodsStatusName(String orderGoodsStatusName) {
        this.orderGoodsStatusName = orderGoodsStatusName;
    }
}
