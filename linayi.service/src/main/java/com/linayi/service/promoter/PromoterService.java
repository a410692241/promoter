package com.linayi.service.promoter;

import java.util.List;

import com.linayi.entity.promoter.Promoter;

public interface PromoterService {

	List<Promoter> getPromoterListByType(Promoter promoter);
    
}
