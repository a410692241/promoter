package com.linayi.enums;

public enum AuditStatus {
    NOT_AUDIT("未审核"),
    AUDIT_SUCCESS("审核通过"),
    AUDIT_FAIL("价格错误");


    public String priceTypeName;

    AuditStatus(String priceTypeName) {
        this.priceTypeName = priceTypeName;
    }

    public String getPriceTypeName() {
        return priceTypeName;
    }

    public void setPriceTypeName(String priceTypeName) {
        this.priceTypeName = priceTypeName;
    }
}
