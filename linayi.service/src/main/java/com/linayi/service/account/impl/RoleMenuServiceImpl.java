package com.linayi.service.account.impl;

import com.linayi.dao.account.MenuMapper;
import com.linayi.dao.account.RoleMenuMapper;
import com.linayi.entity.account.Menu;
import com.linayi.entity.account.RoleMenu;
import com.linayi.service.account.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Transactional
    @Override
    public void updateRolePrivilage(Integer roleId, List<Integer> privilegeIdList) {
        List<Menu> menus = menuMapper.getMenuByRoleId(roleId);
        Set<Integer> lists = new HashSet<>();
        if(privilegeIdList != null && privilegeIdList.size() > 0){
            for (Integer integer : privilegeIdList) {
                lists.add(integer);
            }
            for (Integer integer : lists) {
                boolean isExist = false;
                if (menus != null && menus.size() > 0){
                    for (Menu menu : menus) {
                        //删除privilegeIdList中不含有的menuId
                        if(!privilegeIdList.contains(menu.getMenuId())){
                            RoleMenu roleMenu = new RoleMenu();
                            roleMenu.setMenuId(menu.getMenuId());
                            roleMenu.setRoleId(roleId);
                            roleMenuMapper.deleteByMenuIdAndRoleId(roleMenu);
                        }
                        if(menu.getMenuId() == integer){
                            isExist = true;
                        }
                    }
                }
                //不存在menuId则新增
                if(!isExist && integer != null){
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(roleId);
                    roleMenu.setMenuId(integer);
                    roleMenuMapper.insert(roleMenu);
                }
            }
        }else {
            //通过角色id直接删除所有
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenuMapper.deleteByMenuIdAndRoleId(roleMenu);
        }
    }
}
