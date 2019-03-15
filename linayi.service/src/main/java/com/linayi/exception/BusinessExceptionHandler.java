package com.linayi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BusinessExceptionHandler implements HandlerExceptionResolver {
    private static Logger log = LoggerFactory.getLogger(BusinessExceptionHandler.class);


    /**全局异常处理拦截器类,自动捕获bisinessException的内容,返回对于格式的json数据
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();
        /*  使用response返回    */
        response.setStatus(HttpStatus.OK.value()); //设置状态码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
        response.setCharacterEncoding("UTF-8"); //避免乱码
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        StringBuilder result = new StringBuilder();

        result.append("{");
        result.append("\"respCode\":");
        result.append("\"F\",");
        result.append("\"errorCode\":");
        if (ex instanceof BusinessException) {
            BusinessException bu = (BusinessException) ex;
            result.append("" + bu.getErrorType().getErrorCode() + ",");
        }else{
            result.append("" + 1000 + ",");
        }
        result.append("\"errorMsg\":");
        result.append("\"" + ex.getMessage() + "\"}");
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            log.error("与客户端通讯异常:" + e.getMessage(), e);
        }

        log.debug("异常:" + ex.getMessage(), ex);
        return mv;
    }
}  