package com.linayi.enums;

public enum MessageType {
    NORMAL("普通消息"),
    SELF_ORDER("自定义下单");

    /**
     * [状态类型] 普通消息：NORMAL  自定义下单：SELF_ORDER
     */
    private String MessageTypeName;

    private MessageType(String MessageTypeName) {
        this.MessageTypeName = MessageTypeName;
    }

    public String getMessageTypeName() {
        return MessageTypeName;
    }

    public void setMessageTypeName(String MessageTypeName) {
        this.MessageTypeName = MessageTypeName;
    }
}
