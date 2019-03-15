package com.linayi.enums;

public enum DeliveryStatus {
    PACKED("已装箱"),
    DELIVERING("配送中"),
    FINISHED("配送完成");

    /**
     * [状态] 已装箱：PACKED; 配送中：DELIVERING;  配送完成：FINISHED;
     */
    private String DeliveryStatusName;

    DeliveryStatus(String DeliveryStatusName) {
        this.DeliveryStatusName = DeliveryStatusName;
    }

    public String getDeliveryStatusName() {
        return DeliveryStatusName;
    }

    public void setDeliveryStatusName(String DeliveryStatusName) {
        this.DeliveryStatusName = DeliveryStatusName;
    }
}
