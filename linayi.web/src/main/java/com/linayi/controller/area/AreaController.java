package com.linayi.controller.area;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.linayi.service.community.CommunityService;
import com.linayi.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linayi.entity.area.Area;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.community.Community;
import com.linayi.service.area.AreaService;
import com.linayi.service.area.SmallCommunityService;
import com.linayi.util.ResponseData;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/area")
public class AreaController {
	@Resource
	private AreaService areaService;
	@Resource
	private SmallCommunityService smallCommunityService;
	@Autowired
	private CommunityService communityService;

	@RequestMapping("/edit.do")
	@ResponseBody
	public ResponseData edit(Integer smallCommunityId){
		ResponseData rr =null;
		
			Map<String,Object> result= new HashMap<>();
			
			Map<String,List<Area>> allAreaList = areaService.getArea();
			//获取所有的社区
			Community community = new Community();
			List<Community> list =smallCommunityService.getAllCommunity(community );
			if(smallCommunityId == null){
				result.put("areaList", allAreaList);
				result.put("communityList", list);
				rr = new ResponseData(result);
				return rr ;
			}
			
			//通过小区ID获取对应的小区信息
			SmallCommunity smallCommunity = new SmallCommunity();
			Area provinceCityAndRegion = new Area();
			if(smallCommunityId !=null){
				smallCommunity.setSmallCommunityId(smallCommunityId);
				smallCommunity = smallCommunityService.getSmallCommunityBySmallCommunityId(smallCommunity);
				community.setCommunityId(smallCommunity.getCommunityId()); 
				if(smallCommunityService.getAllCommunity(community).size()>0){
					community = smallCommunityService.getAllCommunity(community).get(0);
				}
				//通过小区ID获取对应的省市区
				provinceCityAndRegion = smallCommunityService.provinceCityAndRegion(smallCommunity.getAreaCode());
			}
			result.put("communityList", list);
			result.put("areaList", allAreaList);
			result.put("communityLocation", smallCommunity);
			result.put("provinceCityAndRegion", provinceCityAndRegion);
			result.put("community", community);
			rr = new ResponseData(result);
			return rr ;
	}

	@RequestMapping("/list.do")
	@ResponseBody
	public Object list(Area area) {
		List<Area> allStreet = areaService.getAllStreet(area);
		PageResult<Area> areaPageResult = new PageResult<>(allStreet, area.getTotal());
		return areaPageResult;
	}

	@GetMapping("/showCommunity.do")
	public Object showCommunity(String code) {
		ModelAndView modelAndView = new ModelAndView("jsp/community/AreaCommunity");
		Area area = areaService.getByPrimaryKey(code);
		Integer communityId = area.getCommunityId();
		modelAndView.addObject("communityId", communityId);
		modelAndView.addObject("code", code);
		return modelAndView;
	}

	@GetMapping("/bindCommunity.do")
	@ResponseBody
	public Object bindCommunityList(Area area) {
		communityService.bindCommunity(area);
		return new ResponseData("绑定成功");
	}
	@GetMapping("/add.do")
	public Object add() {
		ModelAndView modelAndView = new ModelAndView("jsp/community/AreaEdit");
		Map<String,Object> result= new HashMap<>();
		Map<String,List<Area>> allAreaList = areaService.getArea();
		result.put("areaList", allAreaList);
		return modelAndView;
	}
	@PostMapping("/getArea.do")
	@ResponseBody
	public Object getArea() {
		Map<String,Object> result= new HashMap<>();
		Map<String,List<Area>> allAreaList = areaService.getArea();
		result.put("areaList", allAreaList);
		return new ResponseData(result);
	}
	@PostMapping("/save.do")
	@ResponseBody
	public Object save(Area area) {
		communityService.save(area);
		return new ResponseData("添加成功!");
	}

}
