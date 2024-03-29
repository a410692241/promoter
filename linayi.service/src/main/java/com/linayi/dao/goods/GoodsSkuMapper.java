package com.linayi.dao.goods;

import com.linayi.entity.goods.GoodsSku;import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 *
 */
public interface GoodsSkuMapper {
    int insert(GoodsSku record);

    int insertSelective(GoodsSku record);

    List<GoodsSku> getGoodsList(GoodsSku goods);
    
    
    /**
	 * 根据品牌id分类id商品名获取商品列表
	 * @param brandId 品牌id
	 * @param categoryId	分类id
	 * @param goodsName	商品名
	 * @return
	 */
	List<GoodsSku> getGoodsListBybrandIdcategoryIdGoodsName(GoodsSku goodsSku);


	GoodsSku getGoodsById(Integer goodsSkuId);
	
	
	/**
	 *      差价排行
	 * @param communityId 社区网点id
	 * @param categoryId  分类id
	 * @param brandId	      品牌id	
	 * @return
	 */
	List<GoodsSku> getDifferenceRanking(GoodsSku goodsSku);



	/**
	 * 根据商品名模糊查询商品信息(不包含价格)
	 * @param goodsName
	 * @return
	 */
	List<GoodsSku> getGoodsSkuByVagueGoodsName(GoodsSku goodsSku);


    List<GoodsSku> getGoodsByName(GoodsSku goods);

	/**
	 * 删除商品
	 * @param goodsSkuId
	 */
    void deleteGoodsById(Integer goodsSkuId);
    
    	/**
     		* 获取所有商品id
     	* @return
     	*/
    	List<Integer>getGoodsSkuIdBygoodsSku();
    	
    /**
              * 自定义搜索下单
     * @param brandId   品牌id
     * @param goodsName 商品名
     * @param valueName 属性名
     * @return
     */
    List<GoodsSku>customSearch(GoodsSku goodsSku);

	/**
	 * 修改商品全名称
	 * @param goodsSku
	 */
	void updateGoodsFullName(GoodsSku goodsSku);

	GoodsSku selectNamebyId(Integer goodsSkuId);

	//根据商品id获取商品信息
	GoodsSku getGoodsSkuByGoodsSkuId(Integer goodsSkuId);

    void update(GoodsSku goodsSku);

	/**
	 * 根据条形码查找商品
	 * @param goodsSku
	 * @return
	 */
	List<GoodsSku> getGoodsByGoods(GoodsSku goodsSku);

	/**
	 * 查询所有的商品名
	 * @return
	 */
    List<String> getGoodsNameList();

	/**
	 * 把查询得到商品集合
	 * @param goodsSku
	 * @return
	 */
	List<GoodsSku> getGoodsLists(GoodsSku goodsSku);
	/**
	 * 首页推荐商品
	 * @param goodsSku
	 * @return
	 */
	List<GoodsSku> getRecommendGoodsSku(GoodsSku goodsSku);

	Integer getSoldNumByGoodsSkuId(@Param("goodsSkuId") Long goodsSkuId);

	GoodsSku getGoodsSkuByBarcode(@Param("barcode") String barcode);

	/**
	 * 促销排行
	 * @param goodsSku
	 * @return
	 */
	List<GoodsSku> getSpecialPrice(GoodsSku goodsSku);

	/**
	 * 根据社区id获取特价商品id集合
	 * 根据社区id获取特价商品id集合
	 * @param communityId
	 * @return
	 */
	List<Long> getGoodsSukIdByCommunityId(Integer communityId);


	/**
	 * 后台价差排行
	 * @param goodsSku
	 * @return
	 */
	List<GoodsSku> getBackstageDifferenceRanking(GoodsSku goodsSku);


	/**
	 * 根据超市id和商品id集合查询商品
	 * @param goodsSku
	 * @param goodsSkuIdList
	 * @return
	 */
	List<GoodsSku> selectBySupermerketIdAndGoodsSkuIdList(GoodsSku goodsSku);



	/**
	 * 根据超市id获取无价格的商品信息
	 * @param goodsSku
	 * @return
	 */
	List<GoodsSku> getGoodsSkuNotPrice(GoodsSku goodsSku);
}