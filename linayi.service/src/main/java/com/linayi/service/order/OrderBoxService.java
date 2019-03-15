package com.linayi.service.order;

import org.springframework.web.multipart.MultipartFile;

import com.linayi.entity.order.OrderBox;

public interface OrderBoxService {
	/**
     * *插入 
     * @param orderBox
     * @return
     */
	OrderBox insert(OrderBox orderBox);
}
