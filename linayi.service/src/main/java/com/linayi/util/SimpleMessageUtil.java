package com.linayi.util;


import com.linayi.enums.SMSTypeEnum;
import com.linayi.exception.YiXingException;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信发送util
 */
public class SimpleMessageUtil {


    private static MSGSender msgSender = MSGSender.getInstance( new MSGSenderConfigurationImpl() );

    public static void send(String mobile,String content){
        try{
            String newContent = "【邻阿姨科技有限公司】你的验证码是：{code}，请在2分钟内输入，请勿告诉其他人。";
            Map<String, String> paramters = new HashMap<>();
            paramters.put( "code", content );
            msgSender.sendDefalt( mobile, newContent, paramters  );
        }catch( YiXingException ex ){
            throw new RuntimeException( ex );
        }

    }
    public static void send(String mobile,String content, Map<String, String> paramters){
        try{
            msgSender.send( mobile, content, paramters, SMSTypeEnum.NOTIFICATION );
        }catch( YiXingException ex ){
            throw new RuntimeException( ex );
        }
    }

    public static enum Template{

        //验证码模板
        NORMAL("55847");

        public final String TEMPLATEID;
        Template(String templateId){
            TEMPLATEID = templateId;
        }
    }



}
