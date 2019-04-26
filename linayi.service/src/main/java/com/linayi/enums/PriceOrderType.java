package com.linayi.enums;

public enum  PriceOrderType {

    NORMAL("综合"),
    PRICE_UP("价格升序"),
    PRICE_DOWN("价格降序")/*,
    SOLD_NUM("销量升序")*/;

    private String priceOrderTypeName;

    PriceOrderType(String priceOrderTypeName) {
        this.priceOrderTypeName = priceOrderTypeName;
    }

    public String getPriceOrderTypeName() {
        return priceOrderTypeName;
    }

    public void setPriceOrderTypeName(String priceOrderTypeName) {
        this.priceOrderTypeName = priceOrderTypeName;
    }
}
