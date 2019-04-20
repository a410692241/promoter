package com.linayi.service.order.impl;

import com.linayi.dao.order.OrderBoxMapper;
import com.linayi.entity.order.OrderBox;
import com.linayi.service.order.OrderBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderBoxServiceImpl implements OrderBoxService {
	@Autowired
	private OrderBoxMapper orderBoxMapper;


	@Override
	public OrderBox insert(OrderBox orderBox) {
		Date now = new Date();
		orderBox.setBoxTime(now);
		orderBoxMapper.insert(orderBox);
		return orderBox;
	}

}
