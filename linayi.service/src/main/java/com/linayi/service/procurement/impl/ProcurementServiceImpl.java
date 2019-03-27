package com.linayi.service.procurement.impl;


import java.util.*;

import com.linayi.dao.area.AreaMapper;
import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.area.Area;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.service.community.CommunityService;
import com.linayi.service.community.CommunitySupermarketService;
import com.linayi.service.order.OrderService;
import com.linayi.service.order.OrdersGoodsService;
import com.linayi.service.order.impl.OrderServiceImpl;
import com.linayi.service.supermarket.SupermarketService;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.linayi.dao.community.CommunityMapper;
import com.linayi.dao.goods.GoodsSkuMapper;
import com.linayi.dao.order.OrdersGoodsMapper;
import com.linayi.dao.order.OrdersMapper;
import com.linayi.dao.procurement.ProcurementTaskMapper;
import com.linayi.entity.community.Community;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.order.Orders;
import com.linayi.entity.order.OrdersGoods;
import com.linayi.util.ImageUtil;
import com.linayi.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.service.procurement.ProcurementService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcurementServiceImpl implements ProcurementService {
	@Autowired
	private ProcurementTaskMapper procurementTaskMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private SupermarketMapper supermarketMapper;
	@Autowired
	private AreaMapper areaMapper;
	@Autowired
	private OrderService orderService;
	@Autowired
	private GoodsSkuMapper goodsSkuMapper;
	@Autowired
	private SupermarketService supermarketService;
	@Autowired
	private CommunityMapper communityMapper;
	@Autowired
	private OrdersGoodsMapper ordersGoodsMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private CommunitySupermarketService communitySupermarketService;
	@Autowired
	private CommunityService communityService;

	@Override
	public void addProcurementTask(ProcurementTask task) {

	}

	@Override
	public List<ProcurementTask> getProcurementTaskStatus(ProcurementTask procurementTask) {



		List<ProcurementTask> procurementTaskList = procurementTaskMapper.getProcurementTaskStatus(procurementTask);
		if(procurementTaskList.size()>0) {
			for(ProcurementTask i:procurementTaskList) {
				i.setBuyUserName(userMapper.selectUserByuserId(i.getUserId()).getRealName()); //真实姓名
				i.setSupermarketName(supermarketMapper.selectSupermarketBysupermarketId(i.getSupermarketId()).getName());//超市名
			}
		}

		return procurementTaskList;
	}

	@Override
	public List<Community> getProcurementCommunity(Integer userId) {
		Supermarket supermarket = supermarketService.getSupermarketByUserId(userId);
		List<Community> communities = null;
		List<Integer> communityIdBysupermarketId = communitySupermarketService.getCommunityIdBysupermarketId(supermarket.getSupermarketId());
		if (communityIdBysupermarketId != null && communityIdBysupermarketId.size() > 0){
			communities = new ArrayList<>();
			for (Integer communityId : communityIdBysupermarketId) {
				Community community = communityService.getCommunityById(communityId);
				communities.add(community);
			}
		}
		return communities;
	}

	@Override
	public List<ProcurementTask> getCommunityProcurement(Integer userId, String procureStatus) {
		Integer communityId = supermarketMapper.getSupermarketCommunityId(userId);
		List<ProcurementTask> procurementTaskList = procurementTaskMapper.getCommunityProcurementList(communityId, procureStatus);
		/*if (procurementTaskList != null && procurementTaskList.size() > 0){
			for (ProcurementTask procurementTask : procurementTaskList) {
				procurementTask.setCommunityId(communityId);
			}
		}*/
		return procurementTaskList;
	}

	@Transactional
	@Override
	public void updateProcurmentStatus(Integer goodsSkuId, Integer quantity, Integer userId) {
		Integer communityId = supermarketMapper.getSupermarketCommunityId(userId);
		List<ProcurementTask> procurementTaskList = procurementTaskMapper.getProcurements(goodsSkuId,communityId);
		if (quantity == null){
			//价高
			if (procurementTaskList != null && procurementTaskList.size() > 0){
				for (ProcurementTask procurementTask : procurementTaskList) {
					procurementTask.setProcureStatus("PRICE_HIGH");
					procurementTask.setUpdateTime(new Date());
					procurementTaskMapper.updateProcurementTaskById(procurementTask);
				}
			}
		}else if(quantity > 0){
			//缺货


			if (procurementTaskList != null && procurementTaskList.size() > 0){
				for (ProcurementTask procurementTask : procurementTaskList) {
					if (procurementTask.getQuantity() <= quantity){
						procurementTask.setProcureStatus("BOUGHT");
						quantity -= procurementTask.getQuantity();
					}else {
						procurementTask.setProcureStatus("LACK");
					}
					procurementTask.setUpdateTime(new Date());
					procurementTaskMapper.updateProcurementTaskById(procurementTask);
				}
			}
		}else if(quantity == 0){
			//已买
			if (procurementTaskList != null && procurementTaskList.size() > 0){
				for (ProcurementTask procurementTask : procurementTaskList) {
					procurementTask.setProcureStatus("BOUGHT");
					procurementTask.setUpdateTime(new Date());
					procurementTaskMapper.updateProcurementTaskById(procurementTask);
				}
			}
		}
	}

	@Override
	public ProcurementTask getProcurementById(Long procurementTaskId) {
		ProcurementTask procurementTask = procurementTaskMapper.getProcurementById(procurementTaskId);

		procurementTask.setGoodsSkuName(goodsSkuMapper.getGoodsById(procurementTask.getGoodsSkuId()).getName());

		procurementTask.setImage(ImageUtil.dealToShow(goodsSkuMapper.getGoodsById(procurementTask.getGoodsSkuId()).getImage()));

		procurementTask.setServiceFeeString(10);

		procurementTask.setSupermarketName(supermarketMapper.selectSupermarketBysupermarketId(procurementTask.getSupermarketId()).getName());

		procurementTask.setBuyUserName(userMapper.selectUserByuserId(procurementTask.getUserId()).getRealName()); //采买员姓名
		if (procurementTask.getActualQuantity() != null && procurementTask.getPrice() != null){
			procurementTask.setTotalPrice(procurementTask.getActualQuantity() * procurementTask.getPrice()+"");
		}


		return procurementTask;
	}

	@Override
	public List<ProcurementTask> showProcurementTaskAndOrderGoods(ProcurementTask procurementTask){
		ProcurementTask procurement = getProcurementById(procurementTask.getProcurementTaskId());
		procurementTask.setOrdersGoodsId(procurement.getOrdersGoodsId());
		procurementTask.setProcurementTaskId(null);
		List<ProcurementTask> procurementTaskList = procurementTaskMapper.getProcurementTaskList(procurementTask);
		Integer supermarketId = procurementTaskList.get(0).getSupermarketId();
		for (ProcurementTask task : procurementTaskList) {
			task.setSupermarketName(supermarketMapper.selectSupermarketBysupermarketId(task.getSupermarketId()).getName());
		}
		Collections.reverse(procurementTaskList);
		OrdersGoods ordersGoods = new OrdersGoods();
		ordersGoods.setOrdersGoodsId(procurementTask.getOrdersGoodsId());
		List<OrdersGoods> ordersGoodsList = ordersGoodsMapper.getOrdersGoodsByOrdersGoods(ordersGoods);
		OrdersGoods ordersGoods1 = ordersGoodsList.get(0);
		String sL = ordersGoods1.getSupermarketList();
		List<Map> list = JSON.parseArray(sL, Map.class);
		Map map = list.stream().filter(item -> item.get("supermarket_id") == supermarketId).collect(Collectors.toList()).stream().findFirst().orElse(null);
		int i = list.indexOf(map);
		for (int i1 = i + 1; i1 < list.size(); i1++) {
			Map map1 = list.get(i1);
			Supermarket supermarket = supermarketMapper.selectSupermarketBysupermarketId(Integer.parseInt(map1.get("supermarket_id") + ""));
			ProcurementTask procurementTask1 = new ProcurementTask();
			procurementTask1.setSupermarketName(supermarket.getName());
			procurementTask1.setPrice(Integer.parseInt(map1.get("price") + ""));
			procurementTaskList.add(procurementTask1);
		}
		return procurementTaskList;
	}

	@Override
	public Object getProcurementList(ProcurementTask procurementTask) {
		List<ProcurementTask> procurementList = procurementTaskMapper.getProcurementList(procurementTask);
		for (ProcurementTask procurement : procurementList) {
			procurement.setTotal(procurement.getPrice() * procurement.getQuantity());
		}
		PageResult<ProcurementTask> procurementTaskPageResult = new PageResult<>(procurementList,procurementTask);
		return procurementTaskPageResult;
	}

	@Override
	public ProcurementTask getProcurementTask(ProcurementTask procurementTask) {
		ProcurementTask procurementTask1 = procurementTaskMapper.selectByPrimaryKey(procurementTask.getProcurementTaskId());
		GoodsSku goodsSku = goodsSkuMapper.getGoodsById(procurementTask1.getGoodsSkuId());
		Orders orders = ordersMapper.getOrderById(procurementTask1.getOrdersId());
		Integer communityId = orders.getCommunityId();
		Community community = new Community();
		community.setCommunityId(communityId);
		Community community1 = communityMapper.getCommunity(community);
		procurementTask1.setGoodsSkuName(goodsSku.getFullName());
		procurementTask1.setCommunityName(community1.getName());
		procurementTask1.setImage(ImageUtil.dealToShow(goodsSku.getImage()));
		procurementTask1.setTotalPrice(getpriceString(procurementTask1.getQuantity() * procurementTask1.getPrice()));
		return procurementTask1;
	}

	@Transactional
	@Override
	public void updateProcurement(ProcurementTask procurementTask) {
		ProcurementTask procurementTask1 = procurementTaskMapper.getProcurementById(procurementTask.getProcurementTaskId());
		if ("BOUGHT".equals(procurementTask.getProcureStatus())){
			procurementTask.setActualQuantity(procurementTask1.getQuantity());
			procurementTask.setProcureQuantity(procurementTask1.getProcureQuantity());
		}
		procurementTaskMapper.updateProcurementTaskById(procurementTask);

		OrdersGoods ordersGoods = new OrdersGoods();
		ordersGoods.setOrdersGoodsId(procurementTask1.getOrdersGoodsId());
		List<OrdersGoods> ordersGoodsList = ordersGoodsMapper.getOrdersGoodsByOrdersGoods(ordersGoods);
		OrdersGoods ordersGoods1 = ordersGoodsList.get(0);
		List<Map> list = JSON.parseArray(ordersGoods1.getSupermarketList(), Map.class);
		Integer supermarketId = procurementTask1.getSupermarketId();
		Map map = list.stream().filter(item -> item.get("supermarket_id") == supermarketId).collect(Collectors.toList()).stream().findFirst().orElse(null);
		int i = list.indexOf(map);
		if (i == list.size() - 1 || procurementTask.getProcureStatus().equals("BOUGHT")){
			//采买任务已完成
			ordersGoods1.setProcureStatus("FINISHED");
			ordersGoodsMapper.updateOrdersGoodsById(ordersGoods1);
		}

		boolean isFinish = true;
		List<OrdersGoods> ordersGoodsList1 = ordersGoodsMapper.getOrdersGoodsByOrdersId(ordersGoods1.getOrdersId());
		for (OrdersGoods orderGoods : ordersGoodsList1) {
			String status = orderGoods.getProcureStatus();
			if (!"FINISHED".equals(status)){
				isFinish = false;
			}
		}

		Orders orders = new Orders();
		orders.setOrdersId(ordersGoods1.getOrdersId());

		if (isFinish){
			//订单采买完成
			orders.setCommunityStatus("PROCURE_FINISHED");
			ordersMapper.updateOrderById(orders);
		}

		orderService.updateOrderReceivedStatus(ordersGoods1.getOrdersId());

	}

	/**
	 * 分转元为单位
	 *
	 * @param Price
	 * @return
	 */
	public String getpriceString(Integer Price) {
		StringBuffer sb = new StringBuffer(Price + "");
		if (sb.length() >= 3) {
			sb.insert(sb.toString().length() - 2, ".");
		} else if (sb.length() == 2) {
			return "0." + sb.toString();
		} else if (sb.length() == 1) {
			return "0.0" + sb.toString();
		}
		return sb.toString();
	}


	@Override
	public List<ProcurementTask> getProcurementTaskByOrderIdAndActualQuantity(ProcurementTask procurementTask) {

		//获取订单数据
		Orders orders1 = ordersMapper.getOrderById(procurementTask.getOrdersId());
		//设置收货地址
		String areaCode = orders1.getAddressOne();
		String addressTwo = orders1.getAddressTwo();
		String addressThree = orders1.getAddressThree();
		Area area = new Area();
		String areaName = "";
		while (true) {
			area.setCode(areaCode);
			Area areaInfo = areaMapper.getAreaInfo(area);
			areaName = areaInfo.getName() + areaName;
			if (areaInfo.getParent().equals("1000")) {
				break;
			}
			areaCode = areaInfo.getParent();
		}
		List<ProcurementTask> currentProcurementTask = procurementTaskMapper.getProcurementTaskByOrderIdAndActualQuantity(procurementTask);
		//拼接的完整地址放进集合第一个对象中
		currentProcurementTask.get(0).setReceiverAddress(areaName + addressTwo + addressThree);

		//图片处理
		for(ProcurementTask pp:currentProcurementTask){
			String image = ImageUtil.dealToShow(pp.getGoodsImage());
			pp.setGoodsImage(image);
		}

		return currentProcurementTask;
	}

	@Override
	public void updateOrdersGoodsStatus(ProcurementTask procurementTask) {
		ProcurementTask procurementTask1 = procurementTaskMapper.getProcurementById(procurementTask.getProcurementTaskId());
		OrdersGoods ordersGoods = new OrdersGoods();
		ordersGoods.setOrdersGoodsId(procurementTask1.getOrdersGoodsId());
		List<OrdersGoods> ordersGoodsList = ordersGoodsMapper.getOrdersGoodsByOrdersGoods(ordersGoods);
		OrdersGoods ordersGoods1 = ordersGoodsList.get(0);
		ordersGoods1.setProcureStatus("FINISHED");
		ordersGoodsMapper.updateOrdersGoodsById(ordersGoods1);

		boolean isFinish = true;
		List<OrdersGoods> ordersGoodsList1 = ordersGoodsMapper.getOrdersGoodsByOrdersId(ordersGoods1.getOrdersId());
		for (OrdersGoods orderGoods : ordersGoodsList1) {
			String status = orderGoods.getProcureStatus();
			if (!"FINISHED".equals(status)){
				isFinish = false;
			}
		}

		Orders orders = new Orders();
		orders.setOrdersId(ordersGoods1.getOrdersId());

		if (isFinish){
			//订单采买完成
			orders.setCommunityStatus("PROCURE_FINISHED");
			ordersMapper.updateOrderById(orders);
		}
		orderService.updateOrderReceivedStatus(ordersGoods1.getOrdersId());
	}

	@Override
	public ProcurementTask getProcurementTaskLast(ProcurementTask procurementTask) {
		ProcurementTask procurement = procurementTaskMapper.getProcurementById(procurementTask.getProcurementTaskId());
		ProcurementTask procurementTask1 = new ProcurementTask();
		procurementTask1.setOrdersGoodsId(procurement.getOrdersGoodsId());
		List<ProcurementTask> procurementTaskList = procurementTaskMapper.getProcurementTaskListAsc(procurementTask1);
		ProcurementTask procurementTask2 = procurementTaskList.get(procurementTaskList.size() - 1);
		OrdersGoods ordersGoods = new OrdersGoods();
		ordersGoods.setOrdersGoodsId(procurementTask2.getOrdersGoodsId());
		List<OrdersGoods> ordersGoodsList = ordersGoodsMapper.getOrdersGoodsByOrdersGoods(ordersGoods);
		String procureStatus = ordersGoodsList.get(0).getProcureStatus();
		if ("FINISHED".equals(procureStatus)){
			procurementTask2.setProcureStatus("PROCURING");
		}
		return procurementTask2;
	}

	@Override
	public Integer updateOrderStatus(ProcurementTask procurementTask) {
		//根据procurementTaskId查询任务列表
		ProcurementTask newProcurementTask = procurementTaskMapper.getProcurementById(procurementTask.getProcurementTaskId());

		procurementTask.setProcureQuantity(newProcurementTask.getActualQuantity());

		procurementTask.setReceiveStatus("RECEIVED");
		//将采买任务表改成已收货
		Integer order = procurementTaskMapper.updateProcurementTaskById(procurementTask);
		Long ordersId = procurementTaskMapper.getProcurementById(procurementTask.getProcurementTaskId()).getOrdersId();
		//更新订单状态为已完成
		orderService.updateOrderReceivedStatus(ordersId);
		return order;
	}


}
