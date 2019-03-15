package com.linayi.controller.role;

import com.linayi.entity.account.Menu;
import com.linayi.entity.account.Role;
import com.linayi.service.role.SelectRoleService;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private SelectRoleService selectRoleService;

    @RequestMapping("/list.do")
    @ResponseBody
    public List<Role> getRoleList() {
        return selectRoleService.getList();
    }

    @RequestMapping("/getMenu.do")
    @ResponseBody
    public Object getMenu(Menu menu) {
        return selectRoleService.selectMenu(menu);
    }

    @RequestMapping("/edit.do")
    public String edit() {
        return "/jsp/system/addRole";
    }

    @RequestMapping("/addRoleMenu.do")
    @ResponseBody
    public String addRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        selectRoleService.insertRole(role);
        return new ResponseData("success").toString();
    }

    @RequestMapping("/delRoleMenu.do")
    @ResponseBody
    public String deleteRole(String roleMenuId) {
        selectRoleService.deleteRoleMenu(roleMenuId);
        return new ResponseData("success").toString();
    }
}
