/**
 * 
 */
package com.linayi.util;

import com.google.gson.Gson;
import com.linayi.config.MSGConfig;
import com.linayi.config.URLs;
import com.linayi.enums.SMSTypeEnum;
import com.linayi.exception.YiXingException;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MSGSender {
	
	private static String appKey = null;
	
	private static MSGSender msgSender;
	
	private static Map<String,String> defaultHeaders = new HashMap<>();
	
	private MSGSender(){};
	private MSGSender( MSGConfig configuration ){
		msgSender = new MSGSender();
		appKey = configuration.getAppKey();
		defaultHeaders.put("ApiKey", appKey );
		defaultHeaders.put("Content-Type","application/json; charset=utf-8");
	}
	
	
	private static class MsgSederFactory{
		public static synchronized MSGSender getInstance(MSGConfig configuration ){
			if( msgSender == null ){
				msgSender = new MSGSender( configuration );
			}
			return msgSender;
		}
	}
	
	public static MSGSender getInstance(MSGConfig configuration){
		if( msgSender != null ){
			return msgSender;
		}
		return MsgSederFactory.getInstance( configuration );
	}
	
	/**
	 * 根据指定模板发送短信，模板中的参数（占位符）必须是{变量}
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @param smsType 消息类型
	 * @throws YiXingException 
	 */
	public MSGResult send(String mobile, String content, SMSTypeEnum smsType ) throws YiXingException {
		try{
			Gson gson = new Gson();
			Map<String,Object> params = buildParams( mobile, content, Collections.emptyMap(), smsType );
			String result = YiXingClient.postMSG( gson.toJson( params ) , URLs.SMSS_ERVICE, defaultHeaders );
			MSGResult msgResult = gson.fromJson( result, MSGResult.class );
			if( msgResult.getReturnCode() != 200 ){
				throw new YiXingException( msgResult.getReturnCode(),msgResult.getMessage() );
			}
			return msgResult;
		}catch( YiXingException ex ){
			throw ex;
		}catch( Exception ex ){
			throw new RuntimeException( ex );
		}
		
	}
	
	/***
	 * 根据默认的模板发送短信，模板中的参数（占位符）必须是{变量}
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @return
	 * @throws YiXingException 
	 */
	public MSGResult sendDefalt(String mobile, String content ) throws YiXingException{
		return send( mobile, content,SMSTypeEnum.VALIDA_CODE_MESSAGE );
	}
	
	/**
	 * 根据模板和指定占位符参数发送短信
	 * @param mobile 手机号
	 * @param content 发送内容（包含占位符）
	 * @param paramters 站位符参数
	 * @throws YiXingException 
	 */
	public MSGResult sendDefalt(String mobile, String content, Map<String,String> paramters ) throws YiXingException{
		try{
			return send( mobile, content, paramters, SMSTypeEnum.VALIDA_CODE_MESSAGE );
		}catch( YiXingException ex ){
			throw ex;
		}catch( Exception ex ){
			throw new RuntimeException( ex );
		}
	}
	
	/**
	 * 根据模板和指定占位符参数发送短信
	 * @param mobile 手机号
	 * @param content 发送内容（包含占位符）
	 * @param paramters 站位符参数
	 * @throws YiXingException 
	 */
	public MSGResult send(String mobile, String content, Map<String,String> paramters, SMSTypeEnum smsType ) throws YiXingException{
		try{
			Map<String,Object> params = buildParams( mobile, content, paramters ,smsType );
			Gson gson = new Gson();
			String result = YiXingClient.postMSG( gson.toJson( params ) ,URLs.SMSS_ERVICE,defaultHeaders);
			MSGResult msgResult = gson.fromJson( result, MSGResult.class );
			if( msgResult.getReturnCode() != 200 ){
				throw new YiXingException( msgResult.getReturnCode(),msgResult.getMessage() );
			}
			return msgResult;
		}catch( YiXingException ex ){
			throw ex;
		}catch( Exception ex ){
			throw new RuntimeException( ex );
		}
	}
	
	private static Map<String,Object> buildParams( String mobile,String content, Map<String,String> paramters , SMSTypeEnum smsType  ){
		Map<String,Object> params = buildDefaultPrams();
		for( Entry<String,String> entry : paramters.entrySet() ) {
			content = content.replaceAll( "\\{"+entry.getKey()+"\\}", entry.getValue() );
		}
		params.put( "Content", content );
		params.put( "Mobiles", Arrays.asList( mobile ) );
		params.put( "SMSType", smsType.getValue() );
		return params;
	}
	
	private static Map<String,Object> buildDefaultPrams( ){
		Map<String,Object> params = new HashMap<String,Object>();
		return params;
	}
}
