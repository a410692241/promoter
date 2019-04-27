package com.linayi.enums;

public enum IsRecommendStatus {
    TRUE("是"),
    FALSE("否");
    private String isRecommend;

    IsRecommendStatus() {
    }

    IsRecommendStatus(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }
}
