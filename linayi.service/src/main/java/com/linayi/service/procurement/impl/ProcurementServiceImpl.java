package com.linayi.service.procurement.impl;


import com.alibaba.fastjson.JSON;
import com.linayi.dao.area.AreaMapper;
import com.linayi.dao.community.CommunityMapper;
import com.linayi.dao.goods.GoodsSkuMapper;
import com.linayi.dao.order.OrdersGoodsMapper;
import com.linayi.dao.order.OrdersMapper;
import com.linayi.dao.procurement.ProcureMergeTaskMapper;
import com.linayi.dao.procurement.ProcurementTaskMapper;
import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.area.Area;
import com.linayi.entity.community.Community;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.order.Orders;
import com.linayi.entity.order.OrdersGoods;
import com.linayi.entity.procurement.ProcureMergeTask;
import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.enums.MemberLevel;
import com.linayi.exception.ErrorType;
import com.linayi.service.community.CommunityService;
import com.linayi.service.community.CommunitySupermarketService;
import com.linayi.service.goods.SupermarketGoodsService;
import com.linayi.service.order.OrderService;
import com.linayi.service.procurement.ProcurementService;
import com.linayi.service.supermarket.SupermarketService;
import com.linayi.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
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
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ProcureMergeTaskMapper procureMergeTaskMapper;
	@Autowired
	private SupermarketGoodsService supermarketGoodsService;

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
	public List<ProcurementTask> getCommunityProcurements(ProcurementTask procurementTask) {

		String procureStatus = procurementTask.getProcureStatus();
		if("PROCURING".equals(procureStatus)){
			getQueryTime(procurementTask);
		}


		List<ProcurementTask> procurementTaskList = procurementTaskMapper.getCommunityProcurementsList(procurementTask);
		if (procurementTaskList != null && procurementTaskList.size() > 0){
			for (ProcurementTask task : procurementTaskList) {
				if(task != null && "PROCURING".equals(procureStatus)){
					if(task.getProcureMergeNo() == null || "".equals(task.getProcureMergeNo())){
						//没有采买任务合并编号
                        ProcurementTask procurementTask1 = new ProcurementTask();
						String procureMergeNo = createProcureMergeNo();
                        procurementTask1.setProcureMergeNo(procureMergeNo);
                        procurementTask1.setProcurementTaskIdList(task.getProcurementTaskIdList());
                        procurementTask1.setUpdateTime(new Date());
						procurementTaskMapper.updateProcureTaskNoById(procurementTask1);
						task.setProcureMergeNo(procureMergeNo);
						ProcureMergeTask procureMergeTask = new ProcureMergeTask();
						procureMergeTask.setStartTime(procurementTask.getCreateTime());
						procureMergeTask.setCreateTime(new Date());
						procureMergeTask.setProcureMergeNo(procureMergeNo);
						procureMergeTaskMapper.insert(procureMergeTask);

                    }
					GoodsSku goods = goodsSkuMapper.getGoodsById(task.getGoodsSkuId());
					if(goods != null){
						task.setGoodsImage(ImageUtil.dealToShow(goods.getImage()));
						task.setBarcode(goods.getBarcode());
					}

				}
			}
		}
		return procurementTaskList;
	}

	//采买任务收货详情
	@Override
	public List<ProcurementTask> getCommunityProcurement(ProcurementTask procurementTask) {
		List<ProcurementTask> procurementTaskList = procurementTaskMapper.getCommunityProcurementList(procurementTask);
		for (ProcurementTask task : procurementTaskList) {
			task.setGoodsImage(ImageUtil.dealToShow(task.getGoodsImage()));
		}
		return procurementTaskList;
	}

	private void getQueryTime(ProcurementTask procurementTask) {
		//根据时间断查询
		//采买时间09:00-13:00  查下单时间09:00及之前的   查询
		boolean nowBetween1 = getNowBetween(9, 13);
		if (nowBetween1) {
			Date fix = getDateFix(null,9);
			procurementTask.setCreateTime(fix);
		}

		//采买时间18:00-09:00  查下单时间18:00及之前的   查询
		boolean nowBetween2 = getNowBetween(18, 33);
		if (nowBetween2) {
			Date now = new Date();
			Calendar cl = Calendar.getInstance();
			cl.setTime(now);
			int hour = cl.get(Calendar.HOUR_OF_DAY);
			int i = hour % 24;
			Date fix;
			if(i >= 0 && i < 9){
				fix = getDateFix(-1,18);
			}else {
				fix = getDateFix(null,18);
			}
			procurementTask.setCreateTime(fix);
		}

		//采买时间13:00-18:00  查下单时间13:00及之前的   查询
		boolean nowBetween3 = getNowBetween(13, 18);
		if (nowBetween3) {
			Date fix = getDateFix(null,13);
			procurementTask.setCreateTime(fix);
		}
	}


	private boolean getNowBetween(int from,int to) {
		Date now = new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(now);
		int hour = cl.get(Calendar.HOUR_OF_DAY);
		if(to == 33){
			if(hour >= from || hour < to % 24){
				return true;
			}
		}else {
			if(hour >= from && hour < to){
				return true;
			}
		}
		return false;
	}

	private Date getDateFix(Integer days,int hours){
		Date now = new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(now);
		if(days != null){
			cl.add(Calendar.HOUR_OF_DAY,days);
		}
		cl.set(Calendar.HOUR_OF_DAY,hours);
		cl.set(Calendar.MINUTE,0);
		cl.set(Calendar.SECOND,0);
		cl.set(Calendar.MILLISECOND,0);
		return cl.getTime();
	}

	@Transactional
	@Override
	public ResponseData updateProcurmentStatus(ProcurementTask procurTask) {
		Integer quantity = procurTask.getQuantity();
		String status = procurTask.getStatus();
		String procurementTaskIdList = procurTask.getProcurementTaskIdList();
		String[] split = procurementTaskIdList.split(",");
		List<ProcurementTask> procurementTaskList = null;
		if (procurementTaskIdList != null){
			procurementTaskList = procurementTaskMapper.getProcurementsList(procurementTaskIdList);
			if (procurementTaskList.size() < split.length){
				return new ResponseData(ErrorType.ORDER_CANCELED);
			}
		}
		Date procureTime = new Date();
		if ("PRICE_HIGH".equals(status)){
			//价高
			if (procurementTaskList != null && procurementTaskList.size() > 0){
				for (ProcurementTask procurementTask : procurementTaskList) {
					procurementTask.setProcureStatus("PRICE_HIGH");
					procurementTask.setUpdateTime(new Date());
					procurementTask.setProcureTime(procureTime);
					procurementTaskMapper.updateProcurementTaskById(procurementTask);
					updateOrders(procurementTask);
				}
			}
		}else if("LACK".equals(status)){
			//缺货
			if (procurementTaskList != null && procurementTaskList.size() > 0){
				for (ProcurementTask procurementTask : procurementTaskList) {
					procurementTask.setProcureTime(procureTime);
					if (procurementTask.getQuantity() <= quantity){
						procurementTask.setProcureStatus("BOUGHT");
						procurementTask.setActualQuantity(procurementTask.getQuantity());
						procurementTask.setProcureQuantity(procurementTask.getQuantity());
					}else if(quantity >0 && quantity < procurementTask.getQuantity()){
						procurementTask.setProcureStatus("LACK");
						procurementTask.setActualQuantity(quantity);
						procurementTask.setProcureQuantity(quantity);
					} else {
						procurementTask.setProcureStatus("LACK");
						procurementTask.setActualQuantity(0);
						procurementTask.setProcureQuantity(0);
					}
					quantity -= procurementTask.getQuantity();

					procurementTask.setUpdateTime(new Date());
					procurementTaskMapper.updateProcurementTaskById(procurementTask);
					updateOrders(procurementTask);
				}
			}
		}else if("BOUGHT".equals(status)){
			//已买
			if (procurementTaskList != null && procurementTaskList.size() > 0){
				for (ProcurementTask procurementTask : procurementTaskList) {
					procurementTask.setProcureTime(procureTime);
					procurementTask.setProcureQuantity(procurementTask.getQuantity());
					procurementTask.setActualQuantity(procurementTask.getQuantity());
					procurementTask.setProcureStatus("BOUGHT");
					procurementTask.setUpdateTime(new Date());
					procurementTaskMapper.updateProcurementTaskById(procurementTask);
					updateOrders(procurementTask);
				}
			}
		}
		return new ResponseData("success");
	}

	private void updateOrders(ProcurementTask procurementTask){
		if(procurementTask.getQuantity() == procurementTask.getActualQuantity()){
			//要采买的数量等于实际采买数量
			OrdersGoods ordersGoods = new OrdersGoods();
			ordersGoods.setOrdersId(procurementTask.getOrdersId());
			ordersGoods.setGoodsSkuId(procurementTask.getGoodsSkuId());
			List<OrdersGoods> ordersGoodsList = ordersGoodsMapper.query(ordersGoods);
			ordersGoods = ordersGoodsList.get(0);
			updateOrdersStatus(procurementTask,ordersGoods);
		}else {
			OrdersGoods ordersGoods = new OrdersGoods();
			ordersGoods.setOrdersId(procurementTask.getOrdersId());
			ordersGoods.setGoodsSkuId(procurementTask.getGoodsSkuId());
			List<OrdersGoods> ordersGoodsList = ordersGoodsMapper.query(ordersGoods);
			ordersGoods = ordersGoodsList.get(0);

			List<SupermarketGoods> supermarketGoodsList = supermarketGoodsService.getSupermarketGoodsList(procurementTask.getGoodsSkuId(), procurementTask.getCommunityId());
			if(supermarketGoodsList != null && supermarketGoodsList.size() > 0){
				MemberPriceUtil.supermarketPriceByLevel(MemberLevel.SUPER,supermarketGoodsList);
				List<SupermarketGoods> allSpermarketGoodsList = MemberPriceUtil.allSpermarketGoodsList;

				ProcurementTask procurementTask1 = new ProcurementTask();
				procurementTask1.setOrdersId(procurementTask.getOrdersId());
				procurementTask1.setOrdersGoodsId(ordersGoods.getOrdersGoodsId());
				List<ProcurementTask> procurementTaskList = procurementTaskMapper.getProcurementTaskList(procurementTask1);
				Set<Integer> collect = procurementTaskList.stream().map(ProcurementTask::getSupermarketId).collect(Collectors.toSet());

				List<SupermarketGoods> supermarketGoods1 = allSpermarketGoodsList.stream().filter(supermarketGoods ->  !collect.contains(supermarketGoods.getSupermarketId())).collect(Collectors.toList());
				Boolean isContinue = true;
				if(supermarketGoods1 != null && supermarketGoods1.size() > 0){
					//逆序排序
//					supermarketGoods1.stream().sorted(Comparator.comparing(SupermarketGoods::getPrice).reversed()).collect(Collectors.toList());
//					for (SupermarketGoods supermarketGoods : supermarketGoods1) {
//						if(supermarketGoods.getPrice() <= procurementTask.getPrice()){
//							isContinue = true;
//							break;
//						}
//					}
					isContinue = false;
				}
				if (isContinue){
					//已经是最后一家
					updateOrdersStatus(procurementTask,ordersGoods);
				}
			}
		}
	}

	/**
	 * 改变订单的状态
	 * @param procurementTask
	 */
	private void updateOrdersStatus(ProcurementTask procurementTask,OrdersGoods ordersGoods) {
		ordersGoods.setUpdateTime(new Date());
		ordersGoods.setProcureStatus("FINISHED");
		ordersGoodsMapper.updateOrdersGoodsById(ordersGoods);

		boolean isFinish = true;
		List<OrdersGoods> ordersGoodsList1 = ordersGoodsMapper.getOrdersGoodsByOrdersId(ordersGoods.getOrdersId());
		for (OrdersGoods orderGoods : ordersGoodsList1) {
			String status = orderGoods.getProcureStatus();
			if (!"FINISHED".equals(status)){
				isFinish = false;
			}
		}

		Orders orders = new Orders();
		orders.setOrdersId(ordersGoods.getOrdersId());

		if (isFinish){
			//订单采买完成
			orders.setCommunityStatus("PROCURE_FINISHED");
			orders.setUpdateTime(new Date());
			ordersMapper.updateOrderById(orders);
		}
		orderService.updateOrderReceivedStatus(ordersGoods.getOrdersId());
	}

	/**
	 *
	 * @param procurementTask
	 * @return
	 */
	@Override
	public ProcurementTask getprocurementDeatil(ProcurementTask procurementTask) {
		GoodsSku goodsSku = goodsSkuMapper.getGoodsById(procurementTask.getGoodsSkuId());
		procurementTask.setImage(goodsSku.getImage());
		procurementTask.setGoodsSkuName(goodsSku.getFullName());
		return procurementTask;
	}

	@Override
	public List<ProcurementTask> getProcurementsByRECEIVEDFLOW(ProcurementTask procurTask) {
		List<ProcurementTask> list = getProcurementTaskListByprocurTask(procurTask);
		return list;
	}

	@Override
	@Transactional
	public void batchDelivery(List<Long> procurementTaskIdList) {
		Date flowOutTime = new Date();
		procurementTaskMapper.confirmDeliverGoods(procurementTaskIdList,flowOutTime);
	}

	/**
	 * 后台管理系统 流转中心
	 * @param procurTask
	 * @return
	 */
	private List<ProcurementTask> getProcurementTaskListByprocurTask(ProcurementTask procurTask){
		if("showAll".equals(procurTask.getReceiveStatus()) || procurTask.getReceiveStatus() == null){
			procurTask.setReceiveStatus("");
		}

		if("one".equals(procurTask.getDeliveryWaveTime())){
			try {
				String str = "2019-01-01 08:00:00";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date StartTime = sdf.parse(str);
				procurTask.setWavePickingStartTime(StartTime);

				str = "2019-01-01 13:00:00";
				Date endTime = sdf.parse(str);
				procurTask.setWavePickingEndTime(endTime);
			}catch (Exception e){

			}
		}
		if("two".equals(procurTask.getDeliveryWaveTime())){
			try {
				String str = "2019-01-01 13:00:00";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date StartTime = sdf.parse(str);
				procurTask.setWavePickingStartTime(StartTime);

				str = "2019-01-01 18:00:00";
				Date endTime = sdf.parse(str);
				procurTask.setWavePickingEndTime(endTime);
			}catch (Exception e){

			}
		}
		if("three".equals(procurTask.getDeliveryWaveTime())){
			try {
				String str = "2019-01-01 18:00:00";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date StartTime = sdf.parse(str);
				procurTask.setWavePickingStartTime(StartTime);

				str = "2019-01-01 23:59:59";
				Date endTime = sdf.parse(str);
				procurTask.setWavePickingEndTime(endTime);

				str = "2019-01-01 00:00:00";
				StartTime = sdf.parse(str);
				str = "2019-01-01 08:00:00";
				endTime = sdf.parse(str);
				procurTask.setWavePickingEndTimeTo(endTime);
				procurTask.setWavePickingStartTimeTo(StartTime);
			}catch (Exception e){

			}
		}
		List<ProcurementTask> list  = procurementTaskMapper.getProcurementsByRECEIVEDFLOW(procurTask);
		Calendar cal =Calendar.getInstance();
		for(int i=0;i<list.size();i++){
			if("PROCURING".equals(list.get(i).getProcureStatus()) && "WAIT_OUT".equals(list.get(i).getReceiveStatus())){
				list.get(i).setReceiveStatus("采买中");
			}else if("WAIT_OUT".equals(list.get(i).getReceiveStatus()) && list.get(i).getActualQuantity()>0){
				list.get(i).setReceiveStatus("已采买");
			}else if("OUTED".equals(list.get(i).getReceiveStatus())){
				list.get(i).setReceiveStatus("已提货");
			}else if("RECEIVED_FLOW".equals(list.get(i).getReceiveStatus())){
				list.get(i).setReceiveStatus("待发货");
			}else if("WAIT_RECEIVE".equals(list.get(i).getReceiveStatus())){
				list.get(i).setReceiveStatus("已发货");
			}else if("RECEIVED".equals(list.get(i).getReceiveStatus())){
				list.get(i).setReceiveStatus("已发货");
			}
			Date date = list.get(i).getCreateTime();
			cal.setTime(date);
			Integer hour = cal.get(Calendar.HOUR_OF_DAY);
			if(8<=hour && hour<13){
				list.get(i).setDeliveryWaveTime("第一波次");
			}else if(13<=hour && hour<18){
				list.get(i).setDeliveryWaveTime("第二波次");
			}else{
				list.get(i).setDeliveryWaveTime("第三波次");
			}
		}
		return list;
	}

	@Override
	public void updateProcurReceiveStatus(ProcurementTask procurementTask) {
		String procurementTaskIdList = procurementTask.getProcurementTaskIdList();
		List<ProcurementTask> procurementTaskList = null;
		if (procurementTaskIdList != null){
			procurementTaskList = procurementTaskMapper.getProcurementsList(procurementTaskIdList);
		}
		Date date = new Date();
		if (procurementTaskList != null && procurementTaskList.size() > 0){
			for (ProcurementTask task : procurementTaskList) {
				Integer procureQuantity = task.getProcureQuantity();
				if (procureQuantity != null && procureQuantity > 0){
					task.setReceiveStatus("OUTED");
					task.setProcureOutTime(date);
					procurementTaskMapper.updateProcurementTaskById(task);
				}
			}
		}

	}

	@Override
	public ProcurementTask getProcurementById(Long procurementTaskId) {
		ProcurementTask procurementTask = procurementTaskMapper.getProcurementById(procurementTaskId);

		procurementTask.setGoodsSkuName(goodsSkuMapper.getGoodsById(procurementTask.getGoodsSkuId()).getName());

		procurementTask.setImage(goodsSkuMapper.getGoodsById(procurementTask.getGoodsSkuId()).getImage());

		procurementTask.setServiceFeeString(10);

		procurementTask.setSupermarketName(supermarketMapper.selectSupermarketBysupermarketId(procurementTask.getSupermarketId()).getName());

		//procurementTask.setBuyUserName(userMapper.selectUserByuserId(procurementTask.getUserId()).getRealName()); //采买员姓名
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
		for (ProcurementTask task : procurementTaskList) {
			task.setSupermarketName(supermarketMapper.selectSupermarketBysupermarketId(task.getSupermarketId()).getName());
		}
		Collections.reverse(procurementTaskList);


		List<SupermarketGoods> supermarketGoodsList = supermarketGoodsService.getSupermarketGoodsList(procurement.getGoodsSkuId(), procurement.getCommunityId());
		MemberPriceUtil.supermarketPriceByLevel(MemberLevel.SUPER,supermarketGoodsList);
		ModelAndView mv = new ModelAndView("jsp/order/OrderDetail");
		ProcurementTask procurementTask2 = new ProcurementTask();
		procurementTask2.setOrdersId(procurement.getOrdersId());
		procurementTask2.setOrdersGoodsId(procurement.getOrdersGoodsId());
		List<ProcurementTask> data = procurementTaskMapper.getProcurementTaskList(procurementTask2);

		List<Integer> collect = data.stream().map(p -> p.getSupermarketId()).collect(Collectors.toList());
		List<SupermarketGoods> allSpermarketGoodsList = MemberPriceUtil.allSpermarketGoodsList;
		List<SupermarketGoods> supermarketGoodsList1 = allSpermarketGoodsList.stream().filter(supermarket -> !collect.contains(supermarket.getSupermarketId())).sorted(Comparator.comparing(SupermarketGoods::getPrice)).collect(Collectors.toList());


		for (SupermarketGoods supermarketGoods : supermarketGoodsList1) {
			Supermarket supermarket = supermarketMapper.selectSupermarketBysupermarketId(supermarketGoods.getSupermarketId());
			ProcurementTask procurementTask1 = new ProcurementTask();
			procurementTask1.setSupermarketName(supermarket.getName());
			procurementTask1.setPrice(supermarketGoods.getPrice());
			procurementTask1.setProcureQuantity(0);
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
			String image = pp.getGoodsImage();
			pp.setGoodsImage(ImageUtil.dealToShow(image));
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
		if ("PROCURING".equals(procureStatus) && !"PROCURING".equals(procurementTask2.getProcureStatus())){
			procurementTask2.setProcureStatus("FINISHED");
		}else {
			procurementTask2.setOrdersId(null);
		}
		return procurementTask2;
	}

	@Override
	public void updateOrderStatus(ProcurementTask procurementTask) {

		procurementTask.setReceiveStatus("RECEIVED");
		if (procurementTask.getProcurementTaskIdList().contains(",")){
			String [] str =	procurementTask.getProcurementTaskIdList().split(",");
			for (String s : str) {
				procurementTask.setProcurementTaskId(Long .valueOf(s));
				procurementTaskMapper.updateProcurementTaskById(procurementTask);
				//根据任务id获取采买任务表信息
				ProcurementTask procurementTasks = procurementTaskMapper.selectByPrimaryKey(Long .valueOf(s));
				//修改订单表状态
				orderService.updateOrderReceivedStatus(procurementTasks.getOrdersId());
			}

		}else{
			procurementTask.setProcurementTaskId(Long .valueOf(procurementTask.getProcurementTaskIdList()));
			//将采买任务表改成已收货
			procurementTaskMapper.updateProcurementTaskById(procurementTask);

			ProcurementTask procurementTasks = procurementTaskMapper.selectByPrimaryKey(Long .valueOf(procurementTask.getProcurementTaskIdList()));
			//修改订单表状态
			orderService.updateOrderReceivedStatus(procurementTasks.getOrdersId());
		}

	}



	@Override
	public List<ProcurementTask> getNotReceivingGoods(ProcurementTask procurTask) {
		procurTask.setReceiveStatus("OUTED");
		List<ProcurementTask> list = procurementTaskMapper.getNotReceivingGoods(procurTask);
		for(int i=0;i<list.size();i++){
			String image = list.get(i).getImage();
			list.get(i).setImage(ImageUtil.dealToShow(image));
			list.get(i).setAccessTime(new Date());
		}
		return list;
	}
	@Override
	@Transactional
	public void confirmGoods(String procurementTaskIdList) {
		List<Long> list = new ArrayList<>();
		List<String> gameids = Arrays.asList(procurementTaskIdList.split(","));
		for(int i=0;i<gameids.size();i++){
			list.add(Long.valueOf(gameids.get(i)));
		}
		Date flowReceiveTime = new Date();
		procurementTaskMapper.confirmGoods(list,flowReceiveTime);
	}
	@Override
	public List<ProcurementTask> getNotDeliverGoods(ProcurementTask procurTask) {
		procurTask.setReceiveStatus("RECEIVED_FLOW");
		List<ProcurementTask> list = procurementTaskMapper.getNotDeliverGoods(procurTask);
		for(int i=0;i<list.size();i++){
			String image = list.get(i).getImage();
			list.get(i).setImage(ImageUtil.dealToShow(image));
		}
		return list;
	}
	@Override
	@Transactional
	public void confirmDeliverGoods(String procurementTaskIdList) {
		List<Long> list = new ArrayList<>();
		List<String> gameids = Arrays.asList(procurementTaskIdList.split(","));
		for(int i=0;i<gameids.size();i++){
			list.add(Long.valueOf(gameids.get(i)));
		}
		Date flowOutTime = new Date();
		procurementTaskMapper.confirmDeliverGoods(list,flowOutTime);
	}
	@Override
	public List<ProcurementTask> getDeliverGoodsList(ProcurementTask procurTask) {
		procurTask.setReceiveStatus("WAIT_RECEIVE");
		List<ProcurementTask> list = procurementTaskMapper.getDeliverGoodsList(procurTask);
		for(int i=0;i<list.size();i++){
			String image = list.get(i).getImage();
			list.get(i).setImage(ImageUtil.dealToShow(image));
		}
		return list;
	}

	@Override
	public void exportData(ProcurementTask procurementTask, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 只是让浏览器知道要保存为什么文件而已，真正的文件还是在流里面的数据，你设定一个下载类型并不会去改变流里的内容。
		//而实际上只要你的内容正确，文件后缀名之类可以随便改，就算你指定是下载excel文件，下载时我也可以把他改成pdf等。
		response.setContentType("application/vnd.ms-excel");
		// 传递中文参数编码
		String codedFileName = java.net.URLEncoder.encode("分拣商品信息","UTF-8");
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
		List<ProcurementTask> list = getProcurementTaskListByprocurTask(procurementTask);
		// 定义一个工作薄
		Workbook workbook = new HSSFWorkbook();
		// 创建一个sheet页
		Sheet sheet = workbook.createSheet("分拣商品信息");
		// 创建一行
		Row row = sheet.createRow(0);
		// 在本行赋值 以0开始

		row.createCell(0).setCellValue("采买任务ID");
		row.createCell(1).setCellValue("网点名称");
		row.createCell(2).setCellValue("配送波次");
		row.createCell(3).setCellValue("顾客订单号");
		row.createCell(4).setCellValue("订单状态");
		row.createCell(5).setCellValue("商品名称");
		row.createCell(6).setCellValue("商品条码");
		row.createCell(7).setCellValue("顾客下单数量");
		row.createCell(8).setCellValue("实际采买数量");
		row.createCell(9).setCellValue("商品单价(元)");
		row.createCell(10).setCellValue("采买超市");
		row.createCell(11).setCellValue("下单时间");
		// 定义样式
		CellStyle cellStyle = workbook.createCellStyle();
		// 格式化日期
		//cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd"));
		String pattern = "yyyy-MM-dd HH:mm:ss";
		// 遍历输出
		for (int i = 1; i <= list.size(); i++) {
			ProcurementTask procurTask = list.get(i - 1);
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(procurTask.getProcurementTaskId());
			row.createCell(1).setCellValue(procurTask.getCommunityName());
			row.createCell(2).setCellValue(procurTask.getDeliveryWaveTime());
			row.createCell(3).setCellValue(procurTask.getOrdersId());
			row.createCell(4).setCellValue(procurTask.getReceiveStatus());
			row.createCell(5).setCellValue(procurTask.getFullName());
			row.createCell(6).setCellValue(procurTask.getBarcode());
			row.createCell(7).setCellValue(procurTask.getQuantity());
			row.createCell(8).setCellValue(procurTask.getActualQuantity());
			row.createCell(9).setCellValue(procurTask.getPrice()/100.00);
			row.createCell(10).setCellValue(procurTask.getSupermarketName());
			row.createCell(11).setCellValue(DateUtil.date2String(procurTask.getCreateTime(),pattern));
		}
		OutputStream fOut = response.getOutputStream();
		workbook.write(fOut);
		fOut.flush();
		fOut.close();
	}

	@Override
	public List<ProcurementTask> getProcurements(ProcurementTask procurementTask) {
		return procurementTaskMapper.getProcurementTaskList(procurementTask);
	}

    @Override
	public String createProcureMergeNo(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		int day = c.get(Calendar.DAY_OF_YEAR);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
//		int millisecond = c.get(Calendar.MILLISECOND);
		String dayFmt = String.format("%1$03d", day);
        String hourFmt = String.format("%1$02d", hour);
        String minuteFmt = String.format("%1$02d", minute);
        String secondFmt = String.format("%1$02d", second);
//        String millisecondFmt = String.format("%1$03d", millisecond);
        String prefix = (year - 2000) + dayFmt + hourFmt + minuteFmt + secondFmt;
        long seq = redisUtil.incr(prefix, 1);
        if (seq == 1){
            redisUtil.expire(prefix,3);
        }
        String formatSeq = String.format("%1$02d", seq);
        return (year - 2000) + dayFmt + hourFmt + minuteFmt + formatSeq + secondFmt;
	}

	@Transactional
	@Override
	public void packingSkuGoods(String procureMergeNo, Integer quantity,String box_no) {
		//需要修改查询采买任务列表的sql语句  条件改成pt.box_status == 'WAIT_BOX'
		ProcurementTask procurementTask = new ProcurementTask();
		procurementTask.setProcureMergeNo(procureMergeNo);
		procurementTask.setCreateTime(new Date());
		List<ProcurementTask> procurementTaskList = procurementTaskMapper.getProcurementTaskList(procurementTask);
		if (Optional.ofNullable(procurementTaskList).isPresent()) {
			for (ProcurementTask task : procurementTaskList) {
					if(quantity >= task.getProcureQuantity()){
						task.setBoxQuantity(task.getProcureQuantity());
						task.setBoxNo(box_no);
						quantity -= task.getProcureQuantity();
					}else if(quantity > 0){
						task.setBoxQuantity(quantity);
						task.setBoxNo(box_no);
						ProcurementTask procurementTask1 = new ProcurementTask();
						BeanUtils.copyProperties(task,procurementTask1);
						procurementTask1.setBoxNo(null);
						procurementTask1.setBoxQuantity(null);
						procurementTask1.setProcurementTaskId(null);
						procurementTaskMapper.insert(procurementTask1);
					}
				task.setBoxStatus("BOXED");
				procurementTaskMapper.updateProcurementTaskById(task);

				ProcurementTask procurementTask1 = new ProcurementTask();
				procurementTask1.setOrdersId(task.getOrdersId());
				List<ProcurementTask> procurementTaskList1 = procurementTaskMapper.getProcurementTaskList(procurementTask1);
				Boolean isFinished = true;
				if(Optional.ofNullable(procurementTaskList1).isPresent()){
					for (ProcurementTask procurementTask2 : procurementTaskList1) {
						String boxStatus = procurementTask2.getBoxStatus();
						if(!StringUtils.equals("BOXED",boxStatus)){
							isFinished = false;
						}
					}
				}
				if(isFinished){
					//订单全部装箱完修改订单状态
					Orders orders = new Orders();
					orders.setOrdersId(task.getOrdersId());
//					orders.setCommunityStatus();
					ordersMapper.updateOrderById(orders);
				}
			}
		}
	}

}
