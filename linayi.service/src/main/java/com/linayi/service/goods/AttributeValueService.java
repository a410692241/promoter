package com.linayi.service.goods;

import com.linayi.entity.goods.AttributeValue;

import java.util.List;

public interface AttributeValueService {

    /*通过分类Id和品牌Id查询所有的属性值*/
    List<AttributeValue> getAttributeValueByCaAndBr(Integer categoryId, Integer brandId);
    /*根据属性Id获取所有属性值*/
    List<AttributeValue> getAttrValsByAttrId(Integer attributeId);
    /*新增属性值*/
    AttributeValue addAttributeValue(AttributeValue attributeValue);

    /**
     * 查询属性值
     * @param attributeId
     * @param attrVal
     * @return
     */
    AttributeValue getAttributeValue(Integer attributeId, String attrVal);

    /**
     * 根据属性值id查找属性值
     * @param attrValueId
     * @return
     */
    AttributeValue getAttrValsByAttrValId(Integer valueId);
    
    /**
     * 查询所有
     * @param attributeValue
     * @return
     */
    List<AttributeValue> selectAttrValueList(AttributeValue attributeValue);
    
    /**
     * 根据属性值valueId删除属性值
     * @param valueId
     */
    Integer delete(Integer valueId);
    
    
	
	/**
	 * 修改属性值
	 * @param attributeValue
	 */
	void update(AttributeValue valueId);
	
	/**
	 * 添加属性
	 * @param attributeValue
	 */
	void add(AttributeValue attributeValue);
	
}
