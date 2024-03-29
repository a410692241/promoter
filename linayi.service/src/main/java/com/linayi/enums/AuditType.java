package com.linayi.enums;

public enum AuditType {
    WAIT_AUDIT("未生效"),
    AFFECTED("已生效");


    public String priceTypeName;

    AuditType(String priceTypeName) {
        this.priceTypeName = priceTypeName;
    }

    public String getPriceTypeName() {
        return priceTypeName;
    }

    public void setPriceTypeName(String priceTypeName) {
        this.priceTypeName = priceTypeName;
    }
}
