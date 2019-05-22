package com.linayi.service.user;

import com.linayi.entity.account.Account;
import com.linayi.entity.account.Employee;
import com.linayi.entity.promoter.PromoterOrderMan;
import com.linayi.entity.user.User;
import com.linayi.util.PageResult;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface UserService {
    /**
     * @param userId
     * @return 通过userId查询这条记录
     */
    User selectUserByuserId(Integer userId);

    /**
     * @param user
     * @return 通过user查询user集合
     */
    List<User> selectUserList(User user);

    List<User> selectUserListByWeb(User user);

    PageResult<User> selectUserListPage(User user);

    /** 通过userId更新指定记录指定字段
     * @param user
     */
    void updateUserByuserId(User user);

    /**通过userId更新整条记录所有值
     * @param user
     */
    void updateUserAllByuserId(User user);

    /**通过userId删除指定记录
     * @param userId
     */
    void deleteUserByuserId(Integer userId);

    /**新增一条user记录
     * @param user
     */
    void insertUser(User user);

    /**查询帐号表和用户表的数据
     * @param account
     */
    List<Account> selectAccountList(Account account);

    public Object employeeList(Account account);

    public String addEmployeeHtml(Model model, Account account);

    public Object addEmployee(Account account);

    public Object updateStatus(Account account);

    public Object saveUser(User user);

    List<Account> selectEmployeeList(Account account);

    void deleteEmployeeById(Account account);
    Integer selectUserByAccountId(Integer accountId);

    /**
     * 我的资料
     * @param user
     * @return
     */
    User userInfo(User user);
    /**
     * 保存用户资料
     * @param user
     */
    void saveUserInfo(MultipartFile headFile, User user);
    
    Account getUserIdByToken(String token);

    /**
     * 获取用户信息(代言人参选页面:昵称,电话,区域)
     * @param userId
     * @return
     */
    User getUserForSpokesMan(Integer userId);
}

