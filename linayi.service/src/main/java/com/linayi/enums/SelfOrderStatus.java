package com.linayi.enums;

public enum SelfOrderStatus {
	WAIT_DEAL("等待处理"),
	SUCCESS("处理成功"),
	FAIL("处理失败"),
	PROCESSING("采价中");

    /**
     * [状态] 等待处理：WAIT_DEAL  处理成功：SUCCESS  处理失败：FAIL 
     */
    private String SelfOrderStatus;

    private SelfOrderStatus(String SelfOrderStatus) {
        this.SelfOrderStatus = SelfOrderStatus;
    }

    public String getSelfOrderStatus() {
        return SelfOrderStatus;
    }

    public void setSelfOrderStatus(String SelfOrderStatus) {
        this.SelfOrderStatus = SelfOrderStatus;
    }
}
