package com.linayi.dao.order;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.wp.usermodel.Paragraph;

import com.linayi.entity.order.OrderBox;

public interface OrderBoxMapper {
    
    /**
     * 通过订单id获取OrderBox
     * @param ordersId
     * @return
     */
    List<OrderBox> getOrderBoxList(Long ordersId);
    
    
    /**
     *	 插入
     * @param orderBox
     * @return
     */
    Integer insert(OrderBox orderBox);
    /**
     * 通过订单Id修改封箱图片
     * @param ordersId
     * @return
     */
    Integer updateImage(OrderBox orderBox);
}