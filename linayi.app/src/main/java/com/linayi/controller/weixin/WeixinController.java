package com.linayi.controller.weixin;

import com.linayi.config.WeixinConfig;
import com.linayi.dao.account.AccountMapper;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.weixin.WeixinService;
import com.linayi.util.Configuration;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.PropertiesUtil;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
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
     * @param param
     * @return 获取用户授权页面
     */
    @RequestMapping("getAuthUrl.do")
    @ResponseBody
    public Object getAuthUrl(@RequestBody Map<String, Object> param)  {
        try {
            ParamValidUtil pa = new ParamValidUtil(param);
            pa.Exist();
            String appId = PropertiesUtil.getValueByKey(WeixinConfig.APPID);
            String url = WeixinConfig.GET_CODE_URL + "appid=" + appId + "&redirect_uri=" + URLEncoder.encode(Configuration.getConfig().getValue(WeixinConfig.REDIRECT_URI), "UTF-8") + "&response_type=code" + "&scope=" + WeixinConfig.SCOPE + "&state=STATE#wechat_redirect";
            return new ResponseData(url).toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }


    @RequestMapping("getCode.do")
    @Transactional
    public Object getCode(String code, HttpServletResponse response) {
        try {
            return weixinService.getCode(code,response,false);
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    @RequestMapping("getLinShengCode.do")
    @Transactional
    public Object getLinShengCode(String code, HttpServletResponse response) {
        try {
            return weixinService.getCode(code,response,true);
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }


}
