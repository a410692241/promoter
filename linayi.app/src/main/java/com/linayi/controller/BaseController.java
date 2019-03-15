package com.linayi.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.linayi.dao.account.AccountRoleMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.account.Account;
import com.linayi.enums.UserType;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.account.AccountService;
import com.linayi.service.community.CommunityService;
import com.linayi.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.linayi.service.user.UserService;

public class BaseController {
	@Resource
    private RedisService redisService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommunityService communityService;

	public HttpServletRequest getRequest(){
    	return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
	
	public String getAccessToken(){
    	return getRequest().getHeader("accessToken");
    }
	
	public Integer getUserId(){
		Account userByToken = userService.getUserIdByToken(getAccessToken());
		if(userByToken != null){
			return userByToken.getUserId();
		}
		return null;
	}
	public Integer getAccountId() {
	return 	redisService.getAccountIdByToken(getAccessToken());
	}


	/**
	 * @return 通过accessToken获取communityId
	 */
	public Integer getCommunityId() {
		Account userByToken = userService.getUserIdByToken(getAccessToken());
		//社区账户account的userId实际上是communityId
		if(userByToken != null && (UserType.COMMUNITY.name().equals(userByToken.getUserType()))){
			return userByToken.getUserId();
		}
		throw new BusinessException(ErrorType.NOT_A_COMMUNITY_ACCOUNT);
	}
}
