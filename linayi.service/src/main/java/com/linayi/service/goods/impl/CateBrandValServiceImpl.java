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

    @Override
    public CateBrandVal getCateBrandVal(Integer categoryId, Integer brandId, Integer valueId) {
        return cateBrandValMapper.getCateBrandVal(categoryId, brandId, valueId);
    }

    @Transactional
    @Override
    public String addCateBrandVal(String categoryName, String brandName, String attrStr,Integer goodsSkuId) {
        List<GoodsAttrValue> newGoodsAttrValue = new ArrayList<>();
        // 调用品牌和分类业务层方法，获取品牌id和分类id
        Integer brandId = null;
        List<Brand> brandList;
        if(brandName != null && !brandName.equals("")){
            brandList = brandMapper.getBrandsByName(null, null, brandName);
            if (brandList != null && brandList.size() > 0){
                brandId = brandList.get(0).getBrandId();
            }
        }

        Integer categoryId = null;
        List<Category> categorysByCate;
        if(categoryName != null && !categoryName.equals("")){
            categorysByCate = categoryMapper.getCategorysByCate(null, categoryName);
            if (categorysByCate != null && categorysByCate.size() > 0){
                categoryId = categorysByCate.get(0).getCategoryId();
            }
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
                    if (cateBrandVal == null && goodsSkuId == null){
                        cateBrandVal = new CateBrandVal();
                        cateBrandVal.setAttrValueId(attrbuteVal.getValueId());
                        cateBrandVal.setBrandId(brandId);
                        cateBrandVal.setCategoryId(categoryId);
                        cateBrandValMapper.insert(cateBrandVal);
                    }
                    if (goodsSkuId != null){
                        GoodsAttrValue goodsAttrValue = new GoodsAttrValue();
                        goodsAttrValue.setAttrValueId(attrbuteVal.getValueId());
                        goodsAttrValue.setCreateTime(new Date());
                        goodsAttrValue.setGoodsSkuId(goodsSkuId);
                        newGoodsAttrValue.add(goodsAttrValue);
                    }
                }
            }
            if (goodsSkuId != null){
                GoodsSku goods = new GoodsSku();
                goods.setGoodsSkuId(Long.parseLong(goodsSkuId + ""));
                List<GoodsSku> goodsSkus = goodsSkuService.getGoodsList(goods);
                //获取商品全名称
                String goodsName = goodsSkuService.getNewGoodsName(goodsSkus.get(0),newGoodsAttrValue);
                goods.setFullName(goodsName);
                goods.setStatus("NORMAL");
                List<GoodsSku> goodsByGoods = goodsSkuMapper.getGoodsByGoods(goods);
                if ((goodsByGoods != null && goodsByGoods.size() > 0) && !(goodsByGoods.get(0).getGoodsSkuId() + "").equals(goodsSkus.get(0).getGoodsSkuId() + "")){
                    return "nameRepeat";
                }
                //商品规格要处理
                goodsAttrValueMapper.deleteByGoodsSkuId(goodsSkuId);
                if (newGoodsAttrValue != null && newGoodsAttrValue.size() > 0){
                    for (GoodsAttrValue attrValue : newGoodsAttrValue) {
                        goodsAttrValueMapper.insert(attrValue);
                    }
                }
                goods.setGoodsSkuId(goodsSkus.get(0).getGoodsSkuId());
                goods.setUpdateTime(new Date());
                goodsSkuMapper.update(goods);
            }
        }
        return "success";
    }


}
