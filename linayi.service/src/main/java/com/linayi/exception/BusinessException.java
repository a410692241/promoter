 /******************************************************
 * Copyrights @ 2016，Chanxa Technology Co., Ltd.
 *		linayi
 * All rights reserved.
 * 
 * Filename：
 *		BusinessException.java
 * Description：
 * 		业务异常类
 * Author:
 *		chanxa
 * Finished：
 *		2017年11月20日
 ********************************************************/
 package com.linayi.exception;

public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = -1785706333918687090L;
	
	private ErrorType errorType;
	public BusinessException(ErrorType errorType){
		super(errorType.getErrorMsg());
		this.errorType = errorType;
	}
	
	public ErrorType getErrorType() {
		return errorType;
	}
}
