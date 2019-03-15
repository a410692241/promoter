package com.linayi.dao.account;

import com.linayi.entity.account.RoleMenu;

public interface RoleMenuMapper {
    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    int deleteByMenuIdAndRoleId(RoleMenu roleMenu);
}