package com.linayi.enums;

public enum RemoveType {
    NORMAL("正常"),
    REMOVED("已删除");
    /**
     * [状态] 正常：NORMAL  已删除：REMOVED
     */
    private String removeTypeName;

    RemoveType(String removeTypeName) {
        this.removeTypeName = removeTypeName;
    }

    public String getRemoveTypeName() {
        return removeTypeName;
    }

    public void setRemoveTypeName(String removeTypeName) {
        this.removeTypeName = removeTypeName;
    }

}
