package com.linayi.controller.user;

import com.linayi.entity.user.AuthenticationApply;
import com.linayi.exception.ErrorType;
import com.linayi.service.user.AuthenticationApplyService;
import com.linayi.util.ImageUtil;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;


/**
 * app端用户管理
 */
@Controller
@RequestMapping("/user/authentication")
public class AuthenticationApplyController {

	@Resource
	private AuthenticationApplyService authenticationApplyService;


	@RequestMapping("/list.do")
	@ResponseBody
	public Object authenticationApplyList(AuthenticationApply apply) {
		List<AuthenticationApply> list = authenticationApplyService.selectAuthenticationApplyList(apply);
		if (list != null && list.size() > 0) {
			for (AuthenticationApply authenticationApply : list) {
				authenticationApply.setIdCardBack(ImageUtil.dealToShow(authenticationApply.getIdCardBack()));
				authenticationApply.setIdCardFront(ImageUtil.dealToShow(authenticationApply.getIdCardFront()));
			}
		}
		PageResult<AuthenticationApply> pageResult = new PageResult<AuthenticationApply>(list, apply.getTotal());
		return pageResult;
	}

	@RequestMapping("/show.do")
	public ModelAndView showAuthenticationApply(AuthenticationApply apply) {
		ModelAndView mv = new ModelAndView("jsp/user/AuthenticationApplyShow");
		AuthenticationApply apply1 = this.authenticationApplyService.getAuthenticationApplyByapplyId(apply.getApplyId());
		apply1.setIdCardFront(ImageUtil.dealToShow(apply1.getIdCardFront()));
		apply1.setIdCardBack(ImageUtil.dealToShow(apply1.getIdCardBack()));
		apply1.setImage(ImageUtil.dealToShow(apply1.getImage()));
		mv.addObject("apply1", apply1);
		return mv;
	}

	@RequestMapping(value = "/authenticationAudit.do",produces = {"text/html;charset=utf-8"})
	@ResponseBody
	public Object authenticationApplyAudit(AuthenticationApply apply) {
		try {
			if("家庭服务师".equals(apply.getAuthenticationType())&&"AUDIT_SUCCESS".equals(apply.getStatus())){
				if("undefined".equals(apply.getIdentity()) || "".equals(apply.getIdentity())){
					return new ResponseData(ErrorType.ERROR_ONE).toString();
				}
			}
			authenticationApplyService.updateAuthenticationApplyAndUserInfo(apply);
			return new ResponseData("S").toString();
		} catch (NullPointerException e) {
			return new ResponseData(ErrorType.ERROR_ONE).toString();
		} catch (Exception e) {
			return new ResponseData(ErrorType.ERROR_TWO).toString();
		}
	}
}
