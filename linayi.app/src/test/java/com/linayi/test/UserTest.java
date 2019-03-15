package com.linayi.test;


import com.linayi.dao.user.UserMapper;
import com.linayi.entity.user.User;
import com.linayi.enums.Sex;
import com.linayi.service.user.UserService;
import com.linayi.util.ImageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring-context.xml")
public class UserTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;


    @Test
    public void testUser() {
        User user = new User();
        user.setUserId(1);
        user.setSex("FEMALE");

        user.setNickname("123");

//        userService.userInfo(user);
        userService.saveUserInfo(null,user);
    }

}