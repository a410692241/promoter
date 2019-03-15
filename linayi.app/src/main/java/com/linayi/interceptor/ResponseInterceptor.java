package com.linayi.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linayi.dao.account.AccountRoleMapper;
import com.linayi.entity.account.Account;
import com.linayi.entity.api.ApiLogs;
import com.linayi.service.account.AccountService;
import com.linayi.service.api.ApiLogsService;
import com.linayi.service.redis.RedisService;
import com.linayi.service.user.UserService;
import com.linayi.util.ResponseData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数返回AOP封装类,对请求头允许跨域访问,对String字符串做的乱码修正
 */
@Aspect
@Component
public class ResponseInterceptor {

    @Autowired
    private ApiLogsService apiLogsService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    /**
     * @param joinPoint 方法执行对象
     * @return 已经封装为ResponseDate的json字符串
     * @throws Throwable
     */
    @Around("execution(* com.linayi.controller..*.*(..))")
    public void setResponseDate(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取response
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        Object result = saveLog(joinPoint, request);

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(result);
    }


    /**请求体保存到数据库
     * @param joinPoint
     * @param request
     * @throws Throwable
     * @return
     */
    private Object saveLog(ProceedingJoinPoint joinPoint, HttpServletRequest request) throws Throwable {
        Map<String,Object> requestBody = new HashMap<>();
        Object[] args = joinPoint.getArgs();
        for( Object arg : args ) {
            if (arg instanceof Map) {
                requestBody = (Map<String, Object>) arg;
            }
        }
        ApiLogs apiLogs = new ApiLogs();
        apiLogs.setRequestUrl(request.getRequestURI());
        String accessToken = request.getHeader("accessToken");
        if(accessToken != null && !("null".equals(accessToken))){
            Account userByToken = userService.getUserIdByToken(accessToken);
            Integer userId = null;
            if(userByToken != null){
                userId = userByToken.getUserId();
            }
            Integer accountId = redisService.getAccountIdByToken(accessToken);
            if (userId != null || accountId != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("userId", userId);
                map.put("accountId", accountId);
                apiLogs.setRequestHead(JSON.toJSONString(map));
            }

        }
        Object result = joinPoint.proceed();

        apiLogs.setRequestTime(new Date());
        apiLogs.setRequestBody(JSONObject.toJSONString( requestBody ));
        if (result instanceof ResponseData) {
            ResponseData responseData = (ResponseData) result;
            apiLogs.setResponseBody((JSON.toJSONString(result)));
            if ("F".equals(responseData.getRespCode())) {
                apiLogs.setErrorMsg((String) responseData.getData());
            }
        }
        apiLogsService.insertLog(apiLogs);
        return result;
    }
}
