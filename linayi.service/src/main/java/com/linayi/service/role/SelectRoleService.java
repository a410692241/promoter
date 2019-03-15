package com.linayi.service.role;

import com.linayi.entity.account.Menu;
import com.linayi.entity.account.Role;
import com.linayi.entity.account.Role_menu;

import java.util.List;

public interface SelectRoleService {
    /**
     * @param
     * @return
     */
    List<Role_menu> selectRoleList(Role_menu rm);

    /**
     *
     * @param roleMenuId
     */
    void deleteRoleMenu(String roleMenuId);

    void updateStutus(Role_menu rm);

    List<Menu> selectMenu(Menu menu);

    Role_menu selectRoleByMenu(Long roleMenuId);

    /**
     * 查询所有的角色
     * @return
     */
    List<Role> getList();

    /*void updateRoleMenu(Role_menu rm);

    void insertRoleMenu(Role_menu rm);*/
    void insertRole(Role role);
}
