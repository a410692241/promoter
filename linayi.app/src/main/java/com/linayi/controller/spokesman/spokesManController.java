package com.linayi.controller.spokesman;

import com.linayi.controller.BaseController;
import com.linayi.entity.spokesman.Spokesman;
import com.linayi.entity.user.User;
import com.linayi.exception.ErrorType;
import com.linayi.service.spokesman.SpokesmanService;
import com.linayi.service.user.UserService;
import com.linayi.util.ResponseData;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/spokesman/spokesman")
public class spokesManController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private SpokesmanService spokesmanService;

    //添加代言人 测试用
   /* @RequestMapping(value = "/addSpokesman.do",method = RequestMethod.POST)
    @ResponseBody
    public Object addSpokesman (Spokesman spokesman) {
        try {
            spokesmanService.addSpokesman(spokesman);
            return new ResponseData("添加代言人成功!");
        }catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }*/

    @RequestMapping(value = "/getUserInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public Object getUserInfo () {
        try {
            Integer userId = getUserId();
           User user = userService.getUserForSpokesMan(userId);
            return new ResponseData(user);
        }catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }

    }

}
