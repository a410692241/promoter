package com.linayi.service.goods;

import com.linayi.entity.goods.Attribute;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

public interface AttributeService {
    /**
     * 查询所有的属性
     * @return
     */
    List<Attribute> getAttributes();

    /**
     * 通过名字查找属性
     * @param attrName
     * @return
     */
    Attribute getAttributeByName(String attrName);
    
    /**
     * 查询所有的属性列表
     * @param attribute
     * @return 成功返回属性列表，否则返回null
     */
    List<Attribute> selectAttributeList(Attribute attribute);
    
    /**
     * 保存属性
     * @param attribut 规格属性
     * @return
     */
	Object save(Attribute attribute);
	
	/**
	 * 增加一条数据
	 * @param record
	 * @return
	 */
    void insert(Attribute attribute);
    /**
     * 编辑数据
     * @param attribute
     */
    void updateAttributeByattributeId(Attribute attribute);
    
    /**
     * 通过id查找数据
     * @param attributeId 属性id
     * @return
     */
    Attribute selectAttributeById(@Param("valueId") Integer valueId,HttpServletRequest request);
    
    /**
     * 通过id得到数据
     * @param attributeId
     * @return
     */
    Attribute get(@Param("valueId") Integer valueId);
    
    /**
     * 通过id删除数据
     * @param attributeId
     * @return
     */
    Object delete(@Param("valueId") Integer valueId);
}
