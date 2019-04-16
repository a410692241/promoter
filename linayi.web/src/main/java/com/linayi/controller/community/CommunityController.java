package com.linayi.controller.community;

import java.util.List;

import javax.annotation.Resource;

import com.linayi.entity.account.Account;
import com.linayi.exception.BusinessException;
import com.linayi.service.account.AccountService;
import com.linayi.service.community.CommunitySupermarketService;
import com.linayi.service.community.impl.CommunitySupermarketServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linayi.entity.community.Community;
import com.linayi.exception.ErrorType;
import com.linayi.service.community.CommunityService;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;


@Controller
@RequestMapping("/community/community")
public class CommunityController {
	
	@Resource
	private CommunityService communityService;

	@Resource
	private AccountService accountService;
	@Resource
	private CommunitySupermarketService communitySupermarketService;

	/**
	 * 展示社区列表
	 * @param community
	 * @return
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public Object getCommunityList(Community community) {
		List<Community> list = communityService.getCommunityList(community);
		PageResult<Community> rr = new PageResult<>(list, community.getTotal());
		return rr;
	}
	//展示编辑或新增界面
	@RequestMapping("/edit.do")
	public ModelAndView updateCommunity(Community community) {
		ModelAndView mv = new ModelAndView("jsp/community/CommunityEdit");
		if (community.getCommunityId() !=null) {
			community = communityService.getCommunity(community);
			mv.addObject("community", community);
		}
		return mv;
	}
	//新增或修改社区信息
	@RequestMapping("/save.do")
	@ResponseBody
	public ResponseData save(Community community) {
		ResponseData rr = null;
		try {
			communityService.addOrUpdateCommunity(community);
			rr = new ResponseData(community);
		} catch (Exception e) {
			rr = new ResponseData(ErrorType.SYSTEM_ERROR);
		}
		return rr;
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	public ResponseData delete(Integer communityId) {
		ResponseData rr = null;
		try {
			communityService.removeCommunity(communityId);
			rr = new ResponseData(communityId);
		} catch (Exception e) {
			rr = new ResponseData(ErrorType.SYSTEM_ERROR);
		}
		return rr;
	}

	@RequestMapping("/info.do")
	@ResponseBody
	public ModelAndView show(Integer communityId) {
		ModelAndView mv = new ModelAndView("jsp/community/CommunityShow");
		if (communityId !=null) {
			Account account = accountService.selectAccountBycommunityId(communityId);
			if ("ENABLED".equals(account.getStatus())){
				account.setStatus("启用");
			}else if ("DISABLED".equals(account.getStatus())){
				account.setStatus("禁用");
			}else{
				account.setStatus("未设置状态！");
			}

			mv.addObject("account", account);
		}
		return mv;
	}

	@RequestMapping("/updateCommunityPrice.do")
	@ResponseBody
	public Object updateCommunityPrice(Integer communityId) {
		try {
			communitySupermarketService.toUpdateCommunityPrice(communityId);
			return new ResponseData("社区商品价格更新成功！");
		}catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
	}
}
