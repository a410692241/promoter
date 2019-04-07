package com.linayi.controller.community;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linayi.entity.community.CommunitySupermarket;
import com.linayi.exception.ErrorType;
import com.linayi.service.community.CommunitySupermarketService;
import com.linayi.util.ResponseData;

@Controller
@RequestMapping("/communitySupermarket")
public class CommunitySupermarketController {
	@Resource
	private CommunitySupermarketService communitySupermarketService;
	
	@RequestMapping("/bind.do")
	@ResponseBody
	public ResponseData bind(CommunitySupermarket communitySupermarket){
		ResponseData rr = null;
//		try{
			communitySupermarketService.bind(communitySupermarket);
			rr = new ResponseData(communitySupermarket);
//		}catch(Exception e){
//			rr = new ResponseData(ErrorType.SYSTEM_ERROR);
//		}
		return rr;
	}
	
	
	//社区超市解绑
	@RequestMapping("/unbind.do")
	@ResponseBody
	public ResponseData unbind(CommunitySupermarket communitySupermarket){
		ResponseData rr = null;
		try{
			communitySupermarketService.unbind(communitySupermarket);
			rr = new ResponseData(communitySupermarket);
		}catch(Exception e){
			rr = new ResponseData(ErrorType.SYSTEM_ERROR);
		}
		return rr;
	}
}
