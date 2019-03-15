package com.linayi.service.account.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linayi.entity.account.Account;
import com.linayi.util.*;
import org.springframework.stereotype.Service;

import com.linayi.dao.account.AdminAccountMapper;
import com.linayi.entity.account.AdminAccount;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.account.AdminAccountService;
import com.linayi.service.redis.RedisService;

@Service
public class AdminAccountServiceImpl implements AdminAccountService{

	@Resource
	private AdminAccountMapper adminAccountMapper;

	@Resource
	private RedisService redisService;

	@Override
	public void resetAdminPassword(Account account) {
		account.setPassword(MD5Util.MD5(PropertiesUtil.getValueByKey("resetPassword")));
		adminAccountMapper.resetAdminPassword(account);
	}

	@Override
	public Object updateAdminStatus(Account account) {
		if(account.getStatus().equals("1000")){
			account.setStatus("ENABLED");
		}else{
			account.setStatus("DISABLED");
		}
			adminAccountMapper.updateAdminStatus(account);
		return new ResponseData("success").toString();
	}

	@Override
	public Account selectAccountByaccountId(Integer accountId) {
		return adminAccountMapper.selectAccountByaccountId(accountId);
	}

	@Override
	public Integer getUserId(Integer accountId) {
		Account account = adminAccountMapper.selectAccountByaccountId(accountId);
		if(account != null){
			return account.getUserId();
		}
		return null;
	}

	@Override
	public Object insertAccountAdmin(Account account) {
		Date date = new Date();
		Integer accountId = account.getAccountId();
		if (accountId != null) {
			//修改,判断用户名和手机号是否跟之前一样,一样返回未做任何修改
			Account acc = adminAccountMapper.selectAccountByaccountId(accountId);
			if (acc.getMobile().equals(account.getMobile())) {
				return new ResponseData(ErrorType.UPDATE_ERROR).toString();
			}
			account.setUpdateTime(date);
			adminAccountMapper.updateAccountAllByaccountId(account);
		} else {
			//新增
			account.setCreateTime(date);
			account.setStatus("1000");
			account.setUserType("EMPLOYEE");
			Account a = adminAccountMapper.selectByName(account.getUserName());
			if (a != null) {
				return new ResponseData(ErrorType.ACCOUNT_ERROR).toString();
			}
			adminAccountMapper.insertAccountAdmin(account);
		}
		return new ResponseData("沙发斯蒂芬").toString();
	}

	@Override
	public void updateAccountAllByaccountId(Account account) {
		adminAccountMapper.updateAccountAllByaccountId(account);
	}

	@Override
	public Account selectAdminAccountByaccountId(Integer accountId) {
		return adminAccountMapper.selectAdminAccountByaccountId(accountId);
	}

	@Override
	public List<AdminAccount> getList(AdminAccount adminAccount) {
		List<AdminAccount> list = adminAccountMapper.selectAdminAccountList(adminAccount);
		return list;
	}

	@Override
	public void deleteAccountAdminByaccountId(Integer accountId) {
		adminAccountMapper.deleteAccountAdminByaccountId(accountId);
	}

	public Object selectAdminAccountListJoinUserList(Account account) {
		List<Account> list = adminAccountMapper.selectAdminAccountListJoinUserList(account);
		PageResult<Account> pageResult = new PageResult<Account>(list, account.getTotal());
		return pageResult;
	}

	/**
	 * 员工登录
	 * @throws UnsupportedEncodingException
	 */
	public Object checkAccountPwd(String account,String password,String userType,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		if(account!=null && account!="" && password!=null && password!=""){
			AdminAccount adminAccount=new AdminAccount();
			adminAccount.setUserName(account);
			adminAccount.setPassword(MD5Util.MD5(password));
			adminAccount.setUserType(userType);
			//校验用户名，密码
			List<AdminAccount>list= adminAccountMapper.selectAdminAccountList(adminAccount);
			if(list.size()!=0){
				Map<String,Object> map=new HashMap<String,Object>();
				for(AdminAccount admin:list){
					adminAccount.setAccountId(admin.getAccountId());
                    adminAccount.setPassword(null);
                    adminAccount.setUserId(admin.getUserId());
                    //放入返回的数据
					map.put("AdminAccount", adminAccount);
					request.getSession().setAttribute("loginAccount",adminAccount);
					}
				return map;
				}else{
					throw new BusinessException(ErrorType.ACCOUNT_OR_PASSWORD_ERROR);
				}
			}else{
				throw new BusinessException(ErrorType.ACCOUNT_OR_PASSWORD_ERROR);
			}
		}
	}



