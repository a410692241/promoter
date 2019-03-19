package com.linayi.service.goods.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.linayi.entity.account.Account;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.entity.user.User;
import com.linayi.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.linayi.dao.account.AdminAccountMapper;
import com.linayi.dao.community.CommunityMapper;
import com.linayi.dao.goods.AttributeMapper;
import com.linayi.dao.goods.AttributeValueMapper;
import com.linayi.dao.goods.BrandMapper;
import com.linayi.dao.goods.CategoryMapper;
import com.linayi.dao.goods.CommunityGoodsMapper;
import com.linayi.dao.goods.GoodsAttrValueMapper;
import com.linayi.dao.goods.GoodsSkuMapper;
import com.linayi.dao.goods.SupermarketGoodsMapper;
import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.entity.account.AdminAccount;
import com.linayi.entity.goods.Attribute;
import com.linayi.entity.goods.AttributeValue;
import com.linayi.entity.goods.Brand;
import com.linayi.entity.goods.Category;
import com.linayi.entity.goods.CommunityGoods;
import com.linayi.entity.goods.GoodsAttrValue;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.enums.CategoryLevel;
import com.linayi.service.goods.AttributeService;
import com.linayi.service.goods.AttributeValueService;
import com.linayi.service.goods.BrandService;
import com.linayi.service.goods.CateBrandValService;
import com.linayi.service.goods.CategoryService;
import com.linayi.service.goods.GoodsAttrValueService;
import com.linayi.service.goods.GoodsSkuService;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Service
public class GoodsSkuServiceImpl implements GoodsSkuService {
	@Resource
	private GoodsSkuMapper goodsSkuMapper;
	@Resource
	private BrandMapper brandMapper;
	@Resource
	private CategoryMapper categoryMapper;
	@Resource
	private AttributeMapper attributeMapper;
	@Autowired
	@Resource
	private AttributeValueMapper attributeValueMapper;
	@Resource
	private GoodsAttrValueMapper goodsAttrValueMapper;
	@Autowired
	private BrandService brandService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AttributeValueService attributeValueService;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private CateBrandValService cateBrandValService;
	@Autowired
	private GoodsAttrValueService goodsAttrValueService;
	@Autowired
	private SupermarketMapper supermarketMapper;
	@Autowired
	private CommunityGoodsMapper communityGoodsMapper;
	@Autowired
	private SupermarketGoodsMapper supermarketGoodsMapper;
	@Autowired
	private CommunityMapper communityMapper;

	@Override
	public List<GoodsSku> getGoodsList(GoodsSku goods) {
		return goodsSkuMapper.getGoodsList(goods);
	}
	@Override
	public GoodsSku insertGoods(ModelMap modelMap, MultipartFile file, String category, String brand, GoodsSku goods, String [] attribute, HttpServletRequest httpRequest, Integer userId) throws Exception {
		MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpRequest;
		return addGoods(category, brand, goods, attribute,file,userId);
	}

	@Override
	public void getBrandAndVal(ModelMap model) {
		// 获取4级分类用于管理员选择
		List<Category> categorys = categoryService.getCategorysByLevel(CategoryLevel.fourth.getValue());
		model.addAttribute("categorys", categorys);
		// 获取品牌用于管理员选择
		List<Brand> brands = brandService.getBrandsByName();
		model.addAttribute("brands", brands);

	}

	@Override
	public GoodsSku getGoodsSkuById(Long goodsSkuId) {
		GoodsSku goodsSku = new GoodsSku();
		goodsSku.setGoodsSkuId(Long.parseLong(goodsSkuId + ""));
		List<GoodsSku> goodsList = goodsSkuMapper.getGoodsList(goodsSku);
		if (goodsList != null && goodsList.size() > 0) {
			goodsSku = goodsList.get(0);
		}
		Integer brandId = goodsSku.getBrandId();
		Integer categoryId = goodsSku.getCategoryId();
		if (brandId != null && brandId > 0) {
			Brand brand = brandMapper.getBrandById(brandId);
			if(brand != null)
				goodsSku.setBrandName(brand.getName());
		}
		if (categoryId != null && categoryId > 0) {
			Category category = categoryMapper.getCategoryById(categoryId);
			if(category != null)
				goodsSku.setCategoryName(category.getName());
		}
		return goodsSku;
	}
	@Override
	public GoodsSku addGoods(String category, String brand, GoodsSku goods, String [] attribute,MultipartFile file, Integer userId) throws Exception {
		//判断条形码是否存在
		String barcode = goods.getBarcode();
		GoodsSku goodsSku = new GoodsSku();
		goodsSku.setBarcode(barcode);
		GoodsSku goodsByGoods = goodsSkuMapper.getGoodsByGoods(goodsSku);
		if (goodsByGoods != null){
			return null;
		}
		goodsSku.setBarcode(null);

		if (file != null) {
			String image = null;
			image = ImageUtil.handleUpload(file);
			goods.setImage(image);
		}
		// 调用品牌和分类业务层方法，获取品牌id和分类id
		Integer brandId = null;
		List<Brand> brandList = brandMapper.getBrandsByName(null, null, brand);
		if (brandList != null && brandList.size() > 0) {
			brandId = brandList.get(0).getBrandId();
		}
		Integer categoryId = null;
		List<Category> categorysByCate = categoryMapper.getCategorysByCate(null, category);
		if (categorysByCate != null && categorysByCate.size() > 0) {
			categoryId = categorysByCate.get(0).getCategoryId();
		}
		if (brandId == null && !"".equals(brand)) {
			Brand brand1 = new Brand();
			brand1.setName(brand);
			brand1.setStatus("NORMAL");
			brandMapper.insert(brand1);
		}

		if (categoryId == null && !"".equals(category)) {
			Category category1 = new Category();
			category1.setName(category);
			category1.setLevel(Short.parseShort(CategoryLevel.fourth.getValue() + ""));
			category1.setStatus("NORMAL");
			categoryMapper.insert(category1);
		}


		if (goods.getName() != null && !"".equals(goods.getName())) {
			int length = barcode.length();
			for (int i = 0; i < 13 - length; i++) {
				barcode = "0" + barcode;
			}
			goods.setBarcode(barcode);
			goods.setBrandId(brandId);
			goods.setCategoryId(categoryId);
			goods.setStatus("NORMAL");
			goods.setCreateTime(new Date());
			goods.setUserId(userId);
			goodsSkuMapper.insert(goods);

			List<Attribute> attributes = attributeMapper.getAttributes();
			for (int i = 0; i < attribute.length; i++) {
				if (attribute[i] != null && !"".equals(attribute[i])){
					GoodsAttrValue goodsAttrValue = new GoodsAttrValue();
					Attribute attr = attributes.get(i);
					AttributeValue attrbuteVal = null;
					List<AttributeValue> attributeValue = attributeValueMapper.getAttributeValue(attr.getAttributeId(), attribute[i], null);
					if (attributeValue != null && attributeValue.size() > 0) {
						attrbuteVal = attributeValue.get(0);
					}
					goodsAttrValue.setAttrValueId(attrbuteVal.getValueId());
					goodsAttrValue.setGoodsSkuId(Integer.parseInt(goods.getGoodsSkuId() + ""));
					goodsAttrValue.setCreateTime(new Date());
					goodsAttrValueMapper.insert(goodsAttrValue);
				}
			}
			//判断商品全称是否存在
			String fullName = getGoodsName(goods);
			goodsSku.setFullName(fullName);
			goodsByGoods = goodsSkuMapper.getGoodsByGoods(goodsSku);
			if (goodsByGoods != null){
				goodsSkuMapper.deleteGoodsById(Integer.parseInt(goods.getGoodsSkuId() + ""));
				return null;
			}
			goods.setFullName(fullName);
			goodsSkuMapper.updateGoodsFullName(goods);
		}
		return goods;
	}

	@Override
	public List<Object> specificationsFilter(String categoryName, String brandName) {
		List<Object> list = new ArrayList<>();
		// 调用品牌和分类业务层方法，获取品牌id和分类id
		Integer brandId = brandService.getBrandIdByBrandName(brandName);
		Integer categoryId = categoryService.getCategoryByName(categoryName);
		/*通过分类名和品牌名查询所有的属性值*/
		List<AttributeValue> attributeValues = attributeValueService.getAttributeValueByCaAndBr(categoryId, brandId);
		List<Attribute> attributes = attributeMapper.getAttributes();
		List<Integer> attributeValueIds = new ArrayList<>();
		for (Attribute attribute : attributes) {
			boolean flag = true;
			if (attributeValues != null && attributeValues.size() > 0) {
				for (AttributeValue attributeValue : attributeValues) {
					if (attribute.getAttributeId() == attributeValue.getAttributeId()){
						attributeValueIds.add(attribute.getAttributeId());
						flag = false;
						break;
					}
				}
			}
			if (flag){
				attributeValueIds.add(0);
			}
		}
		list.add(attributeValueIds);
		list.add(attributeValues);
		return list;
	}

	@Override
	public String showSpecifications(ModelMap modelMap, Integer attributeId, String value) {
		/*查询所有的属性*/
		List<Attribute> attributes = attributeService.getAttributes();
		Map<Object, Object> map = new LinkedHashMap<>();
		if (attributes != null && attributes.size() > 0){
			modelMap.addAttribute("attributes", attributes);
			List<AttributeValue> attrvlas;
			if (value != null && !"".equals(value)){
				/* 新增属性值*/
				AttributeValue attributeValue = new AttributeValue();
				AttributeValue attributeValue1 = attributeValueService.getAttributeValue(attributeId, value);
				if (attributeValue1 == null){
					attributeValue.setAttributeId(attributeId);
					attributeValue.setValue(value);
					attributeValue.setStatus("NORMAL");
					attributeValue.setCreateTime(new Date());
					attributeValueService.addAttributeValue(attributeValue);
				}
			}

			for (Attribute attribute : attributes) {
				/*根据属性Id获取所有属性值*/
				attrvlas = attributeValueService.getAttrValsByAttrId(attribute.getAttributeId());
				List<String> list = new ArrayList<>();
				if (attrvlas != null && attrvlas.size() > 0){
					for (AttributeValue attrvla : attrvlas) {
						list.add(attrvla.getValue());
					}
				}
				map.put(attribute.getName(),list);
			}
		}
		modelMap.addAttribute("map",map);
		return "jsp/goods/specifications";
	}

	@Override
	public String specificationsAdd(String categoryName, String brandName, String attrStr) {
		String result = "success";
		try {
			cateBrandValService.addCateBrandVal(categoryName, brandName, attrStr);
		}catch (Exception e){
			result = "defeate";
		}
		return result;
	}

	@Override
	public void view(Long goodsSkuId, Model model) {
		GoodsSku goodsSku = getGoodsSku(goodsSkuId);
		model.addAttribute("goodsSku",goodsSku);
	}

	@Override
	public GoodsSku getGoodsSku(Long goodsSkuId) {
		GoodsSku goodsSku = this.getGoodsSkuById(goodsSkuId);
		if (goodsSku != null){
			packGoodsSku(goodsSku);
			List<GoodsAttrValue> goodsAttrValues = goodsAttrValueService.getGoodsAttrValueByGoodsId(goodsSku.getGoodsSkuId());
			String attrs = "";
			for (GoodsAttrValue goodsAttrValue : goodsAttrValues) {
				AttributeValue attributeValue = attributeValueService.getAttrValsByAttrValId(goodsAttrValue.getAttrValueId());
				if (attrs.equals("")){
					attrs += attributeValue.getValue();
				}else{
					attrs += "," + attributeValue.getValue();
				}
			}
			goodsSku.setAttrValues(attrs);
			String image = goodsSku.getImage();

			if (image != null && !"".equals(image)){
				try {
					goodsSku.setImage(ImageUtil.dealToShow(image));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return goodsSku;
	}

	@Override
	public Object getGoodsLists(GoodsSku goods,String userName) {
		String name = goods.getFullName();
		if (name != null && !"".equals(name)){
			goods.setFullName("%" + name + "%");
		}

		List<GoodsSku> goodsList = this.getGoodsList(goods);
		for (GoodsSku goodsSku : goodsList) {
			packGoodsSku(goodsSku);
		}
		PageResult<GoodsSku> pageResult = new PageResult<>(goodsList, goods.getTotal());
		return pageResult;
	}
	/**
	 * 根据品牌id和类别id分别把name赋给GoodsSku
	 * @param goodsSku
	 */
	private void packGoodsSku(GoodsSku goodsSku) {
		Integer categoryId = goodsSku.getCategoryId();
		if (categoryId != null && categoryId > 0) {
			Category category = categoryService.getCategoryById(categoryId);
			if (category != null){
				goodsSku.setCategoryName(category.getName());
			}
		}

		Integer brandId = goodsSku.getBrandId();
		if (brandId != null && brandId > 0) {
			Brand brand = brandService.getBrandById(brandId);
			if(brand != null){
				goodsSku.setBrandName(brand.getName());
			}
		}
	}


	@Override
	public List<GoodsSku> getGoodsListBybrandIdcategoryIdGoodsName(GoodsSku goodsSku) {

		Integer communityId = communityMapper.getcommunityIdByuserId(goodsSku.getUserId());
		goodsSku.setCommunityId(communityId);
		List<GoodsSku> goodsSkuList = goodsSkuMapper.getGoodsListBybrandIdcategoryIdGoodsName(goodsSku);
		if(goodsSkuList.size()>0) {
			for(GoodsSku i:goodsSkuList) {
				i.setImage(ImageUtil.dealToShow(i.getImage()));
			}
		}
		return goodsSkuList;
	}

	//差价排行
	@Override
	public List<GoodsSku> getDifferenceRanking(GoodsSku goodsSku) {

		if(goodsSku.getCommunityId()==null) {
			goodsSku.setCommunityId(communityMapper.getcommunityIdByuserId(goodsSku.getUserId()));
		}
		System.out.println("社区id:"+goodsSku.getCommunityId());
		List<GoodsSku> differenceRankingList = goodsSkuMapper.getDifferenceRanking(goodsSku);
		DecimalFormat df = new DecimalFormat("#.00");

		if(differenceRankingList.size()>0) {
			for(GoodsSku i:differenceRankingList) {
				System.out.println(i.getMaxSupermarketId());
				i.setMaxSupermarket(supermarketMapper.selectSupermarketBysupermarketId(i.getMaxSupermarketId()).getName());
				i.setMinSupermarket(supermarketMapper.selectSupermarketBysupermarketId(i.getMinSupermarketId()).getName());
				i.setImage(ImageUtil.dealToShow(i.getImage())); 
				i.setSpreadRate(Double.valueOf((df.format(Double.valueOf((i.getMaxPrice()-i.getMinPrice()))/i.getMinPrice()*100))));
			}
		}
		return differenceRankingList;
	}

	@Override
	public List<GoodsSku> getGoodsByName(GoodsSku goodsSku0, HttpServletRequest request) {
		List<GoodsSku> goodsSkusList = new ArrayList<>();
		if (goodsSku0 != null){
			List<GoodsSku> goodsSkus = goodsSkuMapper.getGoodsByName(goodsSku0);
			for (GoodsSku goodsSku : goodsSkus) {
				GoodsSku goodsSku1 = new GoodsSku();
				Brand brand = brandService.getBrandById(goodsSku.getBrandId());
				goodsSku1.setGoodsSkuId(goodsSku.getGoodsSkuId());
				goodsSku1.setName(goodsSku.getName());
				if(brand != null){
					goodsSku1.setBrandName(brand.getName());
				}
				goodsSku1.setImage(ImageUtil.dealToShow(goodsSku.getImage()));
				CommunityGoods communityGoodss = new CommunityGoods();
				communityGoodss.setGoodsSkuId(Integer.parseInt("" + goodsSku.getGoodsSkuId()));
				CommunityGoods communityGoods =communityGoodsMapper.getCommunityGoods(communityGoodss);
				if(communityGoods != null){
					goodsSku1.setMinPriceString(getpriceString(communityGoods.getMinPrice()));
					goodsSku1.setMinSupermarketName(supermarketMapper.selectSupermarketBysupermarketId(communityGoods.getMinSupermarketId()).getName());
				}
				goodsSkusList.add(goodsSku1);
			}
		}
		return goodsSkusList;
	}

	@Override
	public GoodsSku getGoodsPrices(Integer goodsSkuId,Integer userId) {
		GoodsSku goodsSku = goodsSkuMapper.getGoodsById(goodsSkuId);
		GoodsSku goodsSku1 = new GoodsSku();
		Brand brand = brandService.getBrandById(goodsSku.getBrandId());
		goodsSku1.setGoodsSkuId(goodsSku.getGoodsSkuId());
		goodsSku1.setName(goodsSku.getName());
		goodsSku1.setBrandName(brand.getName());
		Integer communityId = communityMapper.getcommunityIdByuserId(userId);
		List<SupermarketGoods> supermarketGoods = supermarketGoodsMapper.getPriceSupermarketBycommunityIdAndgoodsSkuId(communityId, goodsSkuId);
		if (supermarketGoods != null && supermarketGoods.size() > 1){
			goodsSku1.setSpreadRateString(NumberUtil.formatDouble((supermarketGoods.get(0).getPrice() - supermarketGoods.get(supermarketGoods.size() - 1).getPrice()) * 100 / Double.parseDouble(supermarketGoods.get(supermarketGoods.size() - 1).getPrice() + "")) + "%");
		}else {
			goodsSku.setSpreadRateString("0.00%");
		}
		goodsSku1.setSupermarketGoodsList(supermarketGoods);
		return goodsSku1;
	}

	@Transactional
	@Override
	public String deleteGoodsById(Integer goodsSkuId) {
		try {
			goodsSkuMapper.deleteGoodsById(goodsSkuId);
			List<GoodsAttrValue> goodsAttrValues = goodsAttrValueMapper.getGoodsAttrValueByGoodsId(Long.parseLong(goodsSkuId + ""));
			if (goodsAttrValues != null && goodsAttrValues.size() > 0){
				for (GoodsAttrValue goodsAttrValue : goodsAttrValues) {
					goodsAttrValueMapper.deleteById(goodsAttrValue.getAttrValueId());
				}
			}
			return "success";
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return "defeat";
	}

	@Override
	public List<Attribute> getAttributeList() {
		List<Attribute> attributes = attributeService.getAttributes();
		return attributes;
	}

	/**
	 * 分转元为单位
	 * @param Price
	 * @return
	 */
	public String getpriceString(Integer Price) {
		StringBuffer sb = new StringBuffer(Price + "");
		if(sb.length() >= 3){
			sb.insert(sb.toString().length() - 2, ".");
		}else if(sb.length() == 2){
			return "0." + sb.toString();
		}else if(sb.length() == 1){
			return "0.0" + sb.toString();
		}
		return sb.toString();
	}


	//根据商品名模糊查询商品信息(不包含价格)
	@Override
	public List<GoodsSku> getGoodsSkuByVagueGoodsName(GoodsSku goodsSku) {

		List<GoodsSku> goodsSkuList = goodsSkuMapper.getGoodsSkuByVagueGoodsName(goodsSku);
		if(goodsSkuList.size()>0) {

			for(GoodsSku i:goodsSkuList ) {
				i.setImage(ImageUtil.dealToShow(i.getImage()));
			}
		}
		return goodsSkuList;
	}

	//自定义下单
	@Override
	public List<GoodsSku> customSearch(GoodsSku goodsSku) {
		//根据uid获取网点id	
		Integer communityId = communityMapper.getcommunityIdByuserId(goodsSku.getUserId());
		goodsSku.setCommunityId(communityId);
		List<GoodsSku> goodsSkuList = goodsSkuMapper.customSearch(goodsSku);
		DecimalFormat df = new DecimalFormat("#.00");
		if(goodsSkuList.size()!=0) {
			for(GoodsSku i:goodsSkuList) {
				//差价率格式化
				i.setSpreadRate(Double.valueOf(df.format(i.getSpreadRate())));
				i.setImage(ImageUtil.dealToShow(i.getImage()));
			}
		}
		return goodsSkuList;
	}

	//商品名字处理
	public String getGoodsName(GoodsSku goodsSku) {
		Brand brand = brandService.getBrandById(goodsSku.getBrandId());

		List<Attribute> attributesList = attributeMapper.getAttributesList(goodsSku.getGoodsSkuId());
		StringBuffer goodsName = new StringBuffer();
		if (brand != null){
			goodsName.append(brand.getName());
		}
		goodsName.append(" goodsName");
		Map<String, String> attributeMap = new HashMap<>();
		if (attributesList != null && attributesList.size() > 0){
			for (Attribute attribute : attributesList) {
				attributeMap.put(attribute.getName(), attribute.getAttributeValue());
			}
			String taste = attributeMap.get(AttributeOrder.attrOrdes.get(0));
			if (taste != null && !"".equals(taste)){
				goodsName.append(" ").append(taste);
				attributeMap.remove(AttributeOrder.attrOrdes.get(0));
			}
			String packing = null;
			for (String attrOrde : AttributeOrder.attrOrdes) {
				if (attributeMap.containsKey("包装")){
					packing = attributeMap.get("包装");
					attributeMap.remove("包装");
				}
				if ( !attrOrde.equals(AttributeOrder.attrOrdes.get(0)) && attributeMap.containsKey(attrOrde)){
					goodsName.append(" ").append(attributeMap.get(attrOrde));
					attributeMap.remove(attrOrde);
				}

			}
			if (attributeMap != null && attributeMap.size() > 0){
				Set<String> strings = attributeMap.keySet();
				for (String attrName : strings) {
					goodsName.append(" ").append(attributeMap.get(attrName));
				}

			}
			if (packing != null){
				goodsName.append(" /").append(packing);
			}
		}
		return goodsName.toString().replace("goodsName", goodsSku.getName());
	}

	@Override
	public List<SupermarketGoods> selectSupermarketGoodsList(SupermarketGoods smg) {
		List<SupermarketGoods> supermarketGoods = supermarketGoodsMapper.getSkuList(smg);
		if (supermarketGoods != null && supermarketGoods.size() > 0) {
			for (SupermarketGoods supermarketGoods2 : supermarketGoods) {
				supermarketGoods2.setPriceStr(getpriceString(supermarketGoods2.getPrice()));
				System.out.println(getpriceString(supermarketGoods2.getPrice()));
			}
		}
		return supermarketGoods;
	}

	@Override
	public void edit(CommonsMultipartFile goodsImage, GoodsSku goodsSku) {
		String s;
		try {
			s = ImageUtil.handleUpload(goodsImage);
			String createTimeStart = goodsSku.getCreateTimeStart();
			Date produceDate = DateUtil.string2Date(createTimeStart, "yyyy-MM-dd HH:mm:ss");
			goodsSku.setProduceDate(produceDate);
			String createTimeEnd = goodsSku.getCreateTimeEnd();
			Date validDate = DateUtil.string2Date(createTimeEnd, "yyyy-MM-dd HH:mm:ss");
			String barcode = goodsSku.getBarcode();
			for (int i = 0; i < 11 - barcode.length(); i++) {
				barcode = "0" + barcode;
			}
			goodsSku.setBarcode(barcode);
			goodsSku.setValidDate(validDate);
			goodsSku.setImage(s);
			goodsSkuMapper.update(goodsSku);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Supermarket> listSupermarket(Supermarket supermarket) {
		return supermarketMapper.selectAll(supermarket);
	}
}
