package com.linayi.entity.account;

import java.util.Date;

import com.linayi.entity.BaseEntity;

public class AdminAccount extends BaseEntity{
    private Integer accountId;

    private String userName;

    private String password;

    private String mobile;

    private String userType;

    private Integer userId;

    private Integer incorrectTimes;

    private String status;

    private Integer creatorAccountId;

    private Date createTime;

    private Date lastLoginTime;

    @Override
    public String toString() {
        return "AdminAccount{" +
                "accountId=" + accountId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userType='" + userType + '\'' +
                ", userId=" + userId +
                ", incorrectTimes=" + incorrectTimes +
                ", status='" + status + '\'' +
                ", creatorAccountId=" + creatorAccountId +
                ", createTime=" + createTime +
                ", lastLoginTime=" + lastLoginTime +
                ", updateTime=" + updateTime +
                '}';
    }

    private Date updateTime;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIncorrectTimes() {
        return incorrectTimes;
    }

    public void setIncorrectTimes(Integer incorrectTimes) {
        this.incorrectTimes = incorrectTimes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getCreatorAccountId() {
        return creatorAccountId;
    }

    public void setCreatorAccountId(Integer creatorAccountId) {
        this.creatorAccountId = creatorAccountId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


}