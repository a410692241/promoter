package com.linayi.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties properties = new Properties();

    static {
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("config/system.properties");
        // 使用properties对象加载输入流
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取key对应的value值
     * @param key
     * @return
     */
    public static String getValueByKey(String key){
        return properties.getProperty(key);
    }


}
