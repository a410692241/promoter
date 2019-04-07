package com.linayi.tags;

import com.google.gson.Gson;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

public class NameByCode extends SimpleTagSupport {
    private String enumName;

    private String code;

    private String name;

    @Override
    public void doTag() {
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();
        Gson gson = new Gson();

        try {

            Class<?> clazz = Class.forName(enumName);
            Method getKey = clazz.getMethod(name);
            Method name = clazz.getMethod("name");
            //得到enum的所有实例
            Object[] objs = clazz.getEnumConstants();
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            for (Object obj : objs) {
                if (code.equalsIgnoreCase((String) name.invoke(obj))) {
                    String EnumKey = (String) getKey.invoke(obj);
                    map.put("code",code);
                    map.put("name",EnumKey);
                }

            }
            out.println(gson.toJson(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}



