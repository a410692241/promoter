package com.linayi.dao.goods;

import com.linayi.entity.goods.SupermarketGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupermarketGoodsMapper {


    int insert(SupermarketGoods record);

    int insertSelective(SupermarketGoods record);

    List<SupermarketGoods> getSupermarketGoods(SupermarketGoods superGoods);


    /**
     	* 根据商品id获取超市和价格信息
     * @param communityId 社区id
     * @param goodsSkuId  商品id
     * @return
     */
//    List<SupermarketGoods> getPriceSupermarketByGoodsSkuId(Integer goodsSkuId,Integer communityId);



    /**
     * 根据社区网点id和商品id获取超市和价格信息
     * @param communityId 社区id
     * @param goodsSkuId  商品id
     * @return
     */
    List<SupermarketGoods> getPriceSupermarketBycommunityIdAndgoodsSkuId(@Param("communityId")Integer communityId,@Param("goodsSkuId")Integer goodsSkuId);

    /**
     * 根据社区网点id,商品id,超市id获取超市和价格信息
     * @param communityId
     * @param goodsSkuId
     * @param supermarketId
     * @return
     */
    SupermarketGoods getPriceSupermarketBycommunityIdgoodsSkuIdSupermarketId(@Param("communityId")Integer communityId,@Param("goodsSkuId")Integer goodsSkuId,@Param("supermarketId")Integer supermarketId);

    /**
     *  根据超市id获取商品id集合
     * @param communityId 社区id
     * @return
     */
    List<SupermarketGoods> getGoodsSkuIdBySupermarketId(@Param("supermarketIdList")List<Integer> supermarketIdList);



    /**
     * 根据超市id集合和商品id获取超市价格表信息
     * @param supermarketId
     * @param goodsSkuId
     * @return
     */
    List<SupermarketGoods> getSupermarketGoodsBysupermarketIdListAndgoodsSkuId(@Param("goodsSkuId") Integer goodsSkuId,
                                                                               @Param("supermarketIdList")List<Integer> supermarketIdList);

    /**
     * 根据超市id商品id获取超市价格表信息
     * @param supermarketId
     * @param goodsSkuId
     * @return
     */
    SupermarketGoods getSupermarketGoodsBysupermarketIdAndgoodsSkuId(@Param("goodsSkuId") Integer goodsSkuId, @Param("supermarketId") Integer supermarketId);

    //根据correctId删除数据
    Integer deleteSupermarketGoods(@Param("supermarketId") Integer supermarketId,
                                   @Param("goodsSkuId") Long goodsSkuId);

	List<SupermarketGoods> getSkuList(SupermarketGoods smg);
    
}