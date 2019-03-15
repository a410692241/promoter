package com.linayi.enums;

public enum  CorrectType {
    SHARE("分享"),
    CORRECT("纠错");

    /**
     * [类型] 分享：SHARE  纠错：CORRECT
     */

    private String correctTypeName;

    CorrectType(String correctTypeName) {
        this.correctTypeName = correctTypeName;
    }

    public String getCorrectTypeName() {
        return correctTypeName;
    }

    public void setCorrectTypeName(String correctTypeName) {
        this.correctTypeName = correctTypeName;
    }
}
