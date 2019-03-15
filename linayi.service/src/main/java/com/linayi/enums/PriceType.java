package com.linayi.enums;

public enum PriceType {
    NORMAL("正常价"),
    PROMOTION("促销价"),
    DEAL("处理价"),
    MEMBER("会员价");

    /**
     * [价格类型] 正常价：NORMAL  促销价：PROMOTION  处理价：DEAL  会员价：MEMBER
     */
    private String priceTypeName;

    private PriceType(String priceTypeName) {
        this.priceTypeName = priceTypeName;
    }

    public String getPriceTypeName() {
        return priceTypeName;
    }

    public void setPriceTypeName(String priceTypeName) {
        this.priceTypeName = priceTypeName;
    }
}
