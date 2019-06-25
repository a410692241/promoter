package com.linayi.controller.weixin;

import com.alibaba.fastjson.JSONObject;
import com.linayi.config.WeixinConfig;
import com.linayi.dao.account.AccountMapper;
import com.linayi.dto.WxSignatureDto;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.weixin.WeixinService;
import com.linayi.util.Configuration;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Controller
@RequestMapping("user/weixin")
public class WeixinController {
    @Autowired
    private WeixinService weixinService;
    @Autowired
    private AccountMapper accountMapper;


    /**
     * @return 获取用户授权页面
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("getAuthUrl.do")
    @ResponseBody
    public Object getAuthUrl(@RequestBody Map<String, Object> param)  {
        try {
            ParamValidUtil pa = new ParamValidUtil(param);
            String redirectUrl = param.get("redirectUrl") + "";
            String appId = Configuration.getConfig().getValue(WeixinConfig.APPID);
            String url = WeixinConfig.GET_CODE_URL + "appid=" + appId + "&redirect_uri=" + URLEncoder.encode(Configuration.getConfig().getValue(WeixinConfig.REDIRECT_URI), "UTF-8") + "&response_type=code" + "&scope=" + WeixinConfig.SCOPE + "&state="+redirectUrl+"#wechat_redirect";
            return new ResponseData(url).toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }


    @RequestMapping("getCode.do")
    @Transactional
    public Object getCode(String code,String redictUrl, HttpServletRequest request, HttpServletResponse response) {
        try {
            return weixinService.getCode(code,redictUrl,response,false);
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    @RequestMapping("getLinShengCode.do")
    @Transactional
    public Object getLinShengCode(String code, HttpServletRequest request,HttpServletResponse response) {
        try {
            return weixinService.getCode(code, code,response,true);
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    @ResponseBody
    @PostMapping(value="/getSignature.do")
    public ResponseData getSignature(@RequestBody JSONObject requestObject){
        try {
            WxSignatureDto wxSignatureDto = weixinService.getSignature(requestObject);
            return new ResponseData(wxSignatureDto);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(e);
        }
    }
}
