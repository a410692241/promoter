package com.linayi.enums;

public enum ViewStatus {
    NOT_VIEW("未查看"),
    VIEWED("已查看");

    /**
     * [状态] 未查看：NOT_VIEW  已查看：VIEWED
     */
    private String ViewStatus;

    private ViewStatus(String ViewStatus) {
        this.ViewStatus = ViewStatus;
    }

    public String getViewStatus() {
        return ViewStatus;
    }

    public void setViewStatus(String ViewStatus) {
        this.ViewStatus = ViewStatus;
    }
}
