package com.linayi.controller.promoter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.linayi.controller.BaseController;
import com.linayi.entity.order.Orders;
import com.linayi.entity.promoter.OrderManMember;
import com.linayi.entity.promoter.PromoterOrderMan;
import com.linayi.entity.user.ReceiveAddress;
import com.linayi.entity.user.User;
import com.linayi.exception.ErrorType;
import com.linayi.service.address.ReceiveAddressService;
import com.linayi.service.order.OrderService;
import com.linayi.service.promoter.OrderManMemberService;
import com.linayi.service.promoter.PromoterOrderManService;
import com.linayi.util.PageResult;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;
import com.linayi.vo.PageVo;
import com.linayi.vo.promoter.PromoterVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "Promoter",description="推广商接口")

@RestController
@RequestMapping("/promoter/promoter")
public class PromoterController extends BaseController {
	@Autowired
	private OrderManMemberService orderManMemberService;
	@Autowired
	private ReceiveAddressService receiveAddressService;
	@Autowired
	private PromoterOrderManService promoterOrderManService;
	@Autowired
	private OrderService orderService;


	// 推广商首页
	@ApiOperation(value = "推广商首页", notes = "", produces =

			"application/xml,application/json")
	@RequestMapping

			(value = "/promoterIndex.do", method = RequestMethod.POST)
	public Object promoterIndex() {
		try {
			PromoterOrderMan promoterOrderMan = new PromoterOrderMan();
			Integer userId = getUserId();
			promoterOrderMan.setOrderManId(userId);
			PromoterOrderMan currentPromoterOrderMan = promoterOrderManService.promoterIndex(promoterOrderMan);

			return new ResponseData(currentPromoterOrderMan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseData(ErrorType.SYSTEM_ERROR);
	}

	// 我的团队-订单统计
	@ApiOperation(value = "我的团队-订单统计", notes = "", produces = "application/xml,application/json")
	@RequestMapping

			(value = "/myTeamOrderStatistics.do", method = RequestMethod.POST)
	public Object myTeamOrderStatistics(@RequestBody PromoterVo.MyTeamOrderStatisticsObj promoterOrderMan) {
		try {
			ParamValidUtil<PromoterOrderMan> pv = new ParamValidUtil<>(promoterOrderMan);
			PromoterOrderMan promoterOrderMan1 = pv.transObject(PromoterOrderMan.class);
			Integer userId = getUserId();
			promoterOrderMan1.setOrderManId(userId);

			PromoterOrderMan currentPromoterOrderMan = promoterOrderManService.myTeamOrderStatistics(promoterOrderMan1);

			return new ResponseData(currentPromoterOrderMan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseData(ErrorType.SYSTEM_ERROR);
	}

	// 我的团队-下单员列表
	@ApiOperation(value = "我的团队-下单员列表", produces = "application/xml,application/json")
	@RequestMapping(value="/orderManList.do",method=RequestMethod.POST)
	public Object orderManList(@RequestBody PageVo  promoterOrderMan) {
		PageResult<PromoterOrderMan> pageResult = new PageResult<>();
		try {
			ParamValidUtil<PromoterOrderMan> pv = new ParamValidUtil<>(promoterOrderMan);
			PromoterOrderMan promoterOrderMan1 = pv.transObject(PromoterOrderMan.class);

			if (promoterOrderMan1.getPageSize() == null) {
				promoterOrderMan1.setPageSize(15);
			}

			Integer userId = getUserId();
			promoterOrderMan1.setOrderManId(userId);

			List<PromoterOrderMan> promoterOrderManList = promoterOrderManService.orderManList(promoterOrderMan1);
			pageResult = new PageResult<>(promoterOrderManList,promoterOrderMan1);
			return new ResponseData(pageResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseData(ErrorType.SYSTEM_ERROR);
	}

	// 会员列表-订单统计
	@ApiOperation(value = "会员列表-订单统计", produces = "application/xml,application/json")
	@RequestMapping(value = "/memberListOrderStatistics.do", method = RequestMethod.POST)
	public Object memberListOrderStatistics(@RequestBody PromoterVo.MemberListOrderStatisticsObj promoterOrderMan) {
		try {
			ParamValidUtil<PromoterOrderMan> pv = new ParamValidUtil<>(promoterOrderMan);
			PromoterOrderMan promoterOrderMan1 = pv.transObject(PromoterOrderMan.class);

			if (promoterOrderMan1.getOrderManId() == null) {
				Integer userId = getUserId();
				promoterOrderMan1.setOrderManId(userId);
			}
			PromoterOrderMan currentPromoterOrderMan = promoterOrderManService.memberListOrderStatistics(promoterOrderMan1);

			return new ResponseData(currentPromoterOrderMan);
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
	}

	// 会员列表-会员列表
	@ApiOperation(value = "会员列表-会员列表", produces = "application/xml,application/json")
	@RequestMapping(value = "/memberList.do", method = RequestMethod.POST)
	public Object memberList(@RequestBody PromoterVo.MemberListObj promoterOrderMan) {
		PageResult<OrderManMember> pageResult = new PageResult<>();
		try {
			ParamValidUtil<PromoterOrderMan> pv = new ParamValidUtil<>(promoterOrderMan);
			PromoterOrderMan promoterOrderMan1 = pv.transObject(PromoterOrderMan.class);

			if (promoterOrderMan1.getPageSize() == null) {
				promoterOrderMan1.setPageSize(8);
			}

			if (promoterOrderMan1.getOrderManId() == null) {
				Integer userId = getUserId();
				promoterOrderMan1.setOrderManId (userId);
			}
			List<OrderManMember> orderManMemberList = promoterOrderManService.memberList(promoterOrderMan1);
			pageResult = new PageResult<>(orderManMemberList, promoterOrderMan1);
			return new ResponseData(pageResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseData(ErrorType.SYSTEM_ERROR);
	}

	@ApiOperation(value = "新增会员", notes = "根据手机号",produces = "application/xml,application/json")
	@RequestMapping(value = "openPromoter.do",method=RequestMethod.POST)
	public Object openPromoter(@RequestBody PromoterVo.openPromoter param){
		try {
			String mobile = param.getMobile();
			Integer uid = getUserId();
			User user = orderManMemberService.insertMember(mobile,uid);
			if(user !=null){
				User newUser = new User();
				newUser.setUserId(user.getUserId());
				newUser.setOpenMemberInfoId(-1);
				newUser.setHeadImage(user.getHeadImage());
				newUser.setNickname(user.getNickname());
				newUser.setIsRegist(user.getIsRegist());
				ResponseData rr = new ResponseData(newUser);
				return rr;
			}
			return new ResponseData(ErrorType.HAVE_OPEN_Promoter);
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR);
		}

	}

	@ApiOperation(value = "新增会员的地址", notes = "根据会员ID",produces = "application/xml,application/json")
	@RequestMapping(value ="addPromoterAddress.do",method=RequestMethod.POST)
	public Object addPromoterAddress(@RequestBody PromoterVo.addPromoterAddress param){
		try {
			ParamValidUtil<ReceiveAddress> pv = new ParamValidUtil<>(param);
			ReceiveAddress receiveAddress = pv.transObject(ReceiveAddress.class);
			receiveAddress.setAddressType("MINE");
			receiveAddressService.addReceiveAddress(receiveAddress);
			ResponseData rr = new ResponseData("success");
			return rr;
		} catch (Exception e) {
			return new ResponseData(ErrorType.PARAM_ERROR);
		}
	}

	@ApiOperation(value = "新增会员的有效时间", notes = "根据会员ID",produces = "application/xml,application/json")
	@RequestMapping(value ="openPromoterDuration.do",method=RequestMethod.POST)
	public ResponseData openPromoterDuration(@RequestBody PromoterVo.openPromoterDuration param){
		ResponseData rr = null;
//		try{
			Integer promoterDuration = param.getPromoterDuration();
			String memberLevel = param.getMemberLevel();
			Integer userId = getUserId();
			Integer uid = param.getUserId();
			orderManMemberService.updateValidTimeById(uid,userId,memberLevel,promoterDuration);
			rr=new ResponseData("success");
			return rr;
//		}catch(Exception e){
//			return new ResponseData(ErrorType.SYSTEM_ERROR);
//		}
	}

	@ApiOperation(value = "新增顾客的地址", notes = "",produces = "application/xml,application/json")
	@RequestMapping(value ="addCustomerAddress.do",method=RequestMethod.POST)
	public Object addCustomerAddress(@RequestBody PromoterVo.addCustomerAddress param){
		try {
			Integer userId = getUserId();
			ParamValidUtil<ReceiveAddress> pv = new ParamValidUtil<>(param);
			ReceiveAddress receiveAddress = pv.transObject(ReceiveAddress.class);
			receiveAddress.setUserId(userId);
			receiveAddress.setAddressType("CUSTOMER");
			receiveAddressService.addReceiveAddress(receiveAddress);
			ResponseData rr = new ResponseData("success");
			return rr;
		} catch (Exception e) {
			return new ResponseData(ErrorType.PARAM_ERROR);
		}
	}

	@ApiOperation(value = "顾客的地址集合", notes = "",produces = "application/xml,application/json")
	@RequestMapping(value ="customerAddressList.do",method=RequestMethod.POST)
	public Object customerAddressList(@RequestBody PromoterVo.customerAddressList param){
		try {
			Integer currentPage = param.getCurrentPage();
			Integer pageSize = param.getPageSize();
			ReceiveAddress receiveAddress = new ReceiveAddress();
			receiveAddress.setPageSize(pageSize);
			receiveAddress.setCurrentPage(currentPage);
			receiveAddress .setUserId(getUserId());
			List<ReceiveAddress> list = receiveAddressService.getListReceiveAddressByUserIdAndAdderssType(receiveAddress);
			PageResult<ReceiveAddress> pr = new PageResult<>(list,receiveAddress);
			return new ResponseData(pr);
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR);
		}
	}

	@ApiOperation(value = "代顾客下单的统计", notes = "",produces = "application/xml,application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "data", value = "本月或全部 ：MONTH或ALL", dataType = "String"),//paramType如果是表单形式就写form
	})
	@RequestMapping(value ="statisticalOrder.do",method=RequestMethod.POST)
	public Object statisticalOrder( @RequestBody PromoterVo.statisticalOrder param){
		ResponseData rr = null;
		try {
			String range = param.getDate();
			PromoterOrderMan promoterOrderMan = promoterOrderManService.getStatisALL(getUserId(), range,"CUSTOMER");
			promoterOrderMan.setOrderStatisticsData3(promoterOrderMan.getNumberOfMembers());
			if(promoterOrderMan.getNumberOfMembers() == null){
				promoterOrderMan.setOrderStatisticsData3(0);
			}
			rr = new ResponseData(promoterOrderMan);
			return rr;
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR);
		}
	}

	@ApiOperation(value = "代顾客下单", notes = "",produces = "application/xml,application/json")

	@RequestMapping(value ="helpCustomerUpOrder.do",method=RequestMethod.POST)
	public Object helpCustomerUpOrder(@RequestBody PromoterVo.helpCustomerUpOrder param){
		ResponseData rr = null;
		try {
			Integer receiveAddressId =  param.getReceiveAddressId();
			ReceiveAddress receiveAddress = new ReceiveAddress();
			receiveAddress.setReceiveAddressId(receiveAddressId);
			receiveAddress.setUserId(getUserId());
			receiveAddressService.accGoodsAddrDef(receiveAddress );
			rr = new ResponseData("success");
			return rr;
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR);
		}
	}

	@ApiOperation(value = "查询订单列表",notes = "根据用户Id或者接受地址Id,不能同时都传")
	@RequestMapping(value = "/getOrdersList.do", method = RequestMethod.POST)
	public Object getOrdersList(@RequestBody PromoterVo.OrdersObj orders){
		PageResult<Orders> ordersList = new PageResult<>();
		try {
			ParamValidUtil<Orders> pv = new ParamValidUtil<>(orders);
			Orders orders1 = pv.transObject(Orders.class);
			ordersList = orderService.getOrdersList(orders1);
			return new ResponseData(ordersList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseData(ErrorType.SYSTEM_ERROR);
	}

	// 订单列表-用户详情
	@ApiOperation(value = "订单列表-订单统计", produces = "application/xml,application/json")
	@RequestMapping(value = "/memberDetails.do", method = RequestMethod.POST)
	public Object memberDetails(@RequestBody PromoterVo.MemberDetailsObj orderManMember) {
		OrderManMember currentOrderManMember = new OrderManMember();
		try {
			ParamValidUtil<OrderManMember> pv = new ParamValidUtil<>(orderManMember);
			OrderManMember orderManMember2 = pv.transObject(OrderManMember.class);
			orderManMember2.setOrderManId(getUserId());
			currentOrderManMember = orderManMemberService.memberDetails(orderManMember2);
			return new ResponseData(currentOrderManMember);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseData(ErrorType.SYSTEM_ERROR);
	}
}
