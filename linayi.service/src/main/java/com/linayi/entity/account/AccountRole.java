package com.linayi.entity.account;

import com.linayi.entity.BaseEntity;

public class AccountRole extends BaseEntity {
    private Long accountRoleId;

    private Integer accountId;

    private Integer roleId;

    public Long getAccountRoleId() {
        return accountRoleId;
    }

    public void setAccountRoleId(Long accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}