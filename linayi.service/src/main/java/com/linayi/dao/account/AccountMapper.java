package com.linayi.dao.account;

import com.linayi.entity.account.Account;
import com.linayi.entity.account.Role;

import java.util.List;

public interface AccountMapper {
    /**
     * @param accountId
     * @return 通过accountId查询这条记录
     */
    /*Account selectAccountByaccountId(Integer accountId);*/

    /*Account selectAdminAccountByaccountId(Integer accountId);*/

    /**
     * @param account
     * @return 通过account查询account集合
     */
    List<Account> selectAccountList(Account account);

    /**
     * 通过accountId更新指定记录指定字段
     *
     * @param account
     */
    void updateAccountByaccountId(Account account);

    /**
     * 通过accountId更新整条记录所有值
     *
     * @param account
     */
    /*void updateAccountAllByaccountId(Account account);*/

    /**
     * 通过accountId删除指定记录
     *
     * @param accountId
     */
    void deleteAccountByaccountId(Integer accountId);

    /**
     * 新增一条account记录
     *
     * @param account
     */
    void insertAccount(Account account);

    /**
     * @param mobile
     * @return accountId
     */
    Account selectByName(String mobile);

    /**
     * @param account
     */
    void updateStatus(Account account);

    /**
     * @param account
     */
    void resetPassword(Account account);

    List<Role> selectRole();

    Role selectIdbyRoleName(String roleName);

    void insertAccountRole(Account account);

    int selectByAccountId(Integer accountId);

    int selectByAr(Integer accountRoleId);

    List<Account> selectAccountListJoinUserList(Account account);

    List<Account> selectEmployeeList(Account account);

    void insertAccountAdmin(Account account);

    /*void deleteAccountAdminByaccountId(Integer accountId);*/

    /*void resetAdminPassword(Account account);*/

    /*void updateAdminStatus(Account account);*/

   /* List<Account> selectAdminAccountListJoinUserList(Account account);*/

    int selectUserIdByAccount(int accountId);
    
    Account getAccountById(Integer accountId);

    /**
     *通过社区ID查询社区的信息
     */
    Account selectAccountBycommunityId(Integer communityId);
    
    /**
     * 通过手机号查询userId
     * @param mobile
     * @return
     */
    Integer getUserIdByMobile(String mobile);
}