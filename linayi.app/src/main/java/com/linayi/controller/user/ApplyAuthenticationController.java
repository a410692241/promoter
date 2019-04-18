package com.linayi.controller.user;

import com.linayi.controller.BaseController;
import com.linayi.entity.user.AuthenticationApply;
import com.linayi.exception.ErrorType;
import com.linayi.service.user.AuthenticationApplyService;
import com.linayi.util.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user/authentication")
public class ApplyAuthenticationController extends BaseController{

	@Resource
	private AuthenticationApplyService authenticationApplyService;

	/**
	 *	插入数据show.do
	 * @param realName	真实姓名
	 * @param mobile	联系电话
	 * @param file		身份证正反面
	 * @return	responseData对象json字符串
	 */
	@RequestMapping("/applySharer.do")
	@ResponseBody
	public Object applySharer(String realName, String mobile, MultipartFile[] file) {
		AuthenticationApply apply = new AuthenticationApply();
		apply.setUserId(getUserId());
		apply.setRealName(realName);
		apply.setMobile(mobile);
		//判断对象和数组是否为null
		if(apply != null && file.length==2){
			Object responseData = authenticationApplyService.applySharer(apply,file);
			return responseData;
		}
		return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
	}

	@RequestMapping("/applyProcurer.do")
	@ResponseBody
	public Object applyProcurer(String realName, String mobile, String address, Integer supermarketId, String areaCode, MultipartFile[] file) {
		AuthenticationApply apply = new AuthenticationApply();
		apply.setAddress(address);
		apply.setSupermarketId(supermarketId);
		apply.setAreaCode(areaCode);
		apply.setUserId(getUserId());
		apply.setRealName(realName);
		apply.setMobile(mobile);
		//判断对象和数组是否为null
		if(apply != null && file.length==2){
			Object responseData = authenticationApplyService.applyProcurer(apply,file);
			return responseData;
		}
		return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
	}

	@RequestMapping("/applyDeliverer.do")
	@ResponseBody
	public Object applyDeliverer(String realName, String mobile, String address, Integer smallCommunityId, MultipartFile[] file) {
		AuthenticationApply apply = new AuthenticationApply();
		apply.setAddress(address);
		apply.setSmallCommunityId(smallCommunityId);
		apply.setUserId(getUserId());
		apply.setRealName(realName);
		apply.setMobile(mobile);
		//判断对象和数组是否为null
		if(apply != null && file.length==2){
			Object responseData = authenticationApplyService.applyDeliverer(apply,file);
			return responseData;
		}
		return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
	}

	@RequestMapping("/applySpokesman.do" )
	@ResponseBody
	public Object applySpokesman(AuthenticationApply authenticationApply, MultipartFile file) {
		authenticationApply.setUserId(getUserId());
		Object responseData = authenticationApplyService.applySpokesman(authenticationApply,file);
		return responseData;
	}


}
