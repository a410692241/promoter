package com.linayi.util;

import java.lang.reflect.Field;
import java.util.Map;

public class AuthUtils {
    public static Object getAuth(String clazzName,Map<String,Object>map) {
        Class a = null;
        Object b = null;
        try {
            a = Class.forName(clazzName);
            b = a.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Field[] declaredFields = a.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String name = field.getName();
            for (Map.Entry<String, Object> ma : map.entrySet()) {
                String key = ma.getKey();
                if(name.equals(key)){
                    try {
                        field.set(b, map.get(name));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        }
        return b;
    }
}
