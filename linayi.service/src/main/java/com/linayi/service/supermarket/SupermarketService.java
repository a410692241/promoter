package com.linayi.service.supermarket;

import com.linayi.entity.supermarket.Supermarket;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SupermarketService {

    /**
     * 查找所有的超市列表
     *
     * @param supermarket
     * @return 返回超市的集合
     */
    List<Supermarket> selectAll(Supermarket supermarket, Integer communityId, String type);

    /**
     * 新增一个超市
     *
     * @param supermarket
     */
    void insertSupermarket(Supermarket supermarket);

    /**
     * 通过supermarketId更新指定记录指定字段
     *
     * @param supermarket
     */
    void updateSupermarketBysupermarketId(Supermarket supermarket);


    /**
     * 通过supermarketId查询这条记录
     *
     * @param supermarketId
     * @return supermarket记录
     */
    Supermarket selectSupermarketBysupermarketId(Integer supermarketId);

    /**
     * 通过userId删除指定记录
     *
     * @param supermarketId
     */
    void deleteSupermarketrBysupermarketId(Integer supermarketId);

    void insertSupermarket(CommonsMultipartFile logoFile, Supermarket supermarket);

    void updateSupermarketBysupermarketId(CommonsMultipartFile logoFile, Supermarket supermarket);

    /**
     * 根据收货地址获取绑定的5家超市
     *
     * @return
     */
    List<Supermarket> getSupermarketByAddress(Integer userId);


    Supermarket getSupermarketByProcurerId(Integer userId);


    Supermarket getSupermarketByUserId(Integer userId);

    Supermarket getSupermarketById(Integer SupermarketId);
}
