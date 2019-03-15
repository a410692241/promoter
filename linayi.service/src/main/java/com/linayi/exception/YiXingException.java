/**
 * 
 */
package com.linayi.exception;

public class YiXingException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 错误编码
	 * */
	private int errorCode;
	
	public YiXingException(int errorCode, String msg ) {
		super( msg );
		this.errorCode = errorCode;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
}
