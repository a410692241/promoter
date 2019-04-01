package com.linayi.test;


import com.linayi.dao.user.UserMapper;
import com.linayi.entity.correct.Correct;
import com.linayi.entity.user.User;
import com.linayi.enums.CorrectStatus;
import com.linayi.enums.Sex;
import com.linayi.service.correct.CorrectService;
import com.linayi.service.user.UserService;
import com.linayi.util.ImageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring-context.xml")
public class UserTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CorrectService correctService;


    @Test
    public void testUser() {
        User user = new User();
        user.setUserId(1);
        user.setSex("FEMALE");

        user.setNickname("123");

//        userService.userInfo(user);
        userService.saveUserInfo(null,user);
    }


    @Test
    public void PriceAffectTimer() {
        System.out.println("Method:价格生效方法开始！");
        Correct correct = new Correct();
        correct.setStatus(CorrectStatus.AUDIT_SUCCESS.toString());
        //查询审核通过的数据
        List<Correct> list = correctService.getCorrect(correct);
        if(list.size()>0){
            for(Correct corrects : list){
                correctService.priceAffect(corrects);
            }
        }
        System.out.println("Method:价格生效结束！！！");
    }

}