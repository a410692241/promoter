package com.linayi.controller.goods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linayi.entity.goods.Attribute;
import com.linayi.entity.goods.AttributeValue;
import com.linayi.service.goods.AttributeService;
import com.linayi.service.goods.AttributeValueService;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;

@Controller
@RequestMapping("/goods/attribute")
public class AttributeController {
	
	@Resource
	private AttributeService attributeService;
	@Resource
	private AttributeValueService attributeValueService;
	
	/**
	 * 查询所有属性列表
	 * @param attribute
	 * @return 成功返回属性列表 否则返回null
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public Object attributeList(Attribute attribute) {
		List<Attribute> page = attributeService.selectAttributeList(attribute);
		System.out.println(page.size());
		PageResult<Attribute> pageResult = new PageResult<Attribute>(page, attribute.getTotal());
		setAttrValueList(pageResult.getRows());
		return pageResult;
	}
	
	/**
	 * 通过主键删除
	 * @param valueId
	 * @return
	 */
	@RequestMapping("/delete.do")
	@ResponseBody
	public Object delete(Integer valueId) {
		return attributeService.delete(valueId);

	}

	/**
	 * 通过主键获取对象
	 * @param valueId
	 * @return
	 */
	@RequestMapping("/get.do")
	@ResponseBody
	public Object get(Integer valueId) {
		return this.attributeService.get(valueId); 
	}
	
	/**
	 * 保存 新增/更新attribute
	 * @param attribute
	 * @return
	 */
	@RequestMapping("/save.do")
	@ResponseBody
	public Object save( @RequestBody Attribute attribute) {
		return attributeService.save( attribute );
	}
	
	/**
	 * 编辑页面
	 * @param attributeId
	 * @return
	 */
	@RequestMapping("/edit.do")
	public ModelAndView edit(Integer attributeId,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("jsp/goods/AttributeEdit");
		if (attributeId !=null) {
			Attribute attribute = attributeService.selectAttributeById(attributeId,request);
			List<Attribute> list = new ArrayList<>();
			list.add(attribute);
			setAttrValueList(list);
			mv.addObject("attribute", list.stream().findFirst().orElse(new Attribute()));
		}
		return mv;
	}
	
	
	/**
	 * 获取属性ID集合
	 */
	private final static List<Integer> getAttributeIdList( List<Attribute> attributeList){
		if( attributeList == null || attributeList.isEmpty() ){
			return new ArrayList<>();
		}
		return attributeList.parallelStream()//
					.map( Attribute::getAttributeId )//
					.collect( Collectors.toList() );
	}
	
	/***
	 * 设置属性值
	 * @param
	 */
	private final void setAttrValueList( List<Attribute> attributeList){
		List<Integer> attributeIdList = getAttributeIdList( attributeList );
		if( attributeIdList.isEmpty() ){
			return;
		}
		AttributeValue attributeValue = new AttributeValue();
		
		attributeValue.setAttributeIdList( attributeIdList );
		Map<Integer, List<AttributeValue>> valueGroups = attributeValueService.selectAttrValueList( attributeValue  ).parallelStream().collect( Collectors.groupingBy( AttributeValue::getAttributeId ) );
		
		attributeList.forEach( aItem->{
			if( aItem.getAttributeId() != null ){
				List<AttributeValue> values = valueGroups.getOrDefault( aItem.getAttributeId(), Collections.emptyList() );
				if( values != null  ){
					aItem.setAttributeValueList(values);
				}
			}
		} );
	}
}
