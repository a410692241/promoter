/**
 * 
 */
package com.linayi.util;

public class MSGResult {
	
	/**发送提示*/
	private String Message;
	
	/**错误编码，0表示成功*/
	private Integer ReturnCode;
	
	/**发送短信账单信息*/
	private String Data;

	
	public String getMessage() {
		return Message;
	}

	
	public void setMessage( String message ) {
		Message = message;
	}

	
	public Integer getReturnCode() {
		return ReturnCode;
	}

	
	public void setReturnCode( Integer returnCode ) {
		ReturnCode = returnCode;
	}

	
	public String getData() {
		return Data;
	}

	
	public void setData( String data ) {
		Data = data;
	}


	@Override
	public String toString() {
		return "MSGResult [Message=" + Message + ", ReturnCode=" + ReturnCode + ", Data=" + Data + "]";
	}

	
}
