package com.linayi.util;

import java.util.Calendar;
import java.util.Random;

public class TokenCreate {
	/**
	 * 随机数
	 */
	private static final Random RANDOM = new Random();

	public static String generateToken( String generate ){
		String nextInt = RANDOM.nextInt( 9999999 )+"";
		while( 8 - nextInt.length() > 0 ){
			nextInt = "0" + nextInt;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add( Calendar.DAY_OF_MONTH, Integer.valueOf( Configuration.getConfig().getValue( "validDay" ) ));
		String token = generate + ";" +calendar.getTimeInMillis() +";" + nextInt;
		//判断token是否是16的倍数，如果不是则用 0 补齐
		while( token.length()%16 > 0 ){
			token = token + "0";
		}
		return token;
	}
}