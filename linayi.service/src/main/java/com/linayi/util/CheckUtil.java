 package com.linayi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil{

	/**
	 * 是否是空字符串
	 * 
	 * @param value
	 *           
	 * @return TRUE
	 */
	public static boolean isNullEmpty( Object value ) {
		if( null == value ) {
			return true;
		}
		if( value instanceof Collection ){
			return ( ( Collection<?> )value ).isEmpty();
		}
		if( value instanceof Map ){
			return ( ( Map<?,?> )value ).isEmpty();
		}
		if( value instanceof Object[] ){
			return (( Object[] )value).length == 0;
		}
		return value.toString().trim().length() == 0;
	}

	/**
	 * 是否不是空字符串
	 * 
	 * @param value
	 *            
	 * @return TRUE
	 */
	public static boolean isNotNullEmpty( Object value ) {
		return !isNullEmpty( value );
	}
	
	/**
	 * 是否是map类型
	 * @param value
	 * @return
	 */
	public static boolean isMap(Object value){
		if(value instanceof Map){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 是否是List类型
	 * @param value
	 * @return
	 */
	public static boolean isList(Object value){
		if(value instanceof List){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 获取字符串长度，如果为null 返回-1
	 * 
	 * @param value
	 *           
	 * @return 
	 */
	public static int getValLentgh( String value ) {
		if( null == value ) {
			return -1;
		}
		if( value.trim().length() == 0 ) {
			return 0;
		}
		return value.getBytes().length;
	}

	/**
	 * 是否是数字
	 * 
	 * @param value
	 *            
	 * @return TRUE 
	 */
	public static boolean isNumber( Object value ) {
		if( value == null ) {
			return false;
		}
		return isNumber( value.toString() );
	}

	/**
	 * 是否是数字
	 * 
	 * @param value
	 *           
	 * @return 
	 */
	public static boolean isNumber( String value ) {
		String regEX = "^(-?\\d+)(\\.\\d*)*$";
		if( isNullEmpty( value ) ) {
			return false;
		}
		Pattern pattern = Pattern.compile( regEX );
		Matcher match = pattern.matcher( value );
		return match.find();
	}

	/**
	 *
	 * 
	 * @param value
	 *           
	 * @return TRUE 
	 */
	public static boolean isPayment( String value ) {
		String regEX = "^(-?\\d+)(\\.\\d*)*$";
		if( isNullEmpty( value ) ) {
			return false;
		}
		value = value.replaceAll( ",", "" );
		Pattern pattern = Pattern.compile( regEX );
		Matcher match = pattern.matcher( value );
		return match.find();
	}

	/**
	 * 
	 * 是否是有效的日期
	 * @param value
	 *           
	 * @return TRUE 
	 */
	public static boolean isvalidateDate( String value ) {
		if( null == value ) {
			return false;
		}
		String regEX = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-";
		regEX = regEX + "(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-";
		regEX = regEX + "(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-";
		regEX = regEX + "(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";
		Pattern pattern = Pattern.compile( regEX );
		Matcher match = pattern.matcher( value );
		return match.find();
	}

	/**
	 * 
	 * 
	 * @param value
	 *           
	 * @param format
	 *           
	 * @return 
	 */
	public static boolean isValidateDateTime( String value, String format ) {
		try {
			if( value.trim().length() != format.trim().length() ) {
				return false;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat( format );
			dateFormat.setLenient( false );
			dateFormat.parse( value );
		} catch( ParseException e ) {
			return false;
		} catch( IllegalArgumentException e ) {
			return false;
		} catch( Exception e ) {
			return false;
		}
		return true;
	}

	/**
	 * 检车小数点后的位数
	 * 
	 * @param d
	 *            小数
	 * @param length
	 *            小数点后面最多允许出现的位数
	 * @return 符合则true 反之false
	 */
	public static boolean checkDecimalPlaces( double d, int length ) {
		String s = String.valueOf( d );
		boolean b = true;
		if( s.indexOf( "." ) != -1 ) {
			String postfix = s.substring( s.indexOf( "." ) + 1 );
			b = postfix.length() <= length;
		}
		return b;
	}
	
}
