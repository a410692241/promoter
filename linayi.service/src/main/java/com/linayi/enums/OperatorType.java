package com.linayi.enums;

public enum OperatorType {
    USER("USER"),//用户端
    ADMIN("ADMIN");//后台管理员



    /**
     * [状态] 用户端：USER  后台管理系统：ADMIN
     *
     */
    private String operatorTypeName;

    OperatorType(String operatorTypeName) {
        this.operatorTypeName = operatorTypeName;
    }

    public String getOperatorTypeName() {
        return operatorTypeName;
    }

    public void setOperatorTypeName(String operatorTypeName) {
        this.operatorTypeName = operatorTypeName;
    }
}
