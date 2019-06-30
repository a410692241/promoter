package com.linayi.enums;

public enum SourceType {
    USER("用户端"),ADMIN("后台");
    private String sourceTypeName;

    SourceType(String sourceTypeName) {
        this.sourceTypeName = sourceTypeName;
    }

    public String getSourceTypeName() {
        return sourceTypeName;
    }
}
