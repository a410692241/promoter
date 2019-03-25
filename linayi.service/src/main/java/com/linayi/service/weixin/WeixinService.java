package com.linayi.service.weixin;

import javax.servlet.http.HttpServletResponse;

public interface WeixinService {

    /**
     * @param code
     * @return 通过授权验证Url获取个人信息
     */
    public Object getCode(String code, HttpServletResponse response,boolean linsheng);


}
