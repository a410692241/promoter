package com.linayi.dao.account;

import java.util.List;

import com.linayi.entity.account.Account;
import com.linayi.entity.account.AdminAccount;

public interface AdminAccountMapper {
    int insert(AdminAccount record);

    int insertSelective(AdminAccount record);
    
    List<AdminAccount> selectAdminAccountList(AdminAccount param);

    List<Account> selectAdminAccountListJoinUserList(Account account);

    Account selectAdminAccountByaccountId(Integer accountId);

    void deleteAccountAdminByaccountId(Integer accountId);

    /**
     * 通过accountId更新整条记录所有值
     *
     * @param account
     */
    void updateAccountAllByaccountId(Account account);

    /**
     * @param accountId
     * @return 通过accountId查询这条记录
     */
    Account selectAccountByaccountId(Integer accountId);

    /**
     * @param mobile
     * @return accountId
     */
    Account selectByName(String mobile);

    void insertAccountAdmin(Account account);

    void updateAdminStatus(Account account);

    void resetAdminPassword(Account account);

    AdminAccount selectAdminIdByUserId(int employeeId);

    AdminAccount getAdminAccountByKey(Integer accountId);
}