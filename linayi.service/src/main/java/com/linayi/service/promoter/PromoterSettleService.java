package com.linayi.service.promoter;

import com.linayi.dto.PromoterSettleDTO;

import java.util.List;

public interface PromoterSettleService {
    //查询所有推广商列表
    List<PromoterSettleDTO> getAllPromoterDto();
}
