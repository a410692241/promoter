package com.linayi.service.goods.impl;

import com.linayi.dao.goods.AttributeMapper;
import com.linayi.dao.goods.AttributeValueMapper;
import com.linayi.entity.goods.Attribute;
import com.linayi.entity.goods.AttributeValue;
import com.linayi.exception.ErrorType;
import com.linayi.service.goods.AttributeService;
import com.linayi.util.ResponseData;
import com.squareup.okhttp.internal.spdy.ErrorCode;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttributeServiceImpl implements AttributeService {

	@Resource
	private AttributeMapper attributeMapper;
	@Resource
	private AttributeValueMapper attributeValueMapper;

	@Override
	public List<Attribute> getAttributes() {
		return attributeMapper.getAttributes();
	}

	@Override
	public Attribute getAttributeByName(String attrName) {
		return attributeMapper.getAttributeByName(attrName);
	}

	@Override
	public List<Attribute> selectAttributeList(Attribute attribute) {
		return attributeMapper.selectAttributeList(attribute);
	}

	@Transactional
	@Override
	public Object save(Attribute attribute) {

		List<AttributeValue> attributeValueList = attribute.getAttributeValueList();
		// 依据数据有没有valueId，分别对数据进行更新或新增操作
		if (attributeValueList != null && attributeValueList.size() > 0 && attribute.getName() != null && attribute.getName() != "") {
			for (AttributeValue attributeValue : attributeValueList) {
				if (attributeValue.getValueId() == null) {
					attributeValue.setStatus("NORMAL");
					attributeValue.setCreateTime(new Date());
					attributeValue.setUpdateTime(new Date());
					attributeValue.setAttributeId(attribute.getAttributeId());
					attributeValueMapper.add(attributeValue);
				}
			}
		}else {
			if(selectAttributeList(attribute).isEmpty()){
				attribute.setStatus("NORMAL");
				attribute.setCreateTime(new Date());
				attribute.setUpdateTime(new Date());

				this.insert(attribute);
			}else{
				return new ResponseData("F","属性已存在！").toString();
			}
		}
		return new ResponseData("操作成功！");

	}

	@Override
	public void insert(Attribute attribute) {
		attributeMapper.insert(attribute);
	}

	@Override
	public void updateAttributeByattributeId(Attribute attribute) {
		attributeMapper.updateAttributeByattributeId(attribute);
	}

	@Override
	public Attribute selectAttributeById(Integer attributeId, HttpServletRequest request) {
		return attributeMapper.selectAttributeById(attributeId);
		
	}

	@Override
	public Attribute get(Integer valueId) {
		return attributeMapper.get(valueId);
	}

	@Override
	public Object delete(Integer valueId) {
		return attributeMapper.delete(valueId);
	}
	
}
