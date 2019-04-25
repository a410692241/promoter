package com.linayi.service.promoter.impl;

import com.linayi.dao.account.AccountMapper;
import com.linayi.dao.order.OrdersGoodsMapper;
import com.linayi.dao.order.OrdersMapper;
import com.linayi.dao.promoter.OpenMemberInfoMapper;
import com.linayi.dao.promoter.OrderManMemberMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.account.Account;
import com.linayi.entity.order.Orders;
import com.linayi.entity.order.OrdersGoods;
import com.linayi.entity.promoter.OpenMemberInfo;
import com.linayi.entity.promoter.OrderManMember;
import com.linayi.entity.promoter.PromoterOrderMan;
import com.linayi.entity.user.User;
import com.linayi.enums.EnabledDisabled;
import com.linayi.enums.UserType;
import com.linayi.service.promoter.OrderManMemberService;
import com.linayi.service.promoter.PromoterOrderManService;
import com.linayi.service.user.UserService;
import com.linayi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderManMemberServiceImpl implements OrderManMemberService {
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private OrderManMemberMapper orderManMemberMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrdersGoodsMapper ordersGoodsMapper;
	@Autowired
	private OpenMemberInfoMapper openMemberInfoMapper;
	@Autowired
	private PromoterOrderManService promoterOrderManService;
	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public User insertMember(String mobile,Integer uid) {
		Integer userId = accountMapper.getUserIdByMobile(mobile);
		User info = userMapper.getUserByIdAndMemberLevel(userId);
		 User user = new User();
		User newUser = new User();
		//如果用户已是会员 返回null
		if(info != null){
			return null;
		}

		//如果用户不存在，先增加用户
		if(userId == null){
			 Account accountTB = new Account();
		        accountTB.setMobile(mobile);
		        accountTB.setPassword("a123456");
		        accountTB.setUpdateTime(new Date());
		        //添加用户
		        user.setNickname(mobile);
		        user.setNickname(mobile);
		        user.setCreateTime(new Date());
		        user.setUpdateTime(new Date());
		        userMapper.insertUser(user);
		        //新用户添加新号码
		        accountTB.setUserName(mobile);
		        accountTB.setStatus(EnabledDisabled.ENABLED.name());
		        accountTB.setUserType(UserType.USER.name());
		        accountTB.setCreateTime(new Date());
		        accountTB.setUserId(user.getUserId());
		        accountMapper.insertAccount(accountTB);

		        userId =user.getUserId();
		        newUser.setIsRegist("FALSE");
		}else{
			newUser.setIsRegist("TRUE");
		}

		//插入会员信息

		user = userMapper.selectUserByuserId(userId);
		newUser.setUserId(userId);
		newUser.setNickname(user.getNickname());
		if(user.getHeadImage() == null){
			newUser.setHeadImage("http://www.laykj.cn/wherebuy/images/2019/02/14/15/d40c2c26-20bc-4a4d-a012-e62c7ede7d80.png");
		}else{
			newUser.setHeadImage(user.getHeadImage());
		}
		return newUser;
	}

	@Override
	public OrderManMember getOrderDatail(Integer receiveAddressId, String range) {
		Orders orders = new Orders();
		int totalSum = 0;	//订单合计金额(通用)
		orders.setReceiveAddressId(receiveAddressId);
		if ("MONTH".equals(range)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(new Date());
			cl.set(Calendar.DAY_OF_MONTH,1);
			cl.set(Calendar.HOUR_OF_DAY,0);
			cl.set(Calendar.MINUTE,0);
			cl.set(Calendar.SECOND,0);
			orders.setCreateTimeStart(DateUtil.date2String(cl.getTime(),"yyyy-MM-dd HH:mm:ss"));
			cl.add(Calendar.MONTH,1);
			orders.setCreateTimeEnd(DateUtil.date2String(cl.getTime(),"yyyy-MM-dd HH:mm:ss"));
		}
		OrderManMember orderManMember = new OrderManMember();
		List<Orders> allOrder = ordersMapper.getALLOrder(orders);
		if (allOrder != null && allOrder.size() > 0){
			orderManMember.setNumberOfOrders(allOrder.size());
			for (Orders orders1 : allOrder) {
				List<OrdersGoods> ordersGoodsList = ordersGoodsMapper.getOrdersGoodsByOrdersId(orders1.getOrdersId());
				for (OrdersGoods ordersGoods : ordersGoodsList) {
					int price = ordersGoods.getPrice() * ordersGoods.getQuantity();
					totalSum += price;
				}
				//服务费
				totalSum += 1000;
			}
		} else {
			orderManMember.setNumberOfOrders(0);
		}
		orderManMember.setTotalSum(totalSum);
		return orderManMember;
	}

	/**
	 * 通过会员等级计算免费次数
	 * @param memberLevel
	 * @return
	 */
	private Integer getFreeNumberByMemberLevel(String memberLevel){
		if("NORMAL".equals(memberLevel)){
			return 0;
		}else if("SENIOR".equals(memberLevel)){
			return 5;
		}else{
			return 8;
		}
	}

	@Override
	public Integer updateValidTimeById(Integer uid,Integer userId,String memberLevel,Integer promoterDuration) {
		Integer openOrderManInfoId = userMapper.getOpenOrderManInfoIdByUserId(userId);

		OpenMemberInfo openMemberInfo = new OpenMemberInfo();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND,0);
		Date startTime = cal.getTime();
		openMemberInfo .setStartTime(startTime);

		cal.add(Calendar.MONTH, promoterDuration);
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND,0);
		Date endTime = cal.getTime();
		openMemberInfo.setEndTime(endTime);

		openMemberInfo.setFreeTimes(getFreeNumberByMemberLevel(memberLevel));
		openMemberInfo.setUserId(uid);
		openMemberInfo.setOrderManId(userId);
		openMemberInfo.setCreateTime(new Date());
		openMemberInfo.setMemberLevel(memberLevel);
		openMemberInfo.setOpenOrderManInfoId(openOrderManInfoId);
		openMemberInfoMapper.insert(openMemberInfo);

		OrderManMember record = new OrderManMember();
		record.setMemberId(uid);
		record.setOrderManId(userId);
		record.setCreateTime(new Date());
		orderManMemberMapper.insert(record );

		User user = new User();
		user.setUserId(uid);
		user.setIsMember("TRUE");
		user.setOpenMemberInfoId(openMemberInfo.getOpenMemberInfoId());
		userMapper.updateUserByuserId(user);
		return null;
	}

	@Override
	public OrderManMember memberDetails(OrderManMember orderManMember) {
		if("addressInfo".equals(orderManMember.getType())) {
			OrderManMember currentOrderManMember = getOrderDatail(orderManMember.getReceiveAddressId(),orderManMember.getDate());
			orderManMember.setNumberOfOrders(currentOrderManMember.getNumberOfOrders());
			orderManMember.setTotalSum(currentOrderManMember.getTotalSum());
			//获取昵称头像
			if(orderManMember.getMobile() != null) {
				User user=new User();
				user.setMobile(orderManMember.getMobile());
				User currentUser = userService.selectUserList(user).stream().findFirst().orElse(null);
				if(currentUser != null) {
					orderManMember.setNickname(currentUser.getNickname());
					orderManMember.setHeadImage(currentUser.getHeadImage());
					if(currentUser.getHeadImage() == null) {
						orderManMember.setHeadImage("http://www.laykj.cn/wherebuy/images/2019/02/14/15/d40c2c26-20bc-4a4d-a012-e62c7ede7d80.png");
					}

				}else {
					orderManMember.setHeadImage("http://www.laykj.cn/wherebuy/images/2019/02/14/15/d40c2c26-20bc-4a4d-a012-e62c7ede7d80.png");
				}
			}

		}
		if("memberInfo".equals(orderManMember.getType())) {
			// 获取会员的订单数、订单总金额
			PromoterOrderMan currentPromoterOrderMan = promoterOrderManService.getStatisVIP(orderManMember.getOrderManId(),orderManMember.getMemberId(),orderManMember.getDate());
			orderManMember.setNumberOfOrders(currentPromoterOrderMan.getNumberOfOrders());
			orderManMember.setTotalSum(currentPromoterOrderMan.getTotalSum());
			// 获取会员昵称头像
			User user = userService.selectUserByuserId(orderManMember.getMemberId());
			orderManMember.setNickname(user.getNickname());
			if(user.getHeadImage() != null) {
				String headImage = user.getHeadImage();
				orderManMember.setHeadImage(headImage);
			}else {
				orderManMember.setHeadImage("http://www.laykj.cn/wherebuy/images/2019/02/14/15/d40c2c26-20bc-4a4d-a012-e62c7ede7d80.png");
			}

		}
		return orderManMember;

	}

	@Override
	public void updateMemberInfo(Date date) {
		List<User> listUser = userMapper.getUserByIsMember();
		OpenMemberInfo openMemberInfo = new OpenMemberInfo();
		for(int i=0;i<listUser.size();i++){
			openMemberInfo.setOpenMemberInfoId(listUser.get(i).getOpenMemberInfoId());
			openMemberInfo = openMemberInfoMapper.getMemberInfo(openMemberInfo).get(0);
			Date endTime = openMemberInfo.getEndTime();
			if(date.compareTo(endTime) != -1){
				userMapper.updateMemberById(openMemberInfo.getUserId());
			}
		}

	}
}
