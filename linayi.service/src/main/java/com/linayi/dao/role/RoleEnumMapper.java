package com.linayi.dao.role;

import com.linayi.entity.account.Account;
import com.linayi.entity.account.Menu;
import com.linayi.entity.account.Role;
import com.linayi.entity.account.Role_menu;


import java.util.List;

public interface RoleEnumMapper {

    /**
     * @param
     * @return
     */
    List<Role_menu> selectRoleList(Role_menu rm);

    /**
     *
     * @param roleMenuId 角色菜单id
     */
    void deleteRoleMenu(String roleMenuId);

    void updateStutus(Role_menu rm);

    List<Menu> selectMenu(Menu menu);

    Role_menu selectRoleByMenu(Long roleMenuId);

    List<Role> selectRole();

    Role selectIdbyRoleName(String roleName);

    void insertAccountRole(Account account);

    /*void updateRoleMenu(Role_menu rm);

    void insertRoleMenu(Role_menu rm);*/
}