package com.linayi.service.goods.impl;

import com.linayi.dao.community.CommunityMapper;
import com.linayi.dao.goods.*;
import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.entity.goods.*;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.enums.CategoryLevel;
import com.linayi.enums.MemberLevel;
import com.linayi.enums.PriceOrderType;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.community.CommunityService;
import com.linayi.service.goods.*;
import com.linayi.service.promoter.OpenMemberInfoService;
import com.linayi.util.*;
import com.linayi.vo.promoter.PromoterVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

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
	@Autowired
	private CommunityService communityService;
	@Resource
	private OpenMemberInfoService openMemberInfoService;
	private RestHighLevelClient esClient = RestClientFactory.getHighLevelClient();

	@Override
	public List<GoodsSku> getGoodsList(GoodsSku goods) {
		return goodsSkuMapper.getGoodsList(goods);
	}

	@Transactional
	@Override
	public String insertGoods(ModelMap modelMap, MultipartFile file, String category, String brand, GoodsSku goods, String [] attribute, HttpServletRequest httpRequest, Integer userId) throws Exception {
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
		// 获取商品名用于管理员选择
		/*List<String> goodsNameList = goodsSkuMapper.getGoodsNameList();
		model.addAttribute("goodsNames", goodsNameList);*/
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
	@Transactional
	@Override
	public String addGoods(String category, String brand, GoodsSku goods, String [] attribute,MultipartFile file, Integer userId) throws Exception {
		//判断条形码是否存在
		String barcode = goods.getBarcode().trim();
		GoodsSku goodsSku = new GoodsSku();
        int len = 13 - barcode.length();
        for (int i = 0; i < len; i++){
            barcode = "0" + barcode;
        }
		goodsSku.setBarcode(barcode);
		goodsSku.setStatus("NORMAL");
		List<GoodsSku> goodsByGoods = goodsSkuMapper.getGoodsByGoods(goodsSku);
		if (goodsByGoods != null && goodsByGoods.size() > 0){
			return "barcodeRepeat";
		}
		goodsSku.setBarcode(null);
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
			goods.setBarcode(barcode);
			goods.setBrandId(brandId);
			goods.setCategoryId(categoryId);
			goods.setStatus("NORMAL");
			goods.setCreateTime(new Date());
			goods.setUserId(userId);

			List<Attribute> attributes = attributeMapper.getAttributes();
			List<GoodsAttrValue> list = new ArrayList<>();
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
					goodsAttrValue.setCreateTime(new Date());
					list.add(goodsAttrValue);
					//goodsAttrValueMapper.insert(goodsAttrValue);
				}
			}
			//判断商品全称是否存在
			String fullName = getNewGoodsName(goods,list);
			goodsSku.setFullName(fullName);
			goodsByGoods = goodsSkuMapper.getGoodsByGoods(goodsSku);
			if (goodsByGoods != null && goodsByGoods.size() > 0){
                //goodsSkuMapper.deleteGoodsById(Integer.parseInt(goods.getGoodsSkuId() + ""));
				return "nameRepeat";
			}
			if (file != null) {
				String image = null;
				image = ImageUtil.handleUpload(file);
				goods.setImage(image);
			}
			goods.setFullName(fullName);
			goodsSkuMapper.insert(goods);
			if(list != null && list.size() > 0){
				for (GoodsAttrValue goodsAttrValue : list) {
					goodsAttrValue.setGoodsSkuId(Integer.parseInt(goods.getGoodsSkuId() + ""));
					goodsAttrValueMapper.insert(goodsAttrValue);
				}
			}
		}
		return "success";
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
		getAttributesMap(modelMap, attributeId, value);
		return "jsp/goods/specifications";
	}

	private void getAttributesMap(ModelMap modelMap, Integer attributeId, String value) {
		/*查询所有的属性*/
		List<Attribute> attributes = attributeService.getAttributes();
		Map<Object, Object> map = new LinkedHashMap<>();
		if (attributes != null && attributes.size() > 0) {
			modelMap.addAttribute("attributes", attributes);
			List<AttributeValue> attrvlas;
			if (value != null && !"".equals(value)) {
				/* 新增属性值*/
				AttributeValue attributeValue = new AttributeValue();
				AttributeValue attributeValue1 = attributeValueService.getAttributeValue(attributeId, value);
				if (attributeValue1 == null) {
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
				if (attrvlas != null && attrvlas.size() > 0) {
					for (AttributeValue attrvla : attrvlas) {
						list.add(attrvla.getValue());
					}
				}
				map.put(attribute.getName(), list);
			}
		}
		modelMap.addAttribute("map", map);
	}

	@Override
	public void toShowSpecifications(ModelMap modelMap, Integer attributeId, String value) {
		/*查询所有的属性*/
		getAttributesMap(modelMap, attributeId, value);
	}

	@Transactional
	@Override
	public void addSpecifications(Integer attributeId, String value) {
		if (value != null && !"".equals(value)) {
			/* 新增属性值*/
			AttributeValue attributeValue = new AttributeValue();
			AttributeValue attributeValue1 = attributeValueService.getAttributeValue(attributeId, value);
			if (attributeValue1 == null) {
				attributeValue.setAttributeId(attributeId);
				attributeValue.setValue(value);
				attributeValue.setStatus("NORMAL");
				attributeValue.setCreateTime(new Date());
				attributeValueService.addAttributeValue(attributeValue);
			}
		}
	}

	@Override
	public String getNewGoodsName(GoodsSku goodsSku, List<GoodsAttrValue> newGoodsAttrValue) {
		Brand brand = brandService.getBrandById(goodsSku.getBrandId());
		//获取所有的属性
//		List<Attribute> attributesList = attributeMapper.getAttributesList(goodsSku.getGoodsSkuId());
		//跟商品属性值获取所有属性
		List<Attribute> attributesList = new ArrayList<>();
		for (GoodsAttrValue goodsAttrValue : newGoodsAttrValue) {
			AttributeValue attrVal = attributeValueService.getAttrValsByAttrValId(goodsAttrValue.getAttrValueId());
			Attribute attribute = new Attribute();
			attribute.setName(attrVal.getAttributeName());
			attribute.setAttributeValue(attrVal.getValue());
			attributesList.add(attribute);
		}

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
	public void exportGoodsData(GoodsSku goodsSku, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 只是让浏览器知道要保存为什么文件而已，真正的文件还是在流里面的数据，你设定一个下载类型并不会去改变流里的内容。
		//而实际上只要你的内容正确，文件后缀名之类可以随便改，就算你指定是下载excel文件，下载时我也可以把他改成pdf等。
		response.setContentType("application/vnd.ms-excel");
		// 传递中文参数编码
		String codedFileName = java.net.URLEncoder.encode("商品信息","UTF-8");
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
		goodsSku.setStatus("NORMAL");
		List<GoodsSku> goodsLists = goodsSkuMapper.getGoodsLists(goodsSku);
		// 定义一个工作薄
		Workbook workbook = new HSSFWorkbook();
		// 创建一个sheet页
		Sheet sheet = workbook.createSheet("商品信息");
		// 创建一行
		Row row = sheet.createRow(0);
		// 在本行赋值 以0开始

		row.createCell(0).setCellValue("商品编号");
		row.createCell(1).setCellValue("商品名");
		row.createCell(2).setCellValue("商品全名");
		row.createCell(3).setCellValue("条形码");
		row.createCell(4).setCellValue("分类名");
		row.createCell(5).setCellValue("品牌名");
		row.createCell(6).setCellValue("型号");
		row.createCell(7).setCellValue("功能");
		row.createCell(8).setCellValue("产地");
		row.createCell(9).setCellValue("生产日期");
		row.createCell(10).setCellValue("有效日期");
		row.createCell(11).setCellValue("产家");
		row.createCell(12).setCellValue("其他属性");
		row.createCell(13).setCellValue("创建时间");
		row.createCell(14).setCellValue("修改时间");
		row.createCell(15).setCellValue("添加人");
		row.createCell(16).setCellValue("创建账号");
		// 定义样式
		CellStyle cellStyle = workbook.createCellStyle();
		// 格式化日期
		//cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd"));
		String pattern = "yyyy-MM-dd HH:mm:ss";
		// 遍历输出
		for (int i = 1; i <= goodsLists.size(); i++) {
			GoodsSku goods = goodsLists.get(i - 1);
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(goods.getGoodsSkuId());
			row.createCell(1).setCellValue(goods.getName());
			row.createCell(2).setCellValue(goods.getFullName());
			row.createCell(3).setCellValue(goods.getBarcode());
			row.createCell(4).setCellValue(goods.getCategoryName());
			row.createCell(5).setCellValue(goods.getBrandName());
			row.createCell(6).setCellValue(goods.getModel());
			row.createCell(7).setCellValue(goods.getFunction());
			row.createCell(8).setCellValue(goods.getProduceAddress());
			row.createCell(9).setCellValue(DateUtil.date2String(goods.getProduceDate(),pattern));
			row.createCell(10).setCellValue(DateUtil.date2String(goods.getValidDate(),pattern));
			row.createCell(11).setCellValue(goods.getManufacturer());
			row.createCell(12).setCellValue(goods.getOtherAttribute());
			row.createCell(13).setCellValue(DateUtil.date2String(goods.getCreateTime(),pattern));
			row.createCell(14).setCellValue(DateUtil.date2String(goods.getUpdateTime(),pattern));
			row.createCell(15).setCellValue(goods.getCreateName());
			row.createCell(16).setCellValue(goods.getUserName());

		}
		OutputStream  fOut = response.getOutputStream();
		workbook.write(fOut);
		fOut.flush();
		fOut.close();
	}



	@Override
	public String specificationsAdd(String categoryName, String brandName, String attrStr,Integer goodsSkuId) {
		return cateBrandValService.addCateBrandVal(categoryName, brandName, attrStr, goodsSkuId);
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
	public Object getGoodsLists(GoodsSku goods) {
		List<GoodsSku> goodsList = goodsSkuMapper.getGoodsLists(goods);
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
		//获取用户的会员等级
		MemberLevel memberLevel = openMemberInfoService.getCurrentMemberLevel(goodsSku.getUserId());
		Integer communityId = communityMapper.getcommunityIdByuserId(goodsSku.getUserId());
		goodsSku.setCommunityId(communityId);
		goodsSku.setMemberLevel(memberLevel.toString());
		List<GoodsSku> goodsSkuList = goodsSkuMapper.getGoodsListBybrandIdcategoryIdGoodsName(goodsSku);

		if(goodsSkuList.size()>0) {
			setMemberPrice(memberLevel,goodsSkuList);
		}
		return goodsSkuList;
	}

	//差价排行
	@Override
	public List<GoodsSku> getDifferenceRanking(GoodsSku goodsSku) {
		System.out.println("程序开始~~~~~~~~~~~~~");
		//获取用户的会员等级
		MemberLevel memberLevel = openMemberInfoService.getCurrentMemberLevel(goodsSku.getUserId());
		if(goodsSku.getCommunityId()==null) {
			goodsSku.setCommunityId(communityMapper.getcommunityIdByuserId(goodsSku.getUserId()));
		}
		goodsSku.setMemberLevel(memberLevel.toString());
		System.out.println("开始获取列表~~~~~~~~~~~~~");
		List<GoodsSku> differenceRankingList = goodsSkuMapper.getDifferenceRanking(goodsSku);
		if (differenceRankingList.size() != 0) {
			for (GoodsSku sku : differenceRankingList) {
				sku.setImage(ImageUtil.dealToShow(sku.getImage()));
			}
		}
		System.out.println("程序结束~~~~~~~~~~~~~");
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
				goodsSku1.setImage(goodsSku.getImage());
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
		//获取用户的会员等级
		MemberLevel memberLevel = openMemberInfoService.getCurrentMemberLevel(goodsSku.getUserId());
		//根据uid获取网点id
		Integer communityId = communityMapper.getcommunityIdByuserId(goodsSku.getUserId());
		goodsSku.setCommunityId(communityId);
		goodsSku.setMemberLevel(memberLevel.toString());
		List<GoodsSku> goodsSkuList = goodsSkuMapper.customSearch(goodsSku);
		if(goodsSkuList.size()!=0) {

			setMemberPrice(memberLevel,goodsSkuList);

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
			}
		}
		return supermarketGoods;
	}

	@Transactional
	@Override
	public String edit(CommonsMultipartFile goodsImage, GoodsSku goodsSku, Integer userId) {
		try {
			//判断条形码是否存在
			String barcode = goodsSku.getBarcode().trim();
			GoodsSku goods = new GoodsSku();
			int len = 13 - barcode.length();
			for (int i = 0; i < len; i++){
				barcode = "0" + barcode;
			}
			goods.setBarcode(barcode);
			goods.setStatus("NORMAL");
			List<GoodsSku> goodsByGoods = goodsSkuMapper.getGoodsByGoods(goods);
			if ((goodsByGoods != null && goodsByGoods.size() > 0) && !(goodsByGoods.get(0).getGoodsSkuId() + "").equals(goodsSku.getGoodsSkuId() + "")){
				return "barcodeRepeat";
			}
			//修改时间小于2019-03-19 时更新创建时间和创建的account_id
			GoodsSku goodsSku_new = goodsSkuMapper.getGoodsById(Integer.parseInt(goodsSku.getGoodsSkuId() +""));
			if (!barcode.equals(goodsSku_new.getBarcode())){
				if(goodsSku_new.getCreateTime().getTime() < DateUtil.string2Date("2019-03-19 00:00:00","yyyy-MM-dd HH:mm:ss").getTime()){
					goodsSku.setCreateTime(new Date());
					goodsSku.setUserId(userId);
				}
			}
			goods.setBarcode(null);
			String s = ImageUtil.handleUpload(goodsImage);
			String createTimeStart = goodsSku.getCreateTimeStart();
			if(createTimeStart != null && !"".equals(createTimeStart)){
                Date produceDate = DateUtil.string2Date(createTimeStart, "yyyy-MM-dd HH:mm:ss");
                goodsSku.setProduceDate(produceDate);
            }

			String createTimeEnd = goodsSku.getCreateTimeEnd();
            if(createTimeEnd != null && !"".equals(createTimeEnd)){
                Date validDate = DateUtil.string2Date(createTimeEnd, "yyyy-MM-dd HH:mm:ss");
                goodsSku.setValidDate(validDate);
            }
			//判断商品全称是否存在
			String goodsName = getGoodsName(goodsSku);
			goods.setFullName(goodsName);
			goodsByGoods = goodsSkuMapper.getGoodsByGoods(goods);
			if ((goodsByGoods != null && goodsByGoods.size() > 0) && !(goodsByGoods.get(0).getGoodsSkuId() + "").equals(goodsSku.getGoodsSkuId() + "")){
				return "nameRepeat";
			}
			goodsSku.setFullName(goodsName);
			goodsSku.setBarcode(barcode);
			goodsSku.setImage(s);
			goodsSku.setUpdateTime(new Date());
			goodsSkuMapper.update(goodsSku);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "defeat";
	}

	@Override
	public List<Supermarket> listSupermarket(Supermarket supermarket) {
		return supermarketMapper.selectAll(supermarket);
	}
	@Override
	public List<GoodsSku> searchByKey(PromoterVo.EsConfig esConfig) throws Exception {
		//获取会员等级
		Integer userId = esConfig.getUserId();
		MemberLevel currentMemberLevel = openMemberInfoService.getCurrentMemberLevel(userId);
		//获取配送地址所在的网点id
		Integer communityId = communityService.getcommunityIdByuserIdInDefaultAddress(userId);
		String keyword = "";
		//普通会员
		if (MemberLevel.NOT_MEMBER.toString().equals(currentMemberLevel.toString()) || MemberLevel.NORMAL.toString().equals(currentMemberLevel.toString())) {
			keyword = "Normal";
		}
		//高级会员
		else if (MemberLevel.SENIOR.toString().equals(currentMemberLevel.toString())) {
			keyword = "Senior";
		}
		//超级vip
		else if (MemberLevel.SUPER.toString().equals(currentMemberLevel.toString())) {
			keyword = "Super";
		}
		String key = esConfig.getKey();
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		String fieldName = "fullName";
		String fieldName2 = "category";
		String fieldName3 = "brand";
		//设置查询的条件为商品名存在特定关键字符,而且价格不能为null
		//对指定字段设置ik分词器
		searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery(key, fieldName))
				.must(QueryBuilders.existsQuery("minPrice" + keyword))
				.must(QueryBuilders.matchQuery("communityId", communityId)));
		searchSourceBuilder.size(esConfig.getPageSize());
		String orderType = esConfig.getOrderType();
		//排序规则
		if (PriceOrderType.SPREAD_DOWN.name().equalsIgnoreCase(orderType)) {
			if (MemberLevel.NOT_MEMBER.toString().equals(currentMemberLevel.toString()) || MemberLevel.NORMAL.toString().equals(currentMemberLevel.toString())) {
				searchSourceBuilder.sort("spreadNormal", SortOrder.DESC);
			}
			else if (MemberLevel.SENIOR.toString().equals(currentMemberLevel.toString())) {
				searchSourceBuilder.sort("spreadSenior", SortOrder.DESC);
			}
			else if (MemberLevel.SUPER.toString().equals(currentMemberLevel.toString())) {
				searchSourceBuilder.sort("spreadSuper", SortOrder.DESC);
			}

		}
		if (PriceOrderType.PRICE_UP.name().equalsIgnoreCase(orderType)) {
			searchSourceBuilder.sort("minPrice" + keyword, SortOrder.ASC);
		}
		if (PriceOrderType.PRICE_DOWN.name().equalsIgnoreCase(orderType)) {
			searchSourceBuilder.sort("minPrice" + keyword, SortOrder.DESC);
		}
		searchSourceBuilder.from((esConfig.getCurrentPage() - 1) * esConfig.getPageSize());
		searchRequest.source(searchSourceBuilder);
		searchRequest.indices("community_goods_index");
		SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits hits = response.getHits();
		List<GoodsSku> goodsSkus = new ArrayList<>();
		if (hits.totalHits == 0) {
			return goodsSkus;
		}
		long totalHits = hits.getTotalHits();

		//获取当前数据
		//获取的带有价格的数据,额外添加规格属性,商品图
		for (SearchHit hit : hits) {
			GoodsSku esGoodsSkuByHit = getEsGoodsSkuByHit(hit, keyword);

			Long goodsSkuId = esGoodsSkuByHit.getGoodsSkuId();
			GetRequest goods_index = new GetRequest("goods_sku_index", "goods_sku", goodsSkuId + "");
			GetResponse goods_index_resp = esClient.get(goods_index, RequestOptions.DEFAULT);
			if (goods_index_resp.isExists()) {
				Map<String, Object> sourceAsMap = goods_index_resp.getSourceAsMap();
				esGoodsSkuByHit.setImage(Configuration.getConfig().getValue(ConstantUtil.IMAGE_SERVER) + "/" + sourceAsMap.get("image") + "");
				esGoodsSkuByHit.setAttrValues(sourceAsMap.get("attribute") + "");
			}else{
				esGoodsSkuByHit.setImage("");
				esGoodsSkuByHit.setAttrValues("");
			}

			goodsSkus.add(esGoodsSkuByHit);
		}
		esConfig.setTotal( Integer.parseInt(totalHits + ""));
		return goodsSkus;
	}

	@Override
	@Transactional
	public void recommendGoodsSku(GoodsSku goodsSku) {
		goodsSku.setIsRecommend("TRUE");
		goodsSkuMapper.update(goodsSku);
	}

	@Override
	@Transactional
	public void removedRecommend(GoodsSku goodsSku) {
		goodsSku.setIsRecommend("FALSE");
		goodsSkuMapper.update(goodsSku);
	}



	/**
	 * @param hit
	 * @param PriceKeyWord 最低价的那个字段
	 * @return
	 */
	private GoodsSku getEsGoodsSkuByHit(SearchHit hit, String PriceKeyWord) {
		GoodsSku goodsSku = new GoodsSku();
		Map<String, Object> resultMap = hit.getSourceAsMap();
		//获取商品所在当前服务点的最低价与价差
		long goodsSkuId = Long.parseLong(resultMap.get("goodsSkuId") + "");
		//设置商品id
		goodsSku.setGoodsSkuId(goodsSkuId);
		//设置商品名
		goodsSku.setFullName(resultMap.get("fullName") + "");
		int minPrice = Integer.parseInt(resultMap.get("minPrice" + PriceKeyWord) + "");
		int maxPrice = Integer.parseInt(resultMap.get("maxPrice" + PriceKeyWord) + "");
		goodsSku.setMinPrice(minPrice);
//		goodsSku.setSoldNum(Integer.parseInt(resultMap.get("soldNum") + ""));
        double spreadRate = Double.parseDouble(resultMap.get("spread" + PriceKeyWord) + "");
		BigDecimal num = new BigDecimal(Double.toString(spreadRate));
		BigDecimal number100 = new BigDecimal(Double.toString(100));
		double value = num.multiply(number100).doubleValue(); // radio2 * 100;
		goodsSku.setSpreadRate(value);
		return goodsSku;
	}


	/**
	 * @return
	 */
	@Override
	public GoodsSku searchByBarcode(PromoterVo.EsConfig esConfig)  {
		GoodsSku resultGoodsSku = new GoodsSku();
		String barcode = esConfig.getBarcode();
		GoodsSku goodsSku = goodsSkuMapper.getGoodsSkuByBarcode(barcode);

		if(goodsSku == null){
			throw new BusinessException(ErrorType.BARCODE_ERROR);
		}
		//设置商品图,商品名,销量
		resultGoodsSku.setGoodsSkuId(goodsSku.getGoodsSkuId());
//		resultGoodsSku.setSoldNum(goodsSku.getSoldNum());
		resultGoodsSku.setImage(Configuration.getConfig().getValue(ConstantUtil.IMAGE_SERVER) + "/" + goodsSku.getImage());
		resultGoodsSku.setFullName(goodsSku.getFullName());
		Long goodsSkuId = goodsSku.getGoodsSkuId();
		Integer userId = esConfig.getUserId();
		Integer communityId = communityService.getcommunityIdByuserIdInDefaultAddress(userId);
		CommunityGoods communityGoods = communityGoodsMapper.getCommunityGoodsByBarcode(goodsSkuId,communityId);
		if(communityGoods == null){
			throw new BusinessException(ErrorType.BARCODE_ERROR);
		}

		//设置商品最近价和价差
		MemberLevel currentMemberLevel = openMemberInfoService.getCurrentMemberLevel(userId);
		//获取配送地址所在的网点id
		//普通会员
		if (MemberLevel.NOT_MEMBER.toString().equals(currentMemberLevel.toString()) || MemberLevel.NORMAL.toString().equals(currentMemberLevel.toString())) {
			Integer minPrice = communityGoods.getMinPriceNormal();
			Integer maxPrice = communityGoods.getMaxPriceNormal();
			resultGoodsSku.setMinPrice(minPrice);
			double spreadRate = (maxPrice - minPrice) * 100 / Double.parseDouble(minPrice + "");
			BigDecimal bigDecimal = new BigDecimal(spreadRate);
			double v = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			resultGoodsSku.setSpreadRate(v);
		}
		//高级会员
		else if (MemberLevel.SENIOR.toString().equals(currentMemberLevel.toString())) {
			Integer minPrice = communityGoods.getMinPriceSenior();
			Integer maxPrice = communityGoods.getMaxPriceSenior();
			resultGoodsSku.setMinPrice(minPrice);
			double spreadRate = (maxPrice - minPrice) * 100 / Double.parseDouble(minPrice + "");
			BigDecimal bigDecimal = new BigDecimal(spreadRate);
			double v = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			resultGoodsSku.setSpreadRate(v);
		}
		//超级vip
		else if (MemberLevel.SUPER.toString().equals(currentMemberLevel.toString())) {
			Integer minPrice = communityGoods.getMinPriceSuper();
            Integer maxPrice = communityGoods.getMaxPriceSuper();
            resultGoodsSku.setMinPrice(minPrice);
            double spreadRate = (maxPrice - minPrice) * 100 / Double.parseDouble(minPrice + "");
            BigDecimal bigDecimal = new BigDecimal(spreadRate);
            double v = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			resultGoodsSku.setSpreadRate(v);
		}
		List<Integer> attributeValueIdList = goodsAttrValueMapper.getGoodsAttrValueByGoodsId(goodsSkuId).stream()
				.collect(Collectors.mapping(GoodsAttrValue::getAttrValueId, Collectors.toList()));
		AttributeValue attributeValue = new AttributeValue();
		attributeValue.setAttributeIdValueList(attributeValueIdList);
		List<AttributeValue> attributeValues = attributeValueMapper.selectAttrValueList(attributeValue);

		StringBuilder stringBuilder = new StringBuilder();

		for (AttributeValue attributeV : attributeValues) {
			stringBuilder.append(" /"+attributeV.getValue());
		}
		resultGoodsSku.setAttrValues(stringBuilder.toString().substring(2));

		return resultGoodsSku;

	}


	@Override
	public List<GoodsSku> getRecommendGoodsSku(GoodsSku goodsSku) {
		//获取用户的会员等级
		MemberLevel memberLevel = openMemberInfoService.getCurrentMemberLevel(goodsSku.getUserId());
		//根据uid获取网点id
		Integer communityId = communityService.getcommunityIdByuserIdInDefaultAddress(goodsSku.getUserId());
		goodsSku.setCommunityId(communityId);
		goodsSku.setMemberLevel(memberLevel.toString());
		List<GoodsSku> goodsSkuList = goodsSkuMapper.getRecommendGoodsSku(goodsSku);
		if (goodsSkuList.size() != 0) {
			for (GoodsSku sku : goodsSkuList) {
				sku.setImage(ImageUtil.dealToShow(sku.getImage()));
			}
		}
		return goodsSkuList;
	}

	private List<GoodsSku> setMemberPrice(MemberLevel memberLevel, List<GoodsSku> goodsSkuList) {
		DecimalFormat df = new DecimalFormat("#.00");
		//普通用户和普通会员
		if (MemberLevel.NOT_MEMBER.toString().equals(memberLevel.toString()) || MemberLevel.NORMAL.toString().equals(memberLevel.toString())) {
			for (GoodsSku i : goodsSkuList) {
				Supermarket minSupermarket = supermarketMapper.selectSupermarketBysupermarketId(i.getMinSupermarketIdNormal());
				Supermarket maxSupermarket = supermarketMapper.selectSupermarketBysupermarketId(i.getMaxSupermarketIdNormal());
				i.setMinPrice(i.getMinPriceNormal());
				i.setMaxPrice(i.getMaxPriceNormal());
				i.setMinSupermarket(minSupermarket.getName());
				i.setMaxSupermarket(maxSupermarket.getName());
				i.setImage(ImageUtil.dealToShow(i.getImage()));
				i.setSpreadRate(Double.valueOf((df.format(Double.valueOf((i.getMaxPrice() - i.getMinPrice())) / i.getMinPrice() * 100))));
			}
		}
		//高级会员
		else if (MemberLevel.SENIOR.toString().equals(memberLevel.toString())) {
			for (GoodsSku i : goodsSkuList) {
				Supermarket minSupermarket = supermarketMapper.selectSupermarketBysupermarketId(i.getMinSupermarketIdSenior());
				Supermarket maxSupermarket = supermarketMapper.selectSupermarketBysupermarketId(i.getMaxSupermarketIdSenior());
				i.setMinPrice(i.getMinPriceSenior());
				i.setMaxPrice(i.getMaxPriceSenior());
				i.setMinSupermarket(minSupermarket.getName());
				i.setMaxSupermarket(maxSupermarket.getName());
				i.setImage(ImageUtil.dealToShow(i.getImage()));
				i.setSpreadRate(Double.valueOf((df.format(Double.valueOf((i.getMaxPrice() - i.getMinPrice())) / i.getMinPrice() * 100))));
			}
		}
		//超级vip
		else if (MemberLevel.SUPER.toString().equals(memberLevel.toString())) {
			for (GoodsSku i : goodsSkuList) {
				Supermarket minSupermarket = supermarketMapper.selectSupermarketBysupermarketId(i.getMinSupermarketIdSuper());
				Supermarket maxSupermarket = supermarketMapper.selectSupermarketBysupermarketId(i.getMaxSupermarketIdSuper());
				i.setMinPrice(i.getMinPriceSuper());
				i.setMaxPrice(i.getMaxPriceSuper());
				i.setMinSupermarket(minSupermarket.getName());
				i.setMaxSupermarket(maxSupermarket.getName());
				i.setImage(ImageUtil.dealToShow(i.getImage()));
				i.setSpreadRate(Double.valueOf((df.format(Double.valueOf((i.getMaxPrice() - i.getMinPrice())) / i.getMinPrice() * 100))));
			}
		}

		return goodsSkuList;
	}


	@Override
	public List<GoodsSku> getSpecialPrice(GoodsSku goodsSku) {
		//获取用户的会员等级
		MemberLevel memberLevel = openMemberInfoService.getCurrentMemberLevel(goodsSku.getUserId());
		if(goodsSku.getCommunityId()==null) {
			goodsSku.setCommunityId(communityMapper.getcommunityIdByuserId(goodsSku.getUserId()));
		}
		goodsSku.setMemberLevel(memberLevel.toString());
		List<GoodsSku> differenceRankingList = goodsSkuMapper.getSpecialPrice(goodsSku);
		if (differenceRankingList.size() != 0) {
			for (GoodsSku sku : differenceRankingList) {
				sku.setImage(ImageUtil.dealToShow(sku.getImage()));
			}
		}
		return differenceRankingList;
	}
}
