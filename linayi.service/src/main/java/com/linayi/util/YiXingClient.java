/**
 *
 */
package com.linayi.util;

import com.squareup.okhttp.*;
import com.squareup.okhttp.Request.Builder;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

public class YiXingClient {

    private static Logger logger = Logger.getLogger( YiXingClient.class.toString() );

    /**
     * get方式发送请求
     * @param paramters
     * @param url
     * @return
     * @throws IOException
     */
    public static String getMSG( Map<String,String> paramters,String url ) throws IOException{
        OkHttpClient httpClient = new OkHttpClient();

        Request.Builder requestBuilder = new Request.Builder().url( url +"?" +queryString( paramters ) ).get();
        Response response = httpClient.newCall(requestBuilder.build()).execute();;
        return new String(response.body().bytes(),"utf-8");
    }

    private static String queryString( Map<String,String> params ){
        encode( params );
        StringBuilder builder = new StringBuilder();
        for(  Entry<String, String> entry : params.entrySet() ) {
            builder.append( entry.getKey() + "=" + entry.getValue() +"&" );
        }
        return builder.substring( 0, builder.length() );
    }

    private static void encode( Map<String,String> params ){
        try{
            Set<Entry<String, String>> entrys = params.entrySet();
            for( Entry<String, String> entry : entrys ) {
                params.put( URLEncoder.encode( entry.getKey(),"utf-8" ), URLEncoder.encode( entry.getValue(),"utf-8" ) );
            }
        }catch( Exception ex ){
            throw new RuntimeException( ex );
        }

    }


    /**
     * post方式发送请求
     * @param paramters
     * @param headers
     * @return
     * @throws IOException
     */
    public static String postMSG( Map<String,String> paramters,String url, Map<String,String> headers ) throws IOException{

        OkHttpClient httpClient = new OkHttpClient();
        FormEncodingBuilder params = new FormEncodingBuilder();
        for( Entry<String, String> entry : paramters.entrySet() ) {
            params.add( entry.getKey(), entry.getValue() );
        }
        Request.Builder requestBuilder = new Request.Builder().url( url ).post( params.build() );
        for(  Entry<String, String> entry : headers.entrySet() ) {
            requestBuilder.addHeader( entry.getKey(), entry.getValue() );
        }
        return excute( httpClient, requestBuilder.build() );
    }

    public static String postMSG( String body,String url, Map<String,String> headers ) throws UnsupportedEncodingException, IOException{
        OkHttpClient httpClient = new OkHttpClient();
        String contentType = "";
        for(  Entry<String, String> entry : headers.entrySet() ) {
            if( entry.getKey().equalsIgnoreCase( "content-type" ) ){
                contentType = entry.getKey();
            }
        }
        RequestBody requestBody = RequestBody.create( MediaType.parse( headers.getOrDefault( contentType, "application/json;charset=utf-8" )  ), body );
        Builder builder = new Request.Builder();
        for(  Entry<String, String> entry : headers.entrySet() ) {
            builder.addHeader( entry.getKey(), entry.getValue() );
        }
        return excute( httpClient, builder.url( url ).post( requestBody ).build() );
    }

    private static String excute( OkHttpClient httpClient,Request request  ) throws IOException{
        Response response = httpClient.newCall( request  ).execute();
        String result = new String(response.body().bytes(),"utf-8");
        logger.info( "短信发送headers->"+ request.headers() );
        return result;
    }
}
