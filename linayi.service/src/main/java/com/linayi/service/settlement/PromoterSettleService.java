package com.linayi.service.settlement;

import com.linayi.dto.PromoterSettleDTO;
import com.linayi.entity.order.Orders;

import java.util.List;

public interface PromoterSettleService {
    //查询所有推广商列表
    List<PromoterSettleDTO> getAllPromoterDto(Orders orders);
}
