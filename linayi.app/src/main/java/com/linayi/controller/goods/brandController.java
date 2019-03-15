package com.linayi.controller.goods;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linayi.entity.goods.Brand;
import com.linayi.entity.goods.Category;
import com.linayi.exception.ErrorType;
import com.linayi.service.goods.BrandService;
import com.linayi.util.ResponseData;

@Controller
@RequestMapping("/brand/brand")
public class brandController {
	
	@Autowired
	private BrandService brandService;
	
	
	//根据品牌名模糊查找品牌(已测试)
	@RequestMapping("/searchBrand.do")
	@ResponseBody
	public ResponseData showBrandByBrandName(@RequestBody Map<String, Object> param) {
		
		String brandName =  param.get("brandName").toString();
		Integer currentPage = (Integer)param.get("currentPage");
    	try {
    		Brand brand = new Brand();
    		brand.setName(brandName);
    		brand.setPageSize(4);
    		brand.setCurrentPage(currentPage);
			List<Brand> brandList = brandService.getBrandByName(brand );
    		return new ResponseData(brandList);
    	} catch (Exception e) {
    		return new ResponseData(ErrorType.SYSTEM_ERROR);
    		
		}
    	

	}

}
