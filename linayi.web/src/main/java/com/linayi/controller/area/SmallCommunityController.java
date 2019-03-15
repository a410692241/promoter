package com.linayi.controller.area;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.linayi.entity.order.Orders;
import com.linayi.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.exception.ErrorType;
import com.linayi.service.area.SmallCommunityService;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;
@Controller
@RequestMapping("/smallCommunity")
public class SmallCommunityController {
	@Resource
	private SmallCommunityService smallCommunityService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/save.do")
	@ResponseBody
	public ResponseData save(SmallCommunity smallCommunity){
		ResponseData rr = null;
		if(smallCommunity.getSmallCommunityId() == null){
			smallCommunityService.insert(smallCommunity);
			rr = new ResponseData(smallCommunity);
		}else{
			Date date = new Date();
			smallCommunity.setUpdateTime(date);
			smallCommunityService.updateSmallCommunity(smallCommunity);
			rr = new ResponseData(smallCommunity);
		}
		return  rr;
	}
	
	@RequestMapping("/list.do")
	@ResponseBody
	public Object getSmallCommunity(
			String streetName,SmallCommunity smallCommunity,String type){
		List<SmallCommunity> list = smallCommunityService.getSmallCommunity
				(streetName, smallCommunity,smallCommunity.getCommunityId(),type);
        if(list.size()>0){
        	PageResult<SmallCommunity> pageResult = new PageResult<SmallCommunity>(list, smallCommunity.getTotal());
        	 return pageResult;
        }else{
        	return list;
        }
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	public ResponseData removeSmallCommunity(Integer smallCommunityId){
		ResponseData rr = null;
		try {
			smallCommunityService.removeSmallCommunity(smallCommunityId);
			rr = new ResponseData(smallCommunityId);
		} catch (Exception e) {
			rr = new ResponseData(ErrorType.SYSTEM_ERROR.toString());
		}
		return rr;
	}

	@RequestMapping("/saveOrders.do")
	@ResponseBody
	public ResponseData saveOrders(Orders orders){
		ResponseData rr = null;
		if(orders.getCommunityStatus()!=null&&orders.getCommunityStatus()!=""){
			orderService.updateOrderById(orders);
			rr = new ResponseData(orders);
			return rr;
		}else{
			rr = new ResponseData(ErrorType.UPDATE_STATUS);
			return rr;
		}
	}

}
