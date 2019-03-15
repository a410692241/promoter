package com.linayi.sync.factory;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MySqlSessionFactory {

    private static String resource = "sync/mybatis-config.xml";

    public static SqlSession getSqlSession(){
        SqlSession sqlSession = null;
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = factory.openSession(false);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sqlSession;
    }
}
