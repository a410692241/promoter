package com.linayi.entity.account;

import com.linayi.entity.BaseEntity;

public class AccountMenu extends BaseEntity {
    private Long accountMenuId;

    private Integer accountId;

    private Integer menuId;

    public Long getAccountMenuId() {
        return accountMenuId;
    }

    public void setAccountMenuId(Long accountMenuId) {
        this.accountMenuId = accountMenuId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}