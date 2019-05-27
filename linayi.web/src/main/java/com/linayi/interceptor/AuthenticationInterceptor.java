package com.linayi.interceptor;

import com.linayi.service.redis.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Aspect
@Component
public class AuthenticationInterceptor {
	@Resource
	private HttpServletRequest request;

	@Resource
	private HttpServletResponse response;
	@Resource
	private RedisService redisService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**不拦截路径*/
	private List<String> urlList ;

	@PostConstruct
	public void init(){
		urlList = new ArrayList<>();
		urlList.add("/account/employeeLogin.do");
		urlList.add("/common/picture/upload.do");
	}

	@Around("execution(* com.linayi.controller..*.*(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable {

		Object invokeResult = null;
		//获取路径
		String requestURL = request.getRequestURL().toString();
		logger.info(requestURL);
		//判断是否为拦截路径
		for (String string : urlList) {
			if (requestURL.endsWith(string)) {
				//不是，通过
				return point.proceed();
			}
		}



		//获取session
		HttpSession session=request.getSession();
		if(session.getAttribute("loginAccount")!=null){
			return point.proceed();
		}else{
			//重定向到登录界面
			String fullUrl = request.getScheme() + "://" + request.getServerName() +":"+request.getServerPort()+request.getContextPath();
			response.sendRedirect(fullUrl+"/login.jsp");
		}
		return invokeResult;
	}
}