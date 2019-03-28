package com.linayi.config;

public class WeixinConfig {

    public static final String APPID = "weixin.appID";
    public static final String APPSECRET = "weixin.appsecret";
    //接口地址
    public static final String GET_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?";
    //回调地址
    public static final String REDIRECT_URI = "redirect_uri";
    //静默方式调用
    public static final String SCOPE = "snsapi_userinfo";
    //获取微信用户Token的接口地址
    public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?";
    //如果网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息了。
    public static final String GET_WEI_XIN_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?";
    public static final String AUTH_TOKEN_EXPIRE_URL = "https://api.weixin.qq.com/sns/auth?";
    //刷新微信token
    public static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?";
    //app项目首页url
    public static final String REDICT_INDEX_URL = "redict_index_url";
    //邻生商户端首页
    public static final String LINSHENG_REDICT_INDEX_URL = "linsheng_redict_index_url";


}
