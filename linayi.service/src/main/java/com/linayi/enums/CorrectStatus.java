package com.linayi.enums;

public enum CorrectStatus {
    WAIT_AUDIT("待审核"),
    AUDIT_SUCCESS("审核通过"),
    RECALL("撤回"),
    AUDIT_FAIL("审核不通过"),
    AFFECTED("已生效"),
    NOT_AUDIT("未审核"),
    AUDIT_FAIL_EXPIRED("价格错误过期"),
    AUDIT_FAIL_CORRECT("价格错误修改价格"),
    EXPIRED("已过期");


    /**
     * [状态] 待审核：WAIT_AUDIT  审核通过：AUDIT_SUCCESS  撤回：RECALL  审核不通过：AUDIT_FAIL  已生效：AFFECTED   已过期：EXPIRED
     *
     */
    private String correctTypeName;

    CorrectStatus(String correctTypeName) {
        this.correctTypeName = correctTypeName;
    }

    public String getCorrectTypeName() {
        return correctTypeName;
    }

    public void setCorrectTypeName(String correctTypeName) {
        this.correctTypeName = correctTypeName;
    }
}
