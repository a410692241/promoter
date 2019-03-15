package com.linayi.config;


public class RedisConstant {
	
	/**redis保存的key*/
	public static class Keys {

        private static final String SYSTEM = "whereBuy";

        /**验证码存储前缀 + 手机号*/
		public static final String VALIDATE_CODE = SYSTEM + ":validate_code:";

        /**访问令牌存储前缀 + 用户id*/
		public static final String ACCESS_TOKEN = SYSTEM + ":access_token:";

        /**访问令牌存储前缀 + 用户id*/
        public static final String ACCESS_TOKEN_ADMIN =  SYSTEM + ":access_token_admin:";

		public static String getKey( String key,String... appendKey ){
			StringBuilder builder = new StringBuilder( key );
			if( appendKey == null || appendKey.length == 0 ){
				throw new RuntimeException( "appendKey is empty!" );
			}else{
				int length = appendKey.length ;
				for( int i = 0 ; i< length ; i ++ ) {
					if( i == length - 1 ){
						builder.append( appendKey[i] );
					}else{
						builder.append( appendKey[i]+":" );
					}
				}
			}
			return builder.toString();
		}
    }

}
