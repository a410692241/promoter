package com.linayi.service.role.impl;

import com.linayi.dao.account.RoleMapper;
import com.linayi.dao.role.RoleEnumMapper;
import com.linayi.entity.account.Menu;
import com.linayi.entity.account.Role;
import com.linayi.entity.account.Role_menu;
import com.linayi.service.role.SelectRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class selectRoleServiceImpl implements SelectRoleService {
    @Autowired
    private RoleEnumMapper roleEnumMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role_menu> selectRoleList(Role_menu rm) {

        return roleEnumMapper.selectRoleList(rm);
    }

    @Override
    public void deleteRoleMenu(String roleMenuId) {
        roleEnumMapper.deleteRoleMenu(roleMenuId);
    }

    public  void updateStutus(Role_menu rm){
        roleEnumMapper.updateStutus(rm);
    }

    @Override
    public List<Menu> selectMenu(Menu menu) {
        return roleEnumMapper.selectMenu(menu);
    }

    @Override
    public Role_menu selectRoleByMenu(Long roleMenuId) {
        return roleEnumMapper.selectRoleByMenu(roleMenuId);
    }

    @Override
    public List<Role> getList() {
        return roleMapper.getList();
    }

    @Override
    public void insertRole(Role role) {
        role.setStatus("ENABLED");
        roleMapper.insert(role);
    }

   /* @Override
    public void updateRoleMenu(Role_menu rm) {
        roleEnumMapper.updateRoleMenu(rm);
    }

    @Override
    public void insertRoleMenu(Role_menu rm) {
        roleEnumMapper.insertRoleMenu(rm);
    }*/


}