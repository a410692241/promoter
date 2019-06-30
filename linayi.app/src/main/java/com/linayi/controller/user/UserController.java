package com.linayi.controller.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.linayi.controller.BaseController;
import com.linayi.util.ParamValidUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.linayi.entity.account.Account;
import com.linayi.entity.user.User;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.account.AccountService;
import com.linayi.service.user.UserService;
import com.linayi.util.ResponseData;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user/user")
public class UserController extends BaseController {
	@Resource
	private AccountService accountService;
    @Resource
	private UserService userService;

	@RequestMapping(value = "/list.do", method = RequestMethod.POST)
	@ResponseBody
	public Object userList(User user) {
		try {
			Account param = new Account();
			List<Account> list= accountService.selectAccountList(param);
			return new ResponseData(list);
		} catch (BusinessException e) {
			return new ResponseData(e.getErrorType());
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR);
		}
	}


    /**
     * 查看用户信息
     */
    @RequestMapping(value = "/userInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public Object userInfo (@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<User> pa = new ParamValidUtil<>(param);
            Integer userId = getUserId();
            User user = pa.transObj(User.class);
            user.setUserId(userId);
            return new ResponseData(userService.userInfo(user)).toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }


    /**
     * 保存用户信息
     */
    @RequestMapping(value = "/saveUserInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public Object saveUserInfo (
            @RequestParam(value = "headFile",required =false) MultipartFile headFile,
            @RequestParam(value = "nickname",required =false) String nickname,
            @RequestParam(value = "birthday",required =false) String birthday,
            @RequestParam(value = "sex",required =false) String sex) {

        try {
            Integer userId = getUserId();
            User user = new User();
            user.setUserId(userId);
            user.setNickname(nickname);
            user.setBirthday(birthday);
            user.setSex(sex);
            userService.saveUserInfo(headFile,user);
            User userInfo = userService.userInfo(user);
            return new ResponseData(userInfo).toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    /**
     * 提取去哪买用户信息
     */
    @RequestMapping(value = "/getQnmInfoToLinSheng.do",method = RequestMethod.POST)
    @ResponseBody
    public Object getQnmInfoToLinSheng (@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<User> pa = new ParamValidUtil<>(param);
            Integer userId = getUserId();
            User user = pa.transObj(User.class);
            user.setUserId(userId);
            return new ResponseData(userService.userInfo(user)).toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    /**
     * 用户绑定家庭服务师
     * @param param
     * @return
     */
    @RequestMapping(value = "/bindingHomeHelper.do",method = RequestMethod.POST)
    @ResponseBody
    public Object bindingHomeHelper(@RequestBody Map<String, Object> param){
        try {
            String mobile = (String) param.get("mobile");
            if(StringUtils.isNotBlank( mobile)){
                Integer userId = getUserId();
                return userService.bindingHomeHelper(userId,mobile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
    }
}