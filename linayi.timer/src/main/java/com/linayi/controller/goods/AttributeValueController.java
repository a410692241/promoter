package com.linayi.controller.goods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linayi.entity.goods.Attribute;
import com.linayi.entity.goods.AttributeValue;
import com.linayi.service.goods.AttributeValueService;
import com.linayi.util.ResponseData;

@Controller
@RequestMapping("/goods/attrValue")
public class AttributeValueController {
	
	@Resource
	private AttributeValueService attributeValueService;

	/**
	 * 查询列表,分页
	 * @param request
	 * @return
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public Object attrValueList(AttributeValue attributeValue) {
		return this.attributeValueService.selectAttrValueList(attributeValue);
	}
	
	/**
	 * 新增/更新attrValue
	 * @param attrValue
	 * @return
	 */
	@RequestMapping("/save.do")
	@ResponseBody
	public Object save( AttributeValue attributeValue ) {
		attributeValueService.add(attributeValue);
		return new ResponseData("操作成功！");
		
	}
	
	/**
	 * 通过主键删除
	 * @param attrValueId
	 * @return
	 */
	@RequestMapping("/delete.do")
	@ResponseBody
	public Object delete(Integer attributeValueId) {
		this.attributeValueService.delete(attributeValueId);
		return new ResponseData(true);
	}

	/**
	 * 通过主键获取对象
	 * @param attrValueId
	 * @return
	 */
	@RequestMapping("/get.do")
	@ResponseBody
	public Object get(Integer attributeValueId) {
		return this.attributeValueService.getAttrValsByAttrValId(attributeValueId);
	}

	/**
	 * 编辑页面
	 * @param attrValueId
	 * @return
	 */
	@RequestMapping("/edit.do")
	public ModelAndView edit(Integer attributeValueId) {
		ModelAndView mv = new ModelAndView("jsp/goods/AttributeEdit");
		if (attributeValueId !=null) {
			AttributeValue attributeValue = attributeValueService.getAttrValsByAttrValId(attributeValueId);
			mv.addObject("attributeValue", attributeValue);
		}
		return mv;
	}
	
}
