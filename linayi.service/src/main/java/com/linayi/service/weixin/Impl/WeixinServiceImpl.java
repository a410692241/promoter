package com.linayi.service.weixin.Impl;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.java.emoji.EmojiConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.linayi.config.WeixinConfig;
import com.linayi.dto.WxSignatureDto;
import com.linayi.entity.account.Account;
import com.linayi.entity.user.Auth;
import com.linayi.entity.user.User;
import com.linayi.enums.Sex;
import com.linayi.enums.UserType;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.account.AccountService;
import com.linayi.service.redis.RedisService;
import com.linayi.service.user.UserService;
import com.linayi.service.weixin.WeixinService;
import com.linayi.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.Map;
import java.util.UUID;

@Service
public class WeixinServiceImpl implements WeixinService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;
    private static EmojiConverter emojiConverter = EmojiConverter.getInstance();
    private static final String NONCESTR = "linayi";
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object getCode( String code,String redictUrl, HttpServletResponse response, boolean linsheng) {
        //获取access_token
        String getTokenUrl = WeixinConfig.GET_TOKEN_URL + "appid=" + Configuration.getConfig().getValue(WeixinConfig.APPID) + "&secret=" + Configuration.getConfig().getValue(WeixinConfig.APPSECRET) + "&code=" + code + "&grant_type=authorization_code";
        logger.info(redictUrl);
        String responseStr = HttpClientUtil.sendGetRequest(getTokenUrl, "utf-8");
        TypeToken<Map> type = new TypeToken<Map>() {
        };
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(responseStr, type.getType());
        Auth auth = (Auth) AuthUtils.getAuth("com.linayi.entity.user.Auth", map);

        //有需要则刷新access_token
        String access_token = auth.getAccess_token();
        String openid = auth.getOpenid();
        String refresh_token = auth.getRefresh_token();
        access_token = reFreshToken(access_token, openid, refresh_token);

        //获取用户的个人资料
        String getWeixinInfoUrl = WeixinConfig.GET_WEI_XIN_USER_INFO + "access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
        String weixinInfoUrlResponseStr = HttpClientUtil.sendGetRequest(getWeixinInfoUrl, null);
        Map<String, Object> userInfoMap = gson.fromJson(weixinInfoUrlResponseStr, type.getType());
        //如果返回正常
        if (!userInfoMap.containsKey("errcode")) {
            Account account = new Account();
            account.setOpenId(openid);
            Account accountDB = accountService.selectAccountList(account).stream().findFirst().orElse(null);
            //openid已存在，用户已存在，生成新的access_token
            Integer accountId = null;
            Integer userId;
            if (accountDB == null) {
                //新用户
                User user = new User();
                String nickname = userInfoMap.get("nickname") + "";
                nickname = emojiConverter.toAlias(nickname);
                user.setNickname(nickname);
                user.setSex((Double) userInfoMap.get("sex") > 1.0 ? Sex.FEMALE.name() : Sex.MALE.name());
                //下载微信头像图片
                String headimgurl = userInfoMap.get("headimgurl") + "";
                System.out.println("headimgurl" + headimgurl);
                String path = uploadImg(headimgurl);
                System.out.println("path" + path);
                //保存图片到数据库
                user.setHeadImage(path);
                user.setCreateTime(new Date());
                user.setUpdateTime(new Date());
                userService.insertUser(user);
                Account accountPM = new Account();
                accountPM.setOpenId(openid);
                accountPM.setCreateTime(new Date());
                accountPM.setUpdateTime(new Date());
                userId = user.getUserId();
                accountPM.setUserId(userId);
                accountPM.setUserType(UserType.USER.name());
                accountPM.setUserName(nickname);
                accountService.insertAccount(accountPM);
                accountId = accountPM.getAccountId();
            } else {
                accountId = accountDB.getAccountId();
                userId = accountService.getUserId(accountId);
            }
            //生成系统Token
            String sysetemAccessToken = redisService.GenerationToken(accountId);
            //用openId进入数据库进行查找
            try {
                //如果是邻生客户端,需要跳转的是邻生的商户端首页
                if (!linsheng) {
                    if (!accountService.isBindMobile(accountId)) {
                        response.sendRedirect(Configuration.getConfig().getValue(WeixinConfig.BIND_MOBILE_URL) + "&accessToken=" + sysetemAccessToken);
                    } else {
                        if (redictUrl.contains("?")) {
                            response.sendRedirect(redictUrl + "&accessToken=" + sysetemAccessToken+ "&accountId=" + accountId+"&userId="+userId+"&loginType="+1);
                        }else{
                            response.sendRedirect(redictUrl + "?accessToken=" + sysetemAccessToken+ "&accountId=" + accountId+"&userId="+userId+"&loginType="+1);
                        }
                    }
                    return new ResponseData("登录成功");
                }
                //如果没有绑定手机号,那么跳转到绑定手机号的页面

                response.sendRedirect(Configuration.getConfig().getValue(WeixinConfig.LINSHENG_REDICT_INDEX_URL) + "?accessToken=" + sysetemAccessToken);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return new ResponseData(ErrorType.WECHAT_CALL_ERROR);
    }


    private String uploadImg(String headimgurl) {
        String datePath = DateUtil.date2String(new Date(), "yyyy/MM/dd/HH");
        String fileName = UUID.randomUUID() +".jpg";
        String path = datePath + fileName;
        //上传图片到图片服务器
        downloadPicture(headimgurl, PropertiesUtil.getValueByKey(ConstantUtil.IMAGE_PATH) + "/" + path);
        return path;
    }


    /**
     * @param accessToken
     * @param openId
     * @param refresh_token
     * @return过期则刷新最新的微信access_token,否则返回原有效的access_token
     */
    public String reFreshToken(String accessToken, String openId, String refresh_token) {
        String authTokenExpireUrl = WeixinConfig.AUTH_TOKEN_EXPIRE_URL + "access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
        String resStr = HttpClientUtil.sendGetRequest(authTokenExpireUrl, null);
        TypeToken<Map> type = new TypeToken<Map>() {};
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(resStr, type.getType());
        if (resStr != null && "0".equals(map.get("errcode"))) {
            //获取微信用户的token过期
            if (!"ok".equals(map.get("errmsg"))) {
                String refreshTokenUrl = WeixinConfig.REFRESH_TOKEN_URL + "appid=" + Configuration.getConfig().getValue(WeixinConfig.APPID) + "&grant_type=refresh_token&refresh_token=" + refresh_token+"&loginType="+1;
                String refreshResStr = HttpClientUtil.sendGetRequest(refreshTokenUrl, null);
                Map<String, String> refreshResStrMap = gson.fromJson(refreshResStr, type.getType());
                if (refreshResStrMap.containsKey("errcode")) {
                    throw new BusinessException(ErrorType.WECHAT_CALL_ERROR);
                }
                return refreshResStrMap.get("access_token");
            }

        }
        return accessToken;
    }

    //链接url下载图片
    private static void downloadPicture(String urlList,String path) {
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            File file = new File(path);
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public WxSignatureDto getSignature(JSONObject requestObject) {

        String timestamp = (System.currentTimeMillis() / 1000) + "";
        //获取access_token
        String appId = Configuration.getConfig().getValue(WeixinConfig.APPID);
        String authTokenExpireUrl = WeixinConfig.GET_ACCESS_TOKEN_URL + "&appid=" + appId + "&secret="
                + Configuration.getConfig().getValue(WeixinConfig.APPSECRET);
        String resStr = HttpClientUtil.sendGetRequest(authTokenExpireUrl, null);
        TypeToken<Map> type = new TypeToken<Map>() {
        };
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(resStr, type.getType());
        String access_token = map.get("access_token") + "";

        String s2 = WeixinConfig.GET_JSAPI_TICKET_URL + access_token;
        String jsApiResStr = HttpClientUtil.sendGetRequest(s2, null);
        Map<String, String> mapTicket = gson.fromJson(jsApiResStr, type.getType());
        String ticket = mapTicket.get("ticket") + "";
        String signtature = getSignature(timestamp, NONCESTR, requestObject.getString("htmlUrl"), ticket);
        WxSignatureDto wxSignatureDto = new WxSignatureDto();
        wxSignatureDto.setSignature(signtature);
        wxSignatureDto.setAppid(appId);
        wxSignatureDto.setNoncestr(NONCESTR);
        wxSignatureDto.setTimestamp(timestamp);
        return wxSignatureDto;
    }


    public static String getSignature(String timestamp, String nonceStr, String url, String jsapiTicket) {
        MessageDigest md = null;
        //注意这里参数名必须全部小写，且必须有序
        String string1 = "jsapi_ticket=" + jsapiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        String signature = "";
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(string1.getBytes());
            signature = byteToHex(md.digest());
            return signature;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return signature;
    }


    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
