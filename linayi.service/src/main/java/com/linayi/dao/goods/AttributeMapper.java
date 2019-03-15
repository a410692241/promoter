package com.linayi.dao.goods;

import com.linayi.entity.goods.Attribute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AttributeMapper {
	
	/**
	 * 增加一条数据
	 * @param attribute
	 */
    void insert(Attribute attribute);
    /**
     * 编辑数据
     * @param attribute
     */
    void updateAttributeByattributeId(Attribute attribute);
    
    int insertSelective(Attribute record);

    List<Attribute> getAttributes();

    Attribute getAttributeByName(String attrName);
    
    /**
     * 查询属性列表
     * @return 成功返回属性列表 否则返回null
     */
    List<Attribute> selectAttributeList(Attribute attribute);
    
    /**
     * 通过id查询属性
     * @return 成功返回属性列表 否则返回null
     */
    Attribute selectAttributeById(@Param("attributeId") Integer attributeId);
    
    /**
     * 通过id得到数据
     * @param attributeId
     * @return
     */
    Attribute get(@Param("attributeId") Integer attributeId);
    
    /**
     * 通过id删除数据
     * @param attributeId
     * @return
     */
    Object delete(@Param("attributeId") Integer attributeId);

    /**
     * 查询商品所有属性含义属性值
     * @param goodsSkuId
     * @return
     */
    List<Attribute> getAttributesList(Long goodsSkuId);
}