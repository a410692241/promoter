package com.linayi.controller.account;

import com.alibaba.fastjson.JSON;
import com.linayi.entity.account.Account;
import com.linayi.entity.account.AdminAccount;
import com.linayi.entity.account.TreeNodeBO;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.account.AccountService;
import com.linayi.service.account.AdminAccountService;
import com.linayi.util.MD5Util;
import com.linayi.util.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/account")
public class AccountController {
    @Resource private AccountService accountService;
    @Resource
    private AdminAccountService adminAccountService;


    /**
     * 员工登录
     */
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户",produces = "application/xml,application/json")
    @RequestMapping(value = "/employeeLogin.do", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object employeelogin(HttpServletRequest request, HttpServletResponse response, String account, String password) {
        try {
            //接受数据
            Map<String, Object> map = (Map<String, Object>) adminAccountService.checkAccountPwd(account, password, "EMPLOYEE", request, response);
            //返回数据
            return new ResponseData(map);
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    @RequestMapping("/supplierLogin.do")
    @ResponseBody
    public Object supplierlogin(String account, String password) {
        return "8888888888888";
    }

    @RequestMapping("/getList.do")
    @ResponseBody
    public Object getList(AdminAccount param) {
        List<AdminAccount> list = adminAccountService.getList(param);
        return JSON.toJSONString(list);
    }

    //展示和搜索
    @RequestMapping("/list.do")
    @ResponseBody
    public Object accountList(Account account) {
        return adminAccountService.selectAdminAccountListJoinUserList(account);
    }

    //新增账号跳转的页面
    @RequestMapping("/edit.do")
    public String addAccountHtml(Model model, Account account) {
        return accountService.addAccountHtml(model, account);
    }


    //添加和修改账号用户信息
    @Transactional(rollbackFor = Throwable.class)
    @RequestMapping("/addUser.do")
    @ResponseBody
    public Object addAccount(Account account) {
        return adminAccountService.insertAccountAdmin(account);
    }

    //删除用户信息
    @Transactional(rollbackFor = Throwable.class)
    @ResponseBody
    @RequestMapping("/delUser.do")
    public Object delAccount(String accountId) {
        adminAccountService.deleteAccountAdminByaccountId(Integer.parseInt(accountId));
        return new ResponseData("删除成功").toString();
    }

    //修改状态
    @Transactional(rollbackFor = Throwable.class)
    @ResponseBody
    @RequestMapping("/cancelFrozen.do")
    public Object updateStatus(Account account) {
        return adminAccountService.updateAdminStatus(account);
    }

    //重置密码
    @Transactional(rollbackFor = Throwable.class)
    @ResponseBody
    @RequestMapping("/setUserPWD.do")
    public Object resetPassword(Account account) {
        adminAccountService.resetAdminPassword(account);
        return new ResponseData(account).toString();
    }

    //查询角色列表
    @ResponseBody
    @RequestMapping("/getRoleList.do")
    public Object getRole() {
        return accountService.selectRole();
    }

    //添加账号与角色的关系 重复的不插入
    @Transactional(rollbackFor = Throwable.class)
    @ResponseBody
    @RequestMapping("/addUserRole.do")
    public Object addUserRole(Account account) {
        return accountService.insertAccountRole(account);
    }

    //这是获取权限的暂时不做因为没表 还有树状图问题
    @ResponseBody
    @RequestMapping("/getRoleListByUser.do")
    public Object selectSameURole(Integer accountId) {
        return null;
    }

    //修改密码
    @ResponseBody
    @RequestMapping("/modifyPassword.do")
    public ResponseData modifyPsd(HttpSession session,String oldPassword,String newPassword){
        try{
            adminAccountService.modifyPsd(oldPassword,newPassword,session);
            session.removeAttribute("loginAccount");
        }catch (BusinessException e){
            return new ResponseData(ErrorType.ACCOUNT_OR_OLDPASSWORD_ERROR);
        }catch (Exception e){
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
        return new ResponseData("操作成功");
    }

    /*@ResponseBody
    @RequestMapping("/getPrivilegesList.do")
    public Object getPrivilegesList (Integer userId){
        Collection<TreeNodeBO> treeNodeBOs = accountService.getModelMenusByUserId(userId);
        return treeNodeBOs;
    }*/

    /*@ResponseBody
    @RequestMapping("/addUserRole.do")
    public Object selctRolebyId(Account account) {

    }*/
}
