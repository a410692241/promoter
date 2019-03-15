package com.linayi.service.account;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linayi.entity.account.Account;
import com.linayi.entity.account.AdminAccount;

public interface AdminAccountService {
	public List<AdminAccount> getList(AdminAccount adminAccount);
	
    Object checkAccountPwd(String account,String password,String userType,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException;

    Object selectAdminAccountListJoinUserList(Account account);

    public Account selectAdminAccountByaccountId(Integer accountId);

    void deleteAccountAdminByaccountId(Integer accountId);

    /**通过accountId更新整条记录所有值
     * @param account
     */
    void updateAccountAllByaccountId(Account account);

    Object insertAccountAdmin(Account account);

    /**
     * @param accountId
     * @return 通过accountId查询这条记录
     */
    Account selectAccountByaccountId(Integer accountId);

    /**
     * @param accountId
     * @return 通过accountId找userId
     */
    Integer getUserId(Integer accountId);

    Object updateAdminStatus(Account account);

    void resetAdminPassword(Account account);

}
