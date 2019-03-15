package com.linayi.enums;


/**
 * <p>Project:  <p>
 * <p>Module:			<p>
 * <p>Description:		<p>
 *
 * @author jobd huang
 * @date 2018年3月10日 下午5:24:41
 */
public enum SMSTypeEnum {
	
	NOTIFICATION( 0 ),VALIDA_CODE_MESSAGE( 4 ),SALES_MESSAGE( 5 );
	;
	/**
	 * 短信类型， 
	 * 0：通知短信，
	 * 4：验证码短信，
	 * 5：营销短信
	 */
	private Integer value;
	
	SMSTypeEnum(int value ){
		this.value = value;
	}

	
	public Integer getValue() {
		return value;
	}
	
}
