package com.linayi.service.goods;

import com.linayi.entity.correct.Correct;
import com.linayi.entity.goods.Attribute;
import com.linayi.entity.goods.GoodsAttrValue;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.goods.SupermarketGoods;

import com.linayi.entity.supermarket.Supermarket;
import com.linayi.vo.promoter.PromoterVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface GoodsSkuService {


    /*新增商品*/
    String addGoods(String category, String brand, GoodsSku goods, String [] attribute,MultipartFile file, Integer userId) throws Exception;

    /*分页查询*/
    List<GoodsSku> getGoodsList(GoodsSku goods);

    /*根据id查询商品*/
    GoodsSku getGoodsSkuById(Long goodsSkuId);

    List<Object> specificationsFilter(String categoryName, String brandName);

    String showSpecifications(ModelMap modelMap, Integer attributeId, String value);

    String specificationsAdd(String categoryName, String brandName, String attrStr, Integer goodsSkuId);

    void view(Long goodsSkuId, Model model);
    GoodsSku getGoodsSku(Long goodsSkuId);
    Object getGoodsLists(GoodsSku goods);

    String insertGoods(ModelMap modelMap, MultipartFile file, String category, String brand, GoodsSku goods, String [] attribute, HttpServletRequest httpRequest, Integer userId) throws Exception;
    void getBrandAndVal(ModelMap model);

	/**
	 * 获取商品的名称
	 * @param goodsSku
	 * @return
	 */
	String getGoodsName(GoodsSku goodsSku);
    
    
    
	/**
	 * 根据品牌id分类id商品名获取商品列表
	 * @param brandId 品牌id
	 * @param categoryId	分类id
	 * @param goodsName	商品名
	 * @return
	 */
	List<GoodsSku>getGoodsListBybrandIdcategoryIdGoodsName(GoodsSku goodsSku);


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

	/**
	 * 根据商品名查询所有商品
	 * @param name
	 * @return
	 */
    List<GoodsSku> getGoodsByName(GoodsSku goodsSku, HttpServletRequest request);

	/**
	 * 根据商品Id查询商品其他超市价格
	 * @param goodsSkuId
	 * @return
	 */
	GoodsSku getGoodsPrices(Integer goodsSkuId, Integer userId);

    String deleteGoodsById(Integer goodsSkuId);

	List<Attribute> getAttributeList();
	
	
	/**
	   * 自定义搜索下单	
	* @param brandId   品牌id
	* @param goodsName 商品名
	* @param valueName 属性名
	* @return
	*/
	List<GoodsSku>customSearch(GoodsSku goodsSku);

	List<SupermarketGoods> selectSupermarketGoodsList(SupermarketGoods smg);

	/**
	 * 修改商品信息
	 * @param goodsImage
	 * @param goodsSku
	 */
	String edit(CommonsMultipartFile goodsImage, GoodsSku goodsSku,Integer userId);

    List<Supermarket> listSupermarket(Supermarket supermarket);

	/**
	 * 修改商品规格
	 * @param attribute
	 * @param goodsSkuId
	 * @return
	 */
    //String editGoodsAttribute(String[] attribute, Integer goodsSkuId);

	void toShowSpecifications(ModelMap modelMap, Integer attributeId, String value);

	void addSpecifications(Integer attributeId, String value);
	/**
	 * 获取商品要新增的商品全名称
	 * @param goodsSku
	 * @return
	 */
	String getNewGoodsName(GoodsSku goodsSku, List<GoodsAttrValue> newGoodsAttrValue);

	/**
	 * 根据条件导出excel
	 * @param goodsSku
	 */
    void exportGoodsData(GoodsSku goodsSku, HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 首页推荐商品
	 * @param goodsSku
	 * @return
	 */
	List<GoodsSku> getRecommendGoodsSku(GoodsSku goodsSku);

	/**通过关键字搜索商品列表
	 * @param esConfig
	 * @return
	 * @throws Exception
	 */
	List<GoodsSku> searchByKey(PromoterVo.EsConfig esConfig) throws Exception;

	@Transactional
	void recommendGoodsSku(GoodsSku goodsSku);

	@Transactional
	void removedRecommend(GoodsSku goodsSku);

	/**通过条码查询商品
	 * @param barcode
	 * @return
	 */
    GoodsSku searchByBarcode(PromoterVo.EsConfig barcode) throws IOException;


	/**
	 * 促销排行
	 * @param goodsSku
	 * @return
	 */
	List<GoodsSku> getSpecialPrice(GoodsSku goodsSku);

	/**
	 * 直接下单
	 * @param goodsSku
	 */
	Map<String, Object> goodsDirectOrder(GoodsSku goodsSku);


	/**
	 * 后台价差排行
	 * @param goodsSku
	 * @return
	 */
	List<GoodsSku> getBackstageDifferenceRanking(GoodsSku goodsSku);

	//导出价差排行
	void exportDifferenceRanking(GoodsSku goodsSku, HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**获取点击量排行商品
	 * @param skuIdsByClientNum
	 * @return
	 */
    List<GoodsSku> getGoodsSkuBySkuIdList(List<Integer> skuIdsByClientNum);

	public void exportAffectedPriceData(Correct correct, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
