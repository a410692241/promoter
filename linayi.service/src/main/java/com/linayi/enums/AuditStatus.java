package com.linayi.enums;

public enum AuditStatus {
    NOT_AUDIT("未审核"),
    AUDIT_SUCCESS("审核通过"),
    AUDIT_FAIL_EXPIRED("价格错误过期"),
    AUDIT_FAIL_CORRECT("价格错误纠错");


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
