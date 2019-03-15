package com.linayi.controller.supermarket;


import java.util.List;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.linayi.entity.area.Area;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.service.area.AreaService;
import com.linayi.service.supermarket.SupermarketService;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;

@Controller
@RequestMapping("/community/supermarket")
public class supermarketController {
	@Resource
	private SupermarketService supermarketService;
	@Resource
	private AreaService areaService;
	
	@RequestMapping("/list.do")
    @ResponseBody


    public Object userList(Supermarket supermarket,Integer communityId,String type) {
        List<Supermarket> list = supermarketService.selectAll(supermarket,communityId,type);
        if(list.size()>0){
        	 PageResult<Supermarket> pageResult = new PageResult<Supermarket>(list, supermarket.getTotal());
             return pageResult;
        }
       return list;
    }
	
	@RequestMapping("/save.do")
    @ResponseBody
    public Object save(@RequestParam(value = "logoFile",required =false) CommonsMultipartFile logoFile,MultipartHttpServletRequest request,Supermarket supermarket) {
        if (supermarket.getSupermarketId() !=  null) {
        	this.supermarketService.updateSupermarketBysupermarketId(logoFile,supermarket);
        	return new ResponseData(supermarket);
        } else {
            this.supermarketService.insertSupermarket(logoFile,supermarket);
            return new ResponseData(true);
        }
    }
	
	@RequestMapping("/edit.do")
    @ResponseBody
    public Object get(Integer supermarketId) {
		Supermarket list = supermarketService.selectSupermarketBysupermarketId(supermarketId);
        return new ResponseData(list);
    }
	
	/**
	 * 根据id查超市
	 * @param supermarketId
	 * @return 
	 */
	@RequestMapping("/show.do")
    public Object show(Integer supermarketId) {
		ModelAndView mv = new ModelAndView("jsp/community/SupermarketShow");
		Supermarket supermarket = supermarketService.selectSupermarketBysupermarketId(supermarketId);
		mv.addObject("supermarket",supermarket);
        return mv;
    }
	
	 /**
	  * 删除用户信息
	  * @param supermarketId
	  * @return
	  */
    @Transactional(rollbackFor = Throwable.class)
    @ResponseBody
    @RequestMapping("/logicDelete.do")
    public ResponseData delect(Integer supermarketId) {
    	 supermarketService.deleteSupermarketrBysupermarketId(supermarketId);
        return new ResponseData(true);
    }
    
    /**
     * 获取省
     * @return
     */
  @RequestMapping("/getProvince.do")
	@ResponseBody
	public Object getProvince() {
		List<Area> areaList = areaService.getProvince();
		return new ResponseData(areaList);
	}
  
  	/**
  	 * 获取市区
  	 * @param area
  	 * @return
  	 */
	@RequestMapping("/getAreaInfo.do")
	@ResponseBody
	public Object getAreaInfo(Area area) {
		List<Area> areaList = areaService.listAreaInfo(area);
		return new ResponseData(areaList);
	}
}
