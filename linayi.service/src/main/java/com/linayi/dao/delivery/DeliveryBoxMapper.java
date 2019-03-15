package com.linayi.dao.delivery;

import com.linayi.entity.delivery.DeliveryBox;

public interface DeliveryBoxMapper {
	/**
	 * 通过配送箱ID获取配送箱信息
	 * @param deliveryBoxId 配送箱ID
	 * @return 配送箱信息
	 */
	DeliveryBox getDeliveryBox(Long deliveryBoxId);

	/**
	 * 插入
	 * @param deliveryBox
	 * @return
	 */
	Integer insert(DeliveryBox deliveryBox);
	/**
	 * 通过配送箱ID插入封箱图片
	 * @param deliveryBox
	 * @return
	 */
	Integer updateImageById(DeliveryBox deliveryBox);
}