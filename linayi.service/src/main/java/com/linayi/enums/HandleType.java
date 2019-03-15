package com.linayi.enums;

public enum HandleType {
    WAIT_DEAL("等待处理"),
    SUCCESS("处理成功"),
    FAIL("处理失败");

    /**
     * [状态类型] 等待处理：WAIT_DEAL  处理成功：SUCCESS  处理失败：FAIL
     */
    private String HandleTypeName;

    private HandleType(String HandleTypeName) {
        this.HandleTypeName = HandleTypeName;
    }

    public String getHandleTypeName() {
        return HandleTypeName;
    }

    public void setHandleTypeName(String HandleTypeName) {
        this.HandleTypeName = HandleTypeName;
    }
}
