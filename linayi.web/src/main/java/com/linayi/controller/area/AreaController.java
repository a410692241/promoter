package com.linayi.controller.area;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linayi.entity.area.Area;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.community.Community;
import com.linayi.service.area.AreaService;
import com.linayi.service.area.SmallCommunityService;
import com.linayi.util.ResponseData;

@Controller
@RequestMapping("/area")
public class AreaController {
	@Resource
	private AreaService areaService;
	@Resource
	private SmallCommunityService smallCommunityService;
	
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

	
}
