package com.linayi.util;

import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

public class ParamValidUtil<T> {
    private Map<String, Object> param = null;
    private Object object = null;

    public ParamValidUtil(Map<String, Object> param) {
        this.param = param;
    }
    public ParamValidUtil(Object object) {
        this.object = object;
    }


    /**
     * 将Map转化为实体类
     * @param clazz 需要转化的对象类型
     * @return
     * */
    public T transObj(Class<T> clazz) {
        Map<String, Object> map = this.param;
        Class a = null;
        Object b = null;
        try {
            a = Class.forName(clazz.getName());
            b = a.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(;a != Object.class; a = a.getSuperclass()){
            Field[] declaredFields = a.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                String name = field.getName();
                for (Map.Entry<String, Object> ma : map.entrySet()) {
                    String key = ma.getKey();
                    if (name.equals(key.trim())) {
                        try {
                            packEntity(b, field, map.get(name));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }

        return (T) b;
    }
    /**
     * 将对象转化为实体类
     * @param clazz 需要转化的对象类型
     * @return
     * */
    public T transObject(Class<T> clazz) {
        Class<?> aClass = object.getClass();
        Field[] declaredFields1 = aClass.getFields();
        Class a = clazz;
        Object b = null;
        try {
            b = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(;a != Object.class; a = a.getSuperclass()){
            Field[] declaredFields = a.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                String name = field.getName();
                for (Field field1 : declaredFields1) {
                    field1.setAccessible(true);
                    String attrName = field1.getName();
                    if (name.equals(attrName)) {
                        try {
                            Object o = field1.get(object);
                            if (o != null && !"".equals(o + "")){
                                packEntity(b, field, field1.get(object));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }

        return (T) b;
    }

    private void packEntity(Object b, Field field, Object o2) throws IllegalAccessException {
        String type = field.getType().toString();//得到此属性的类型
        if (type.endsWith("String")) {
            field.set(b, o2 + "");        //给String属性设值
        } else if (type.endsWith("int") || type.endsWith("Integer")) {
            field.set(b, Integer.parseInt(o2 + ""));       //给int或Integer属性设值
        } else if (type.endsWith("Short")) {
            field.set(b, Short.parseShort(o2 + ""));       //给int或Integer属性设值
        } else if (type.endsWith("Long") || type.endsWith("long")) {
            field.set(b, Long.parseLong(o2 + ""));       //给long或Long属性设值
        } else if (type.endsWith("Date")) {
            field.set(b, new Date(Long.parseLong(o2 + "")));       //给Date属性设值
        } else {
            field.set(b, o2);
        }
    }

    /**
     * 验证http参数列表是否包含指定必传参数
     *
     * @param validParams
     * @return
     */
    public void Exist(String... validParams) {
        for (String validParam : validParams) {
            if (!param.containsKey(validParam)) {
                throw new BusinessException(ErrorType.PARAM_ERROR);
            }
        }
    }

    /**
     * 验证对象是否包含指定必传参数
     *
     * @param validParams
     * @return
     */
    public void ExistObj(String... validParams) {
        Class<?> aClass = object.getClass();
        try {
            for (String validParam : validParams) {
                Field validParam1 = aClass.getDeclaredField(validParam);
                if (validParam1.get(object) == null) {
                    throw new BusinessException(ErrorType.PARAM_ERROR);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 验证http参数列表是否无参数
     *
     * @return
     */
    public void Exist() {

    }

}
