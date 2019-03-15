package com.linayi.service.goods.impl;

import com.linayi.dao.goods.AttributeValueMapper;
import com.linayi.entity.goods.Attribute;
import com.linayi.entity.goods.AttributeValue;
import com.linayi.enums.NormalRemoved;
import com.linayi.service.goods.AttributeValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttributeValueServiceImpl implements AttributeValueService {

    @Resource
    private AttributeValueMapper attributeValueMapper;

    @Override
    public List<AttributeValue> getAttributeValueByCaAndBr(Integer categoryId, Integer brandId) {
        return attributeValueMapper.getAttributeValueByCaAndBr(categoryId,brandId);
    }

    @Override
    public List<AttributeValue> getAttrValsByAttrId(Integer attributeId) {
        return attributeValueMapper.getAttributeValue(attributeId, null,null);
    }

    @Override
    public AttributeValue addAttributeValue(AttributeValue attributeValue) {
        attributeValueMapper.insert(attributeValue);
        return attributeValue;
    }

    @Override
    public AttributeValue getAttributeValue(Integer attributeId, String attrVal) {
        List<AttributeValue> attributeValue = attributeValueMapper.getAttributeValue(attributeId, attrVal,null);
        if (attributeValue != null && attributeValue.size() > 0){
            return attributeValue.get(0);
        }
        return null;
    }

    @Override
    public AttributeValue getAttrValsByAttrValId(Integer attrValueId) {
        List<AttributeValue> attributeValue = attributeValueMapper.getAttributeValue(null, null, attrValueId);
        if (attributeValue != null && attributeValue.size() > 0){
            return  attributeValue.get(0);
        }
        return null;
    }

	@Override
	public List<AttributeValue> selectAttrValueList(AttributeValue attributeValue) {
		return attributeValueMapper.selectAttrValueList(attributeValue);
	}

	@Override
	public Integer delete(Integer valueId) {
		return attributeValueMapper.deleteById(valueId);
	}

	@Override
	public void update(AttributeValue attributeValue) {
		
		attributeValueMapper.updateById(attributeValue);
	}

	@Override
	public void add(AttributeValue attributeValue) {
		attributeValueMapper.add(attributeValue);
	}
	
}
