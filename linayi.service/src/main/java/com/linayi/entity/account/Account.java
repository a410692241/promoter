package com.linayi.entity.account;

import com.linayi.entity.BaseEntity;

import java.util.Date;
import java.util.List;

public class Account extends BaseEntity {
    private Integer accountId;

    private Integer employeeId;

    private String userName;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    private String nickname;

    private String mobile;

    private String password;

    private String openId;

    private String userType;

    private Integer userId;

    private Integer incorrectTimes;

    private String status;

    private Date createTime;

    private Date lastLoginTime;

    private Date updateTime;

    private Integer creatorAccountId;

    private String realName;

    private String sex;

    private String qq;

    private Integer roleId;

    private String roleList;

    private List<Long> accountRoleIdList;

    public List<Long> getAccountRoleIdList() {
        return accountRoleIdList;
    }

    public void setAccountRoleIdList(List<Long> accountRoleIdList) {
        this.accountRoleIdList = accountRoleIdList;
    }

    public String getRoleList() {
        return roleList;
    }

    public void setRoleList(String roleList) {
        this.roleList = roleList;
    }

    public String getInitMobile() {
        return initMobile;
    }

    public void setInitMobile(String initMobile) {
        this.initMobile = initMobile;
    }

    public Integer getAccountRoleId() {
        return accountRoleId;
    }

    public void setAccountRoleId(Integer accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    private Integer accountRoleId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    /*原有userName*/
    private String initUserName;

    /*原来的mobile*/
    private String initMobile;


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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
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

    public Integer getCreatorAccountId() {
        return creatorAccountId;
    }

    public void setCreatorAccountId(Integer creatorAccountId) {
        this.creatorAccountId = creatorAccountId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getInitUserName() {
        return initUserName;
    }

    public void setInitUserName(String initUserName) {
        this.initUserName = initUserName;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userName='" + userName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", openId='" + openId + '\'' +
                ", userType='" + userType + '\'' +
                ", userId=" + userId +
                ", incorrectTimes=" + incorrectTimes +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", lastLoginTime=" + lastLoginTime +
                ", updateTime=" + updateTime +
                ", creatorAccountId=" + creatorAccountId +
                ", realName='" + realName + '\'' +
                ", sex='" + sex + '\'' +
                ", qq='" + qq + '\'' +
                ", email='" + email + '\'' +
                ", initUserName='" + initUserName + '\'' +
                ", initMobile='" + initMobile + '\'' +
                '}';
    }
}