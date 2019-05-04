package com.linayi.exception;

/******************************************************
 * Copyrights @ 2016，Chanxa Technology Co., Ltd.
 *		linayi
 * All rights reserved.
 *
 * Filename：
 *		ErrorType.java
 * Description
 * 		接口相关编码定义类
 * Author:
 * Finished：
 *		2017年11月20日
 ********************************************************/

public enum ErrorType {
    SYSTEM_ERROR(1000, "系统繁忙!"),
    PARAM_ERROR(1901, "请求参数错误!"),
    WECHAT_CALL_ERROR(1902, "微信调用出错!"),
    THE_PHONE_NUMBER_HAS_BEEN_BOUND(1904, "该手机号已被绑定!"),
    THE_PHONE_NUMBER_HAS_BEEN_REGIST(1904, "该手机号已被注册!"),
    MESSAGE_FAILED_TO_SEND(1905, "短信发送失败!"),
    TOKEN_DISABLED(1906, "accessToken失效!"),
    ACCOUNT_ERROR(1908,"账号重复"),
    UPDATE_ERROR(1909,"未做修改"),
    UPDATE_STATUS(1910,"修改状态异常"),
    ADD_ERROR(1911,"添加失败!"),
    VERIFICATION_CODE_ERROR(1912,"验证码错误"),
    AUDIT_ERROR(1913,"审核失败，已有人审核!"),
    USERNAME_DOES_NOT_EXIST(1914,"用户名不存在!"),
    UNFILLED_SHIPPING_ADDRESS(1915,"未填写收货地址!"),
    REDIS_DATA_ERROR(1916,"redis数据出错!"),
    NOT_A_COMMUNITY_ACCOUNT(1917,"该账号不是社区账号!"),
    BARCODE_ERROR(1918,"请确保条码正确,该条码找不到对应的商品!"),
    SAME_AS_THE_ORIGINAL_PASSWORD(1919,"修改的密码和原密码相同!"),
    THIS_ACCOUNT_IS_NOT_ASSOCIATED_WITH_THE_COMMUNITY(1918,"该账号未关联社区!"),
    ACCOUNT_OR_PASSWORD_ERROR(2000,"密码错误"),
    ACCOUNT_AND_ROLEID(2005,"数据库已经存在了这条数据"),
    HAVE_MAN_SHARE_ERROR(2009,"已有人分享，暂时不能分享!"),
    HAVE_MAN_CORRECT_ERROR(2010,"已有人纠错，暂时不能纠错!"),
    HAVE_MAN_RECALL_ERROR(2011,"已是撤回状态，操作无效!"),
    INCOMPLETE_INFO(2012,"填写信息不完整!"),
    NOT_SHARER(2013,"您不是分享纠错员，请先申请认证成为分享纠错员!"),
    NOT_PROCURER(2014,"您不是采买员，没有采买任务！"),
    NOT_YOUR_CORRECT(2015,"不是您的分享/纠错申请，不能撤回!"),
    JUST_CORRECT_MINE(2016,"生效价格不是您分享的，不能申请纠错!"),
    OPERATION_FAIL(2017,"操作失败!"),
    BRAND_ERROR(2018,"品牌重复"),
    NO_PROCURE_GOODS(2019,"没有采买到任何商品，装箱失败!"),
    HAVE_OPEN_Promoter(2020,"此手机号已开通了会员!"),
    NOT_ORDER_MAN(2021,"你不是下单员!"),
    NOT_MODIFY(2008,"订单已取消，不能更改状态!")
	;


    // 错误编码
    private int errorCode;

    // 错误信息
    private String errorMsg;

    private ErrorType(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
