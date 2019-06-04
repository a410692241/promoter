package com.linayi.controller.user;

import com.linayi.entity.account.Account;
import com.linayi.service.account.AccountService;
import com.linayi.service.account.AdminAccountService;
import com.linayi.service.user.UserService;
import com.linayi.util.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * 这是员工的
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private UserService userService;
    @Resource
    private AccountService accountService;
    @Resource
    private AdminAccountService adminAccountService;

    //展示和搜索
    @RequestMapping("/list.do")
    @ResponseBody
    public Object employeeList(Account account) {
        return userService.employeeList(account);
        /*List<Account> list = accountService.selectAccountList(account);
        PageResult<Account> pageResult = new PageResult<Account>(list, account.getTotal());
        return pageResult;*/
    }

    //新增用户跳转的页面
    @RequestMapping("/edit.do")
    public String addEmployeeHtml(Model model, Account account) {
        return userService.addEmployeeHtml(model, account);
    }


    //添加和修改员工用户信息
    @RequestMapping("/addUser.do")
    @ResponseBody
    public Object addEmployee(Account account) {
        return userService.addEmployee(account);
    }

    //删除员工用户信息
    @Transactional(rollbackFor = Throwable.class)
    @ResponseBody
    @RequestMapping("/delUser.do")
    public Object delEmployee(Account account) {
        userService.deleteEmployeeById(account);
        return new ResponseData("success").toString();
    }

    //修改状态
    @Transactional(rollbackFor = Throwable.class)
    @ResponseBody
    @RequestMapping("/cancelFrozen.do")
    public Object updateStatus(Account account) {
        return userService.updateStatus(account);
    }

    //重置员工密码
    @Transactional(rollbackFor = Throwable.class)
    @ResponseBody
    @RequestMapping("/setUserPWD.do")
    public Object resetPassword(Account account) {
        adminAccountService.resetAdminPassword(account);
        return new ResponseData(account).toString();
    }
}
