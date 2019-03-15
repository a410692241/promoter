package com.linayi.dao.account;

import com.linayi.entity.account.Menu;

import java.util.List;

public interface MenuMapper {

    List<Menu> getMenuByRoleId(Integer roleId) ;

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> getMenuList();
}