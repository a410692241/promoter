package com.linayi.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.redis.RedisService;

@Aspect
@Component
public class AuthenticationInterceptor {

    @Resource
    private RedisService redisService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final static List<String> notNeedCheckTokenMethods = new ArrayList<>();
	static {
		notNeedCheckTokenMethods.add("/account/account/login.do");
		notNeedCheckTokenMethods.add("/account/account/regist.do");
		notNeedCheckTokenMethods.add("/user/weixin/getAuthUrl.do");
		notNeedCheckTokenMethods.add("/user/weixin/getCode.do");
		notNeedCheckTokenMethods.add("/account/account/getValidCode.do");
		notNeedCheckTokenMethods.add("/account/account/changePsw.do");
		notNeedCheckTokenMethods.add("/account/account/resetPsw.do");
		notNeedCheckTokenMethods.add("/account/account/logout.do");
		notNeedCheckTokenMethods.add("/common/picture/upload.do");
		notNeedCheckTokenMethods.add("/account/account/communityLogin.do");
		notNeedCheckTokenMethods.add("/area/area/getAreaMap.do");
		notNeedCheckTokenMethods.add("/getCommunityAppUpdateInfo.do");
	}


    @Around("execution(* com.linayi.controller..*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {

//		logger.debug("访问接口:", point.getan);
    	boolean needCheck;
		String methodName = getMethodName();
		logger.info(getRequest().getRequestURL().toString());
		if(notNeedCheckTokenMethods.contains(methodName)){
    		needCheck = false;
    	}else{
    		needCheck = true;
    	}

    	if(needCheck){
    		String accessToken= getRequest().getHeader("accessToken");
    		boolean checkPass = redisService.checkAccessToken(accessToken);
    		if(!checkPass){
    			throw new BusinessException(ErrorType.TOKEN_DISABLED);
    		}
    	}

        return point.proceed();

    }

    public String getMethodName(){
        String uri = getRequest().getRequestURI();
        String methodName = uri.substring(uri.indexOf("/",1));
        return methodName;
    }

    public HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }


}

