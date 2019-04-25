package com.linayi.controller.user;


import com.linayi.entity.user.User;
import com.linayi.service.user.UserService;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * app端用户管理
 */
@Controller
@RequestMapping("/user/user")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping("/list.do")
    @ResponseBody
    public Object userList(User user) {
        List<User> list = userService.selectUserList(user);
        PageResult<User> pageResult = new PageResult<User>(list, user.getTotal());
        return pageResult;
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Object save(User user) {
        if (user.getUserId() != null) {
            this.userService.updateUserByuserId(user);
        } else {
            this.userService.insertUser(user);
        }
        return new ResponseData(user);
    }

    @RequestMapping("/get.do")
    @ResponseBody
    public Object get(Integer userId) {
        User user = userService.selectUserByuserId(userId);
        ModelAndView mv = new ModelAndView("/jsp/user/UserInformationEdit");
        mv.addObject("user", user);
        return mv;
    }

}