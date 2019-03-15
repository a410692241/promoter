package com.linayi.tags;

import com.google.gson.Gson;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class enumTag extends SimpleTagSupport {

    private String enumName;

    private String nameWithCode;

    @Override
    public void doTag() {
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();
        Gson gson = new Gson();
        try {
            Class<?> clazz = Class.forName(enumName);
            String[] nameAndCode = this.nameWithCode.split(";");
            Method toName = clazz.getMethod(nameAndCode[0]);
            Method toCode = clazz.getMethod(nameAndCode[1]);
            //得到enum的所有实例
            Object[] objs = clazz.getEnumConstants();
            List<Object> list = new ArrayList<>();
            for (Object obj : objs) {
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                map.put("name", (String) toName.invoke(obj));
                map.put("code", (String) toCode.invoke(obj));
                list.add(map);
            }
            out.println(gson.toJson(list));
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

    public String getNameWithCode() {
        return nameWithCode;
    }

    public void setNameWithCode(String nameWithCode) {
        this.nameWithCode = nameWithCode;
    }
}
