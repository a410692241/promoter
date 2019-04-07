package com.linayi.controller.account;

import com.linayi.service.account.RoleMenuService;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/roleMenu")
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    @RequestMapping("/updateRolePrivilage.do")
    @ResponseBody
    public Object updateRolePrivilage(Integer roleId,@RequestParam("privilegeIdList") List<Integer> privilegeIdList){
        try {
            roleMenuService.updateRolePrivilage(roleId,privilegeIdList);
            return new ResponseData("操作成功！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseData("操作失败！");
    }
}
