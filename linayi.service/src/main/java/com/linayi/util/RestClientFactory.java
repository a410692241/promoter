package com.linayi.util;

import com.linayi.config.SystemPropert;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * Java客户端生成工厂
 * elasticsearch util client
 */
public class RestClientFactory{

    private static final String HOST = Configuration.getConfig().getValue(SystemPropert.ES_HOST);
    private static final int PORT = 9200;
    private static final String SCHEMA = "http";
    private static final int CONNECT_TIME_OUT = 1000;
    private static final int SOCKET_TIME_OUT = 30000;
    private static final int CONNECTION_REQUEST_TIME_OUT = 500;

    private static final int MAX_CONNECT_NUM = 100;
    private static final int MAX_CONNECT_PER_ROUTE = 100;

    private static HttpHost HTTP_HOST = new HttpHost(HOST,PORT,SCHEMA);
    private static boolean uniqueConnectTimeConfig = false;
    private static boolean uniqueConnectNumConfig = false;
    private static RestClientBuilder builder;
    private static RestClient restClient;
    private static RestHighLevelClient restHighLevelClient;

    static {
        init();
    }

    public static void init(){
        // 可以初始化多个HttpHost
        builder = RestClient.builder(HTTP_HOST);
        if(uniqueConnectTimeConfig){
            setConnectTimeOutConfig();
            uniqueConnectTimeConfig = true;
        }
        if(uniqueConnectNumConfig){
            setMultiConnectConfig();
            uniqueConnectNumConfig = true;
        }
        restClient = builder.build();
        restHighLevelClient = new RestHighLevelClient(builder);
    }

    // 主要关于异步httpclient的连接延时配置
    public static void setConnectTimeOutConfig(){
        // requestConfigBuilder
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(CONNECT_TIME_OUT);
            requestConfigBuilder.setSocketTimeout(SOCKET_TIME_OUT);
            requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT);
            return requestConfigBuilder;
        });
    }

    /**
     *    主要关于异步httpclient的连接数配置
     */
    public static void setMultiConnectConfig(){
       // setHttpClientConfigCallback
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(MAX_CONNECT_NUM);
            httpClientBuilder.setMaxConnPerRoute(MAX_CONNECT_PER_ROUTE);
            return httpClientBuilder;
        });
    }


    public static RestClient getClient(){
        return restClient;
    }

    public static RestHighLevelClient getHighLevelClient(){
        if(restHighLevelClient == null){
            init();
        }
        return restHighLevelClient;
    }

    public static void close() {
        if (restHighLevelClient != null) {
            try {
                restHighLevelClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                uniqueConnectNumConfig = false;
                uniqueConnectTimeConfig = false;
            }
        }
    }
}