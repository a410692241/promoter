package com.linayi.controller.account;

import com.linayi.controller.BaseController;
import com.linayi.entity.account.Account;
import com.linayi.entity.promoter.PromoterOrderMan;
import com.linayi.enums.ClientType;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.account.AccountService;
import com.linayi.service.redis.GetRedisUserService;
import com.linayi.service.redis.RedisService;
import com.linayi.service.user.UserService;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;
import com.linayi.util.SimpleMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/account/account")
public class AccountController extends BaseController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private GetRedisUserService getRedisUserService;
    @Autowired
    private UserService userService;

    @RequestMapping("/userLogin.do")
    @ResponseBody
    public Object userLogin(@RequestParam("account1") String account) {
        String clientType = null;
        if (clientType.equals(ClientType.WEIXIN.toString())) {

        }

        if (clientType.equals(ClientType.NORMAL)) {

        }
        return "";
    }

    @RequestMapping("/communityLogin.do")
    @ResponseBody
    public Object communityLogin(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<Account> pu = new ParamValidUtil<>(param);
            pu.Exist("mobile", "password");
            Account account = pu.transObj(Account.class);
            return new ResponseData(accountService.communityLogin(account)).toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }


    /**
     * 获取短信验证码
     *
     * @param param
     * @return
     */
    @RequestMapping("/getValidCode.do")
    @ResponseBody
    public Object getValidCode(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil pa = new ParamValidUtil(param);
            pa.Exist("mobile");
            //生成短信验证码
            String mobile = param.get("mobile") + "";
            String code = redisService.generationValidCode(mobile);
            //发送验证码
            SimpleMessageUtil.send(mobile, code);
            return new ResponseData("发送成功").toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.MESSAGE_FAILED_TO_SEND);
        }

    }

    /**
     * 注册
     *
     * @param param
     * @return
     */
    @RequestMapping("/regist.do")
    @ResponseBody
    @Transactional
    public Object regist(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<Map> pa = new ParamValidUtil<>(param);
            pa.Exist("mobile", "code", "password");
            String mobile = param.get("mobile") + "";
            String code = param.get("code") + "";
            String password = param.get("password") + "";
            return new ResponseData(accountService.regist(mobile, code, password));
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }

    }

    /**重置密码
     * @param param
     * @return
     */
    @RequestMapping("/resetPsw.do")
    @ResponseBody
    @Transactional
    public Object resetPsw(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<Map> pa = new ParamValidUtil<>(param);
            pa.Exist("mobile", "code", "password");
            String mobile = param.get("mobile") + "";
            String code = param.get("code") + "";
            String password = param.get("password") + "";
            return new ResponseData(accountService.resetPsw(mobile, code, password));
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }

    }




    /**
     * 检测手机号是否存在
     *
     * @param param
     * @return
     */
    @RequestMapping("/existMobile.do")
    @ResponseBody
    @Transactional
    public Object existMobile(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<Map> pa = new ParamValidUtil<>(param);
            pa.Exist("mobile");
            String mobile = param.get("mobile") + "";
            return new ResponseData(accountService.existMobile(mobile));
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }

    }


    /**
     * 检测账号是否绑定手机号
     *
     * @param param
     * @return
     */
    @RequestMapping("/whetherAccountBindMobile.do")
    @ResponseBody
    @Transactional
    public Object whetherAccountBindMobile(@RequestBody Map<String, Object> param) {
        try {
            Integer accountId = getAccountId();
            return new ResponseData(accountService.whetherAccountBindMobile(accountId));
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }

    }

    /**
     * 微信账号绑定手机号
     *
     * @param param
     * @return
     */
    @RequestMapping("/bindMobile.do")
    @ResponseBody
    public Object bindMobile(@RequestBody Map<String, Object> param) {
        try {
            Integer accountId = getAccountId();
            ParamValidUtil<Map> pa = new ParamValidUtil<>(param);
            pa.Exist("mobile","validCode");
            String mobile = param.get("mobile") + "";
            String validCode = param.get("validCode") + "";
            return new ResponseData(accountService.bindMobile(accountId,mobile,validCode));
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }

    }

    /**
     * 验证登录接口
     *
     * @param param
     * @return
     */
    @RequestMapping("/login.do")
    public Object login(@RequestBody Map<String, Object> param, HttpServletResponse response) {
        try {
            ParamValidUtil<Account> pa = new ParamValidUtil<>(param);
            pa.Exist("mobile", "password");
            Account account = pa.transObj(Account.class);
            return new ResponseData(accountService.login(account));
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }

    }

    /***
     * 退出登录
     * @param param
     * @return
     */
    @RequestMapping("/logout.do")
    @ResponseBody
    public Object logOut(@RequestBody Map<String, Object> param) {
        try {
            String accessToken = getAccessToken();
            int accountIdByToken = getRedisUserService.getAccountIdByToken(accessToken);
            redisService.deleteAccessToken((long) accountIdByToken);
            redisService.deleteAnotherAccessToken(accessToken);
            return new ResponseData("success");
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }

}
