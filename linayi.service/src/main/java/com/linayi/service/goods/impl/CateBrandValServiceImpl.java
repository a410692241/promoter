package com.linayi.service.goods.impl;

import com.linayi.dao.goods.*;
import com.linayi.entity.goods.*;
import com.linayi.enums.CategoryLevel;
import com.linayi.service.goods.BrandService;
import com.linayi.service.goods.CateBrandValService;
import com.linayi.service.goods.GoodsSkuService;
import com.linayi.util.AttributeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CateBrandValServiceImpl implements CateBrandValService {
    @Resource
    private CateBrandValMapper cateBrandValMapper;
    @Resource
    private BrandMapper brandMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private AttributeMapper attributeMapper;
    @Resource
    private AttributeValueMapper attributeValueMapper;
    @Autowired
    private GoodsSkuService goodsSkuService;
    @Autowired
    private GoodsAttrValueMapper goodsAttrValueMapper;
    @Autowired
    private GoodsSkuMapper goodsSkuMapper;
    @Autowired
    private BrandService brandService;

    @Override
    public CateBrandVal getCateBrandVal(Integer categoryId, Integer brandId, Integer valueId) {
        return cateBrandValMapper.getCateBrandVal(categoryId, brandId, valueId);
    }

    @Transactional
    @Override
    public String addCateBrandVal(String categoryName, String brandName, String attrStr,Integer goodsSkuId) {
        List<GoodsAttrValue> goodsAttrValueByGoodsId = null;
        // 调用品牌和分类业务层方法，获取品牌id和分类id
        Integer brandId = null;
        List<Brand> brandList = brandMapper.getBrandsByName(null, null, brandName);
        if (brandList != null && brandList.size() > 0){
            brandId = brandList.get(0).getBrandId();
        }
        Integer categoryId = null;
        List<Category> categorysByCate = categoryMapper.getCategorysByCate(null, categoryName);
        if (categorysByCate != null && categorysByCate.size() > 0){
            categoryId = categorysByCate.get(0).getCategoryId();
        }
        
        if (brandId == null && !"".equals(brandName)){
            Brand brand1 = new Brand();
            brand1.setName(brandName);
            brand1.setStatus("NORMAL");
            brandMapper.insert(brand1);
            brandId = brand1.getBrandId();
        }

        if(categoryId == null && !"".equals(categoryName)){
            Category category1 = new Category();
            category1.setName(categoryName);
            category1.setLevel(Short.parseShort(CategoryLevel.fourth.getValue() + ""));
            category1.setStatus("NORMAL");
            categoryMapper.insert(category1);
            categoryId = category1.getCategoryId();
        }
        String[] attrArr = attrStr.split(",");
        List<Attribute> attributes = attributeMapper.getAttributes();
        if (attrArr != null && attrArr.length > 0){
            for (int i = 0; i < attrArr.length; i++) {
                if(attrArr[i] != null && !"无".equals(attrArr[i])){
                    Attribute attribute = attributes.get(i);
                    List<AttributeValue> attributeValue = attributeValueMapper.getAttributeValue(attribute.getAttributeId(), attrArr[i],null);
                    AttributeValue attrbuteVal = null;
                    if (attributeValue != null && attributeValue.size() > 0){
                        attrbuteVal = attributeValue.get(0);
                    }
                    CateBrandVal cateBrandVal = cateBrandValMapper.getCateBrandVal(categoryId, brandId, attrbuteVal.getValueId());
                    if (cateBrandVal == null){
                        cateBrandVal = new CateBrandVal();
                        cateBrandVal.setAttrValueId(attrbuteVal.getValueId());
                        cateBrandVal.setBrandId(brandId);
                        cateBrandVal.setCategoryId(categoryId);
                        cateBrandValMapper.insert(cateBrandVal);
                    }
                    if (goodsSkuId != null){
                        if (i == 0){
                            goodsAttrValueByGoodsId = goodsAttrValueMapper.getGoodsAttrValueByGoodsId(Long.parseLong(goodsSkuId + ""));
                            goodsAttrValueMapper.deleteByGoodsSkuId(goodsSkuId);
                        }
                        GoodsAttrValue goodsAttrValue = new GoodsAttrValue();
                        goodsAttrValue.setAttrValueId(attrbuteVal.getValueId());
                        goodsAttrValue.setCreateTime(new Date());
                        goodsAttrValue.setGoodsSkuId(goodsSkuId);
                        goodsAttrValueMapper.insert(goodsAttrValue);
                    }
                }
            }
            if (goodsSkuId != null){
                GoodsSku goods = new GoodsSku();
                GoodsSku goodsSku = goodsSkuService.getGoodsSku(Long.parseLong(goodsSkuId + ""));
                String goodsName = getGoodsName(goodsSku);
                goods.setFullName(goodsName);
                goods.setStatus("NORMAL");
                List<GoodsSku> goodsByGoods = goodsSkuMapper.getGoodsByGoods(goods);
                if ((goodsByGoods != null && goodsByGoods.size() > 0) && !(goodsByGoods.get(0).getGoodsSkuId() + "").equals(goodsSku.getGoodsSkuId() + "")){
                    goodsAttrValueMapper.deleteByGoodsSkuId(goodsSkuId);
                    if (goodsAttrValueByGoodsId != null && goodsAttrValueByGoodsId.size() > 0){
                        for (GoodsAttrValue attrValue : goodsAttrValueByGoodsId) {
                            goodsAttrValueMapper.insert(attrValue);
                        }
                    }
                    return "repeat";
                }
                goods.setGoodsSkuId(goodsSku.getGoodsSkuId());
                goodsSkuMapper.update(goods);
            }
        }
        return "success";
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
}
