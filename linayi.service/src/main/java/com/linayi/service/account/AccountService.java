package com.linayi.service.account;

import java.util.List;

import com.linayi.entity.account.Account;
import com.linayi.entity.account.AccountRole;
import com.linayi.entity.account.Role;
import com.linayi.exception.ErrorType;
import org.springframework.ui.Model;

/**
 *
 */
public interface AccountService {

	/**
	 * @param accountId
	 * @return 通过accountId查询这条记录
	 */
	/*Account selectAccountByaccountId(Integer accountId);*/

	/**
	 * @param account
	 * @return 通过account查询account集合
	 */
	List<Account> selectAccountList(Account account);

	/** 通过accountId更新指定记录指定字段
	 * @param account
	 */
	void updateAccountByaccountId(Account account);

	/**通过accountId更新整条记录所有值
	 * @param account
	 */
	void updateAccountAllByaccountId(Account account);

	/**通过accountId删除指定记录
	 * @param accountId
	 */
	void deleteAccountByaccountId(Integer accountId);

	/**新增一条account记录
	 * @param account
	 */
	void insertAccount(Account account);
	/**
	 * 通过号码查id
	 * @param
	 *
	 */

	boolean existMobile(String mobile);

	/**
	 * 通过号码查id
	 * @param
	 *
	 */
	Account selectByName(String name);

	/**
	 *
	 * @param account
	 */
	void updateStatus(Account account);

	/**
	 *
	 * @param account
	 */
	void resetPassword(Account account);
	Object regist(String mobile, String code, String password);

	public Object selectAccount(Account account);

	public Object addAccount(Account account);

	public Object updateAccountStatus(Account account);

	public String addAccountHtml(Model model, Account account);

	List<Role> selectRole();

	Role selectIdbyRoleName(String roleName);

	Object insertAccountRole(Account account);

	/**
	 * @param accountId
	 * @return 通过accountId找userId
	 */
    Integer getUserId(Integer accountId);

    /*Object selectByAccountId(Integer accountId);*/
	/*Object insertAccountAdmin(Account account);*/

	/*void deleteAccountAdminByaccountId(Integer accountId);*/

	/*void resetAdminPassword(Account account);*/

	/*Object updateAdminStatus(Account account);*/

	/*Object selectByAccountId(Integer accountId);*/
	/*Object selectAdminAccountListJoinUserList(Account account);*/
	/*public Account selectAdminAccountByaccountId(Integer accountId);*/

	/** 返回登录结果
	 * @param account
	 * @return
	 */
	String login(Account account);

	/***
	 * 通过账号id来获取用户id
	 * @param accountId
	 * @return
	 */
	int selectUserIdByAccount(int accountId);

	/**重置密码
	 * @param mobile
	 * @param code
	 * @param password
	 * @return
	 */
	Object resetPsw(String mobile, String code, String password);


	/**检查是否绑定手机号
	 * @param accountId
	 * @return
	 */
	boolean whetherAccountBindMobile(Integer accountId);

	/**
	 * @param accountId 微信账号绑定手机号
	 * @return
	 */

	Object bindMobile(Integer accountId, String mobile, String validCode);


    Object communityLogin(Account account);

    /**
	 * 根据用户的Id查询权限菜单
	 * @param userId
	 * @return
	 *//*
    Collection<TreeNodeBO> getModelMenusByUserId(Integer userId);*/

	/**
	 * 查询社区信息
	 * @param communityId 社区Id
	 * @return 返回社区对象
	 */
	Account selectAccountBycommunityId(Integer communityId);

	List<AccountRole> getAccountRoleLists(Integer accountId);

	/**判断该账号是否绑定手机号
	 * @param accountId
	 * @return
	 */
	boolean isBindMobile(Integer accountId);


}
