package com.linayi.service.delivery.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.linayi.dao.area.SmallCommunityMapper;
import com.linayi.dao.delivery.DeliveryBoxGoodsMapper;
import com.linayi.dao.delivery.DeliveryBoxMapper;
import com.linayi.dao.delivery.DeliveryTaskMapper;
import com.linayi.dao.order.OrdersMapper;
import com.linayi.dao.procurement.ProcurementTaskMapper;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.delivery.DeliveryBox;
import com.linayi.entity.order.OrderBox;
import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.delivery.DeliveryBoxService;
import com.linayi.service.order.OrderBoxService;
import com.linayi.service.procurement.ProcurementService;

@Service
public class DeliveryBoxServiceImpl implements DeliveryBoxService {
	@Autowired
	private DeliveryBoxGoodsMapper deliveryBoxGoodsMapper;
	@Autowired
	private DeliveryBoxMapper deliveryBoxMapper;
	@Autowired
	private DeliveryTaskMapper deliveryTaskMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private SmallCommunityMapper SmallCommunityMapper;
	@Autowired
	private ProcurementService procurementService;
	@Autowired
	private ProcurementTaskMapper procurementTaskMapper;
	@Autowired
	private OrderBoxService orderBoxService;
	@Autowired
	private SmallCommunityMapper smallCommunityMapper;

	/* 插入 */
	@Override
	public DeliveryBox add(DeliveryBox deliveryBox) {
		deliveryBoxMapper.insert(deliveryBox);
		return deliveryBox;
	}

	/* 点击“装箱”按钮：产生一个箱子，产生一个配送任务，订单社区状态改为已装箱 */
	@Override
	@Transactional
	public void boxing(List<ProcurementTask> procurementTaskList) {
		if (procurementTaskList == null) {
			throw new BusinessException(ErrorType.NO_PROCURE_GOODS);
		}
		//添加订单箱
		Date now = new Date();
		OrderBox orderBox = new OrderBox();
		orderBox.setOrdersId(procurementTaskList.get(0).getOrdersId());
		orderBox.setBoxNo(procurementTaskList.get(0).getBoxNo());
		orderBox.setBoxTime(now);
		orderBoxService.insert(orderBox);

		for (ProcurementTask pt : procurementTaskList) {
			//采买任务的实际数量赋值给装箱数量,插入box_no到采买任务
			ProcurementTask param = new ProcurementTask();
			param.setProcurementTaskId(pt.getProcurementTaskId());
			param.setBoxQuantity(pt.getActualQuantity());
			param.setBoxNo(orderBox.getBoxNo());
			procurementTaskMapper.updateProcurementTaskById(param);	
		}
		//根据社区id和订单ID查询小区信息
		SmallCommunity sc =smallCommunityMapper.getSmallCommunitybyCommunityId(procurementTaskList.get(0).getCommunityId(),procurementTaskList.get(0).getOrdersId());
		// 修改订单社区状态为“已装箱”,插入配送员id
		ordersMapper.updateStatusByOrdersId2(sc.getDelivererId(),procurementTaskList.get(0).getOrdersId());
	}

}
