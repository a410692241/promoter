package com.linayi.service.goods;

import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.supermarket.Supermarket;

import java.util.List;
import java.util.Map;

public interface SupermarketGoodsService {
	List<SupermarketGoods> getSupermarketGoods(SupermarketGoods superGoods);
	
	
	/**
	 *     根据商品id和用户id获取5家超市名称和价格
	 * @param goodsSkuId
	 * @return
	 */
	Map<String,Object>  getPriceSupermarketByGoodsSkuId (Integer uid,Integer goodsSkuId);
	
	
	/**
	 * 根据社区网点id和商品id获取超市和价格信息分享价格用
	 * @param communityId 社区id
	 * @param goodsSkuId  商品id
	 * @return
	 */
	List<Supermarket> getPriceSupermarketBycommunityIdAndgoodsSkuId(Integer uid,Integer goodsSkuId);

	/**
	 * 通过商品ID和网点ID查找对应的商品价格表从高到底排列
	 * @param goodsSkuId
	 * @param communityId
	 * @return
	 */
    List<SupermarketGoods> getSupermarketGoodsList(Integer goodsSkuId, Integer communityId);

	/**
	 * 查询单个超市商品判断可以分享、纠错还是查看（后台修改价格用）
	 * @param goodsSkuId
	 * @param supermarketId
	 * @return
	 */
	public Supermarket getCorrectTypeBySupermarketIdAndgoodsSkuId(Long goodsSkuId,Integer supermarketId);

	/**
	 * 获取超市商品表其它超市价格
	 * @param supermarketGoods
	 * @return
	 */
	List<SupermarketGoods> getOtherPrice(SupermarketGoods supermarketGoods);
}
