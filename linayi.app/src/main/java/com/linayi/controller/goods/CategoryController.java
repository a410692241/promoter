package com.linayi.controller.goods;

import java.util.List;
import java.util.Map;

import com.linayi.entity.order.Orders;
import com.linayi.util.ParamValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.linayi.entity.goods.Category;
import com.linayi.exception.ErrorType;
import com.linayi.service.goods.CategoryService;
import com.linayi.util.ResponseData;

@Controller
@RequestMapping("/goods/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//根据商品分类的父类id
	@RequestMapping("/showCategory.do")
	@ResponseBody
	public ResponseData findTwoCategory(@RequestBody Map<String, Object> param){
		ParamValidUtil<Category> pvu = new ParamValidUtil<>(param);
		//pvu.Exist("accessToken","parentId");
		Category category = pvu.transObj(Category.class);
		ResponseData rr;
		try {
			if(category.getParentId()==null||category.getParentId().equals("")){
				category.setParentId(-1);
			}
			List<Category> findAllCategory = categoryService.findAllCategory(category.getParentId());
			rr = new ResponseData(findAllCategory);
			return rr;
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR);
		}
	}

	//返回所有分类的数据
	@RequestMapping("/showAllCategory.do")
	@ResponseBody
	public ResponseData showCategoryByParentId(){
		ResponseData rr;
		try {
			List<Category> list = categoryService.allCategory();
			rr = new ResponseData(list);
			return rr;
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR);
		}
	}
	
	//根据品牌id获取分类
	@RequestMapping("/searchCategory.do")
	@ResponseBody
	public Object findCategoryByBrandId(@RequestBody Map<String, Object> param){

	
    	try {
    		List<Category> categiryList = categoryService.getCategoryByBrandId((Integer)param.get("brandId"));
    		return new ResponseData(categiryList);

    	} catch (Exception e) {
    		return new ResponseData(ErrorType.SYSTEM_ERROR);

		}
    	

	}
}
