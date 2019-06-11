package com.linayi.service.weixin;

import com.alibaba.fastjson.JSONObject;
import com.linayi.dto.WxSignatureDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WeixinService {

    /**
     * @param code
     * @return 通过授权验证Url获取个人信息
     */


    Object getCode(String code, HttpServletRequest request, HttpServletResponse response, boolean linsheng);

    WxSignatureDto getSignature(JSONObject requestObject);
}
