package com.linayi.dao.goods;

import com.linayi.entity.goods.AttributeValue;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface AttributeValueMapper {
	 /**
     * 查询属性集合
     * @param attributeValue
     * @return
     */
	List<AttributeValue> selectAttrValueList(AttributeValue attributeValue);
	
	AttributeValue selectAttrValueById(Integer valueId);
	
    int insert(AttributeValue record);

    int insertSelective(AttributeValue record);

    List<AttributeValue> getAttributeValueByCaAndBr(@Param("categoryId") Integer categoryId, @Param("brandId") Integer brandId);

    List<AttributeValue> getAttributeValue(@Param("attributeId") Integer attributeId, @Param("value") String value,@Param("valueId") Integer valueId);
    
   
    
	 /**
     * 根据属性值id删除属性值
     * @param valueId
	 * @return 
     */
    Integer deleteById(Integer valueId);
	
	/**
	 * 修改属性值
	 * @param valueId
	 */
	void updateById(AttributeValue valueId);
	
	/**
	 * 添加属性
	 * @param attributeValue
	 */
	void add(AttributeValue attributeValue);
}