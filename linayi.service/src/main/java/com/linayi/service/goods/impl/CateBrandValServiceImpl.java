package com.linayi.service.goods.impl;

import com.linayi.dao.goods.*;
import com.linayi.entity.goods.*;
import com.linayi.enums.CategoryLevel;
import com.linayi.service.goods.CateBrandValService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public CateBrandVal getCateBrandVal(Integer categoryId, Integer brandId, Integer valueId) {
        return cateBrandValMapper.getCateBrandVal(categoryId, brandId, valueId);
    }

    @Transactional
    @Override
    public void addCateBrandVal(String categoryName, String brandName, String attrStr) {
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
                }
            }
        }
    }
}
