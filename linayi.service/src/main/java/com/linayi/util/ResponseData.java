package com.linayi.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.linayi.exception.ErrorType;

import java.util.HashMap;
import java.util.Map;

public class ResponseData {
	private String respCode;	// S：成功     F：失败
	
	/**
	 * 只有respCode=F时，errorType才会有值
	 */
	private String errorType;

	/**
	 * 只有respCode=S时，data才会有值
	 */
	private Object data;


	public ResponseData() {

	}

	/**
	 * 成功的方法
	 * this.respCode = "S";this.errorType = "无";
	 * @param data
	 */
	public ResponseData(Object data) {
		this.respCode = "S";
		this.data = data;
	}

	/**
	 * 失败的方法
	 * respCode F 失败 S 成功
	 * this.data = "defeat";
	 * @param respCode
	 * @param errorType
	 */
	public ResponseData(String respCode,String errorType) {
		this.respCode = respCode;
		this.data = "defeat";
		this.errorType = errorType;
	}

	public ResponseData(ErrorType errorType) {
		this.respCode = "F";
		this.errorType = errorType.getErrorCode()+"";
		this.data = errorType.getErrorMsg();
	}

	public ResponseData(String respCode, String errorType, Object data) {
		this.respCode = respCode;
		this.errorType = errorType;
		this.data = data;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		Map<Object, Object> responseData = new HashMap<>();
		responseData.put("respCode", respCode);

		if(respCode.equals("S")){
			responseData.put("data", data);
		}else{
			responseData.put("errorCode", errorType);
			responseData.put("errorMsg", data);
		}

		return JSON.toJSONString(responseData, SerializerFeature.DisableCircularReferenceDetect);
	}


}
