package com.linayi.dao.promoter;

import com.linayi.dto.PromoterSettleDTO;
import com.linayi.entity.order.Orders;
import com.linayi.entity.promoter.Promoter;

import java.util.List;

public interface PromoterMapper {
    int insert(Promoter record);

    /**
     * 获取所有的推广商
     *
     * @return
     */
    List<PromoterSettleDTO> getAllPromoter(Orders orders);

    /**
     * 通过id获取推广商信息
     * @param promoterId
     * @return
     */
    Promoter getPromoterById(Integer promoterId);
}
