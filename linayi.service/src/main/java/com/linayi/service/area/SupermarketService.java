package com.linayi.service.area;

import java.util.List;

import com.linayi.entity.supermarket.Supermarket;
import com.linayi.entity.supermarket.SupermarketFullName;
import com.linayi.exception.ErrorType;
import com.linayi.util.PageResult;
import com.linayi.vo.promoter.PromoterVo;

public interface SupermarketService {

	List<Supermarket> getSupermarketByAreaCode(String areaCode);

    /**模糊搜索超市名获取超市list
     * @param search
     * @return
     */
    PageResult<SupermarketFullName> getSupermarketByKey(PromoterVo.SearchSmallCommunityByKey search);

    Supermarket getSupermarketByProcureId(Integer userId);
}
