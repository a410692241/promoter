package com.linayi.service.goods;

import com.linayi.entity.goods.Attribute;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.goods.SupermarketGoods;

import com.linayi.entity.supermarket.Supermarket;
import com.linayi.entity.user.User;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GoodsSkuService {


    /*新增商品*/
    GoodsSku addGoods(String category, String brand, GoodsSku goods, String [] attribute,MultipartFile file, Integer userId) throws Exception;

    /*分页查询*/
    List<GoodsSku> getGoodsList(GoodsSku goods);

    /*根据id查询商品*/
    GoodsSku getGoodsSkuById(Long goodsSkuId);

    List<Object> specificationsFilter(String categoryName, String brandName);

    String showSpecifications(ModelMap modelMap, Integer attributeId, String value);

    String specificationsAdd(String categoryName, String brandName, String attrStr);

    void view(Long goodsSkuId, Model model);
    GoodsSku getGoodsSku(Long goodsSkuId);
    Object getGoodsLists(GoodsSku goods,String userName);

    GoodsSku insertGoods(ModelMap modelMap, MultipartFile file, String category, String brand, GoodsSku goods, String [] attribute, HttpServletRequest httpRequest, Integer userId) throws Exception;
    void getBrandAndVal(ModelMap model);
    
    
    
    
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
	String edit(CommonsMultipartFile goodsImage, GoodsSku goodsSku);

    List<Supermarket> listSupermarket(Supermarket supermarket);

	/**
	 * 修改商品规格
	 * @param attribute
	 * @param goodsSkuId
	 * @return
	 */
    String editGoodsAttribute(String[] attribute, Integer goodsSkuId);

	void toShowSpecifications(ModelMap modelMap, Integer attributeId, String value);

	void addSpecifications(Integer attributeId, String value);
}
