package com.linayi.entity.user;

import java.util.Date;

public class Message {
    private Long messageId;

    private String messageType;

    private String title;

    private String content;

    private Integer userId;

    private String userType;

    private String viewStatus;

    private Date updateTime;

    private Date createTime;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType == null ? null : messageType.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus == null ? null : viewStatus.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Message(String messageType, String title, String content, Integer userId, String userType, String viewStatus, Date updateTime, Date createTime) {
        this.messageType = messageType;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.userType = userType;
        this.viewStatus = viewStatus;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public Message() {
        super();
    }
}