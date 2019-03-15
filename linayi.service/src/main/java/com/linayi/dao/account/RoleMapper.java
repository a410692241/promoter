package com.linayi.dao.account;

import com.linayi.entity.account.Role;

import java.util.List;

public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);

    List<Role> getList();
}