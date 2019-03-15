package com.linayi.enums;

public enum  ProcureStatus {
	PACKED("已装箱"),
	DELIVERING("配送中"),
	FINISHED("配送完成");

    ProcureStatus(String procureStatus) {
        this.procureStatus = procureStatus;
    }

    private String procureStatus;

    public String getProcureStatus() {
        return procureStatus;
    }

    public void setProcureStatus(String procureStatus) {
        this.procureStatus = procureStatus;
    }
}
