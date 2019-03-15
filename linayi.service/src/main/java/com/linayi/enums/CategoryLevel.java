package com.linayi.enums;

public enum CategoryLevel {
    first(1,"一级"),second(2,"二级"),third(3,"三级"),fourth(4,"四级");

    /**
     *一级：1；二级：2；三级：3：四级 :商品
     *
     */

    // 等级值
    private int value;

    // 等级名
    private String levelName;

    CategoryLevel(int value, String levelName) {
        this.value = value;
        this.levelName = levelName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
