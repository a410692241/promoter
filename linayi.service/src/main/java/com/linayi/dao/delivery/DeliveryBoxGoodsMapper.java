package com.linayi.dao.delivery;

import java.util.List;

import com.linayi.entity.delivery.DeliveryBox;
import com.linayi.entity.delivery.DeliveryBoxGoods;

public interface DeliveryBoxGoodsMapper {
	/**
	 * 通过DeliveryBoxId获取DeliveryBoxGoods
	 * @param DeliveryBoxId
	 * @return DeliveryBoxGoods
	 */
	List<DeliveryBoxGoods> getDeliveryBoxGoods(Long deliveryBoxId);
	
	/**
	 * 插入
	 * @param DeliveryBoxGoods
	 * @return
	 */
	Integer insert(DeliveryBoxGoods DeliveryBoxGoods);
}