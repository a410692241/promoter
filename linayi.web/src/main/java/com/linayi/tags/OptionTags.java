package com.linayi.tags;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Map;

public class OptionTags extends SimpleTagSupport {
    private String sqlId;
    private String param;
    private final static ApplicationContext applicationContext;
    private final static SqlSessionTemplate sqlSessionTemplate;
    static {
        applicationContext =new ClassPathXmlApplicationContext("classpath:config/spring-context.xml");
        sqlSessionTemplate = (SqlSessionTemplate) applicationContext.getBean("sqlSessionTemplate");
    }
    @Override
    public void doTag() throws IOException {
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();
        Gson gson = new Gson();
        if (this.sqlId != null) {
            if (param != null && param != "") {
                Map map = gson.fromJson(param, new TypeToken<Map>() {}.getType());
                out.println(gson.toJson(sqlSessionTemplate.selectList("com.linayi.dao.tags.TagsMapper." + sqlId, map)));
            }else{
                out.println(gson.toJson(sqlSessionTemplate.selectList("com.linayi.dao.tags.TagsMapper." + sqlId)));
            }
        }
    }


    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }


}
