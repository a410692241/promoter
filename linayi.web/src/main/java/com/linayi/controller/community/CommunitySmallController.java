package com.linayi.controller.community;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.community.Community;
import com.linayi.exception.ErrorType;
import com.linayi.service.area.SmallCommunityService;
import com.linayi.util.ResponseData;

@Controller
@RequestMapping("/communitySmallCommunity")
public class CommunitySmallController {
	@Resource
	private SmallCommunityService smallCommunityService;
	
	//社区与小区绑定
	@RequestMapping("/bind.do")
	@ResponseBody
	public ResponseData bind(SmallCommunity smallCommunity){
		ResponseData rr = null;
		try{
			Date date = new Date();
			smallCommunity.setUpdateTime(date);
			smallCommunityService.updateSmallCommunity(smallCommunity);
			rr = new ResponseData(smallCommunity);
		}catch(Exception e){
			rr = new ResponseData(ErrorType.SYSTEM_ERROR.toString());
		}
		return rr;
	}
	//社区与小区解除绑定
	@RequestMapping("/unbind.do")
	@ResponseBody
	public ResponseData unbind(SmallCommunity smallCommunity){
		ResponseData rr = null;
		try{
			Date date = new Date();
			smallCommunity.setUpdateTime(date);
			smallCommunity.setCommunityId(null);
			smallCommunityService.removeBind(smallCommunity);
			rr = new ResponseData(smallCommunity);
		}catch(Exception e){
			rr = new ResponseData(ErrorType.SYSTEM_ERROR.toString());
		}
		return rr;
	}


	
}
