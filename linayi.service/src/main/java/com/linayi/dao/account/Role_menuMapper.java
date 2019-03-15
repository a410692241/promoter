package com.linayi.dao.account;

import com.linayi.entity.account.Role_menu;

public interface Role_menuMapper {
    int insert(Role_menu record);

    int insertSelective(Role_menu record);

    int deleteById(Integer roleId);
}