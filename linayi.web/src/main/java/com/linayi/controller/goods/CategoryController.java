package com.linayi.controller.goods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.linayi.entity.goods.Category;
import com.linayi.service.goods.CategoryService;
import com.linayi.util.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/goods/category")
public class CategoryController {

	@Resource
	private CategoryService categoryService;

	@RequestMapping("/list.do")
	@ResponseBody
	public List<Category> getCategoryList() {
		Short Level = 1;
		List<Category> category = categoryService.getCatergoysList(Level);
		return category;
	}

	@RequestMapping("/addCatergoy.do")
	public ModelAndView addCatergoy(Category categorys) {
		ModelAndView mv = new ModelAndView("jsp/goods/CategoryEdit");
		Category category = this.categoryService.getCategoryById(categorys.getParentId());
		category.setUpdateTime(null);
		if(category != null){
			category.setParentName(category.getName());
			category.setName(null);
			category.setLogo(categorys.getLogo());
			//当操作是add,categoryUpdateKey是2
			mv.addObject("categoryUpdateKey",2);
			mv.addObject("category", category);
		}
		return mv;
	}

	@RequestMapping("/updateCatergoy.do")
	public ModelAndView updateCatergoy(Category categorys) {
		ModelAndView mv = new ModelAndView("jsp/goods/CategoryEdit");
		Category category = this.categoryService.getCategoryById(categorys.getCategoryId());
		mv.addObject("category", category);
		//当操作是update,categoryUpdateKey是1.
		mv.addObject("categoryUpdateKey",1);
		return mv;
	}

	@RequestMapping(value="/save.do",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public Object save(@RequestParam("category") String categoryStr,@RequestParam("logo")MultipartFile file){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		TypeToken<Category> type = new TypeToken<Category>() {};
		Category category = gson.fromJson(categoryStr, type.getType());
		//添加
		String response = null;
		Short level = 5;
		if(category.getParentName()!=null){
			if(level.equals(category.getLevel())){
				return new ResponseData("F",null,"不支持五级菜单").toString();
			}
			response = categoryService.insertCategory(category,file);
		}else{//编辑
			response = categoryService.updateCategory(category,file);
		}
		return response;
	}

	@RequestMapping(value = "/delete.do",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public Object delete(String categoryIdList) {
		if(StringUtils.isNotBlank(categoryIdList)){
			String[] categoryId = categoryIdList.split(",");
			categoryService.deleteCategory(categoryId);
			return new ResponseData("操作成功！").toString();
		}
		return new ResponseData("操作失败！").toString();
	}

}
