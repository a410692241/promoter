package com.linayi.controller.promoter;

import java.util.List;

import com.linayi.entity.user.AuthenticationApply;
import com.linayi.exception.BusinessException;
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
import org.springframework.web.multipart.MultipartFile;

@Api(value = "Promoter",description="家庭服务师接口")

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
	@ApiOperation(value = "家庭服务师首页", notes = "", produces =
			"application/xml,application/json")
	@RequestMapping
			(value = "/promoterIndex.do", method = RequestMethod.POST)
	public Object promoterIndex() {
		try {
			PromoterOrderMan promoterOrderMan = new PromoterOrderMan();
			Integer userId = getUserId();
			promoterOrderMan.setUserId(userId);
			PromoterOrderMan currentPromoterOrderMan = promoterOrderManService.getIndexData(promoterOrderMan);
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
			promoterOrderMan1.setUserId(userId);
			PromoterOrderMan currentPromoterOrderMan = promoterOrderManService.getOpenOrderManOrderList(promoterOrderMan1);
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
				promoterOrderMan1.setPageSize(10);
			}

			Integer userId = getUserId();
			promoterOrderMan1.setUserId(userId);

			List<PromoterOrderMan> promoterOrderManList = promoterOrderManService.getOpenOrderManInfoList(promoterOrderMan1);
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

        /*    if (promoterOrderMan1.getOrderManId() == null) {
                Integer userId = getUserId();
                promoterOrderMan1.setOrderManId(userId);
            }*/
            /*PromoterOrderMan currentPromoterOrderMan = promoterOrderManService.memberListOrderStatistics(promoterOrderMan1);*/

			PromoterOrderMan currentPromoterOrderMan = promoterOrderManService.getOrderManData(promoterOrderMan1);
            return new ResponseData(currentPromoterOrderMan);
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    // 会员列表-会员列表
    @ApiOperation(value = "会员列表-会员列表", produces = "application/xml,application/json")
    @RequestMapping(value = "/memberList.do", method = RequestMethod.POST)
    public Object memberList(@RequestBody PromoterVo.MemberListObj promoterOrderMan) {
        PageResult<PromoterOrderMan> pageResult = new PageResult<>();
        try {
            ParamValidUtil<PromoterOrderMan> pv = new ParamValidUtil<>(promoterOrderMan);
            PromoterOrderMan promoterOrderMan1 = pv.transObject(PromoterOrderMan.class);
            if (promoterOrderMan1.getPageSize() == null) {
                promoterOrderMan1.setPageSize(8);
            }
			promoterOrderMan1.setUserId(promoterOrderMan1.getOrderManId());
			List<PromoterOrderMan> orderManMemberList = promoterOrderManService.getMemberData(promoterOrderMan1);
           /* List<OrderManMember> orderManMemberList = promoterOrderManService.getMemberData(promoterOrderMan1);*/
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

	@ApiOperation(value = "提交邀请会员开通", notes = "根据会员ID",produces = "application/xml,application/json")
	@RequestMapping(value ="openPromoterDuration.do",method=RequestMethod.POST)
	public Object openPromoterDuration(@RequestBody PromoterVo.openPromoterDuration param){
		ResponseData rr = null;
		try{
			Integer promoterDuration = param.getPromoterDuration();
			String memberLevel = param.getMemberLevel();
			Integer userId = getUserId();
			Integer uid = param.getUserId();
			orderManMemberService.updateValidTimeById(uid,userId,memberLevel,promoterDuration);
			rr=new ResponseData("邀请会员申请成功！");
			return rr;
		}catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		}catch(Exception e){
			return new ResponseData(ErrorType.SYSTEM_ERROR);
		}
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
		try {
			ParamValidUtil<PromoterOrderMan> pv = new ParamValidUtil<>(param);
			PromoterOrderMan promoterOrderMan = pv.transObject(PromoterOrderMan.class);
			promoterOrderMan.setUserId(getUserId());
			PromoterOrderMan promoterOrder= promoterOrderManService.getPersonalOrderProfit(promoterOrderMan);
			return new ResponseData(promoterOrder);
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
				orders1.setOrderManId(getUserId());
				ordersList = orderService.getOrdersList(orders1);
				return new ResponseData(ordersList);
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR);
		}
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


	//邀请家庭服务师（扫二维码不用审核版本）
	@ApiOperation(value = "扫二维码邀请", produces = "application/xml,application/json")
		@PostMapping("/inviteOrderMan.do")
	@ResponseBody
	public Object inviteOrderMan(String realName,String areaCode, String mobile,Integer userId,String address, MultipartFile[] file) {
		try {
			AuthenticationApply apply = new AuthenticationApply();
			apply.setAddress(address);
			apply.setRealName(realName);
			apply.setMobile(mobile);
			if(userId != null){
				apply.setUserId(userId);
			}
			apply.setAreaCode(areaCode);
			apply.setApplierId(getUserId());
			//判断对象和数组是否为null
			if (apply != null && file.length == 2) {
				promoterOrderManService.inviteOrderMan(apply, file);
			}
			return new ResponseData("申请成为家庭服务师成功，请等待后台系统审核!");
		} catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}

	}



	@ApiOperation(value = "会员列表进入的订单列表",notes = "会员列表进入的订单列表")
	@RequestMapping(value = "/getMemberOrderList.do", method = RequestMethod.POST)
	public Object getMemberOrderList(@RequestBody PromoterVo.MemberOrderList orders){
		PageResult<Orders> promoterOrder = new PageResult<>();
		try {
			ParamValidUtil<PromoterOrderMan> pv = new ParamValidUtil<>(orders);
			PromoterOrderMan promoterOrderMan = pv.transObject(PromoterOrderMan.class);
			promoterOrderMan.setOrderManId(getUserId());
			promoterOrderMan.setUserId(promoterOrderMan.getMemberId());
			List<Orders> ordersList = promoterOrderManService.getMemberOrderList(promoterOrderMan);
			PageResult<Orders> pr = new PageResult<>(ordersList,promoterOrderMan);
			return new ResponseData(pr);
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR);
		}
	}

	// 会员进入的订单列表-订单统计
	@ApiOperation(value = "会员列表进入的订单列表-订单统计", produces = "application/xml,application/json")
	@RequestMapping(value = "/getMemberOrderData.do", method = RequestMethod.POST)
	public Object getMemberOrderData(@RequestBody PromoterVo.MemberOrderData orderManMember) {
		try {
			ParamValidUtil<PromoterOrderMan> pv = new ParamValidUtil<>(orderManMember);
			PromoterOrderMan promoterOrderMan = pv.transObject(PromoterOrderMan.class);
			promoterOrderMan.setOrderManId(getUserId());
			promoterOrderMan.setUserId(promoterOrderMan.getMemberId());
			PromoterOrderMan currentOrderManMember = promoterOrderManService.getMemberOrderData(promoterOrderMan);
			return new ResponseData(currentOrderManMember);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseData(ErrorType.SYSTEM_ERROR);

	}



}
