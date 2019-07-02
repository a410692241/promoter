package com.linayi.service.community.impl;


import com.linayi.dao.area.AreaMapper;
import com.linayi.dao.area.SmallCommunityMapper;
import com.linayi.dao.community.CommunityMapper;
import com.linayi.entity.area.Area;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.community.Community;
import com.linayi.enums.SourceType;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.area.SmallCommunityService;
import com.linayi.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class CommunityServiceImpl implements CommunityService {
    @Resource
    private CommunityMapper communityMapper;
    @Autowired
    private SmallCommunityService smallCommunityService;
    @Resource
    private AreaMapper areaMapper;
    @Autowired
    private SmallCommunityMapper smallCommunityMapper;


    @Override
    public List<Community> getCommunityList(Community community) {
        List<Community> list = communityMapper.getCommunityList(community);
        //为每一个社区的地址 增加省市区街道
        for(int i=0;i<list.size();i++){
        	String areaCode = list.get(i).getAreaCode();
        	if(!"".equals(areaCode) && areaCode != null){
        		String areaName = getAreaNameByAreaCode(areaCode);
            	String address = list.get(i).getAddress();
            	list.get(i).setAddress(areaName+address);	
        	}
        }
        return list;
    }

    @Override
    public Community getCommunity(Community community) {
    	community = communityMapper.getCommunity(community);
    	String areaCode = community.getAreaCode();
    	if(!"".equals(areaCode) && areaCode != null){
    		String areaName = getAreaNameByAreaCode(areaCode);
    		community.setAreaName(areaName);
    	}
        return community;
    }

    @Override
    public Community addOrUpdateCommunity(Community community) {
        if (community.getCommunityId() != null) {
            Date date = new Date();
            community.setUpdateTime(date);
            communityMapper.updateCommunity(community);
        } else {
            Date createTime = new Date();
            community.setStatus("NORMAL");
            community.setCreateTime(createTime);
            communityMapper.insert(community);
        }
        return community;
    }


    //解绑网点修改状态
    @Override
    @Transactional
    public Integer removeCommunity(Integer communityId) {
        return communityMapper.removeCommunity(communityId);
    }

    @Override
    public List<Community> getCommunityByAreaCode(Community community) throws Exception {
        String code = community.getAreaCode();
        List<SmallCommunity> smallCommunities = smallCommunityService.getSmallCommunityByAreaCode(code);
        List<Integer> communityIdList = smallCommunities.stream().collect(Collectors.mapping(SmallCommunity::getCommunityId, Collectors.toList()));
        List<Community> communityLists = communityMapper.getCommunityByCommunityIdList(communityIdList);
        //对communityId去重
        List<Community> uniqueCommunityList = communityLists.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getCommunityId() + ";" + o.getCommunityId()))), ArrayList::new));
        return uniqueCommunityList;
    }
    
    /** 通过areaCode获取完整的省市区街道名
     * @param areaCode
     * @return
     */
    private String getAreaNameByAreaCode(String areaCode){
    	//获取街道名
		String streetName = areaMapper.getNameByCode(areaCode);
		//获取省市区区名
		String regionName = areaMapper.getNameByCode(areaCode.substring(0, 8));
		String cityName = areaMapper.getNameByCode(areaCode.substring(0, 6));
		String provinceName = areaMapper.getNameByCode(areaCode.substring(0, 4));
		//拼接
		String areaName =provinceName+cityName+regionName+streetName;
    	return areaName;
    }

    @Override
    public Community getCommunityById(Integer communityId) {
        Community community = new Community();
        community.setCommunityId(communityId);
        return communityMapper.getCommunity(community);
    }

    @Override
    public Integer getcommunityIdByuserIdInDefaultAddress(Integer userId) {
        Integer communityId = communityMapper.getcommunityIdByuserIdInDefaultAddress(userId);
        if (communityId == null) {
            throw new BusinessException(ErrorType.UNFILLED_SHIPPING_ADDRESS);
        }
        return communityId;
    }

    @Override
    @Transactional
    public void bindCommunity(Area area) {
        //更新与该街道有关,而且是用户端添加的小区到新网点
        SmallCommunity smallCommunity = new SmallCommunity();
        smallCommunity.setAreaCode(area.getCode());
        smallCommunity.setCommunityId(area.getCommunityId());
        smallCommunity.setSource(SourceType.USER.name());
        smallCommunity.setUpdateTime(new Date());
        smallCommunityMapper.updateSmallCommunity(smallCommunity);
        //更新街道绑定的网点
        areaMapper.updateByPrimaryKey(area);
    }

    @Override
    public void save(Area area) {
        String parent = area.getCode();
        //使用上级编号生成子集编号
        Area areaParam = new Area();
        areaParam.setParent(parent);
        //避免重复添加街道
        List<Area> areaList = areaMapper.query(areaParam);
        List<String> nameList = areaList.stream().collect(Collectors.mapping(Area::getName, Collectors.toList()));
        if (nameList.contains(area.getName())) {
            throw new BusinessException(ErrorType.DO_NOT_ADD_STREETS_REPEATEDLY);
        }
        String newCode;
        //存在已知最大code则+1,否则在父code上加000001
        if (areaList != null && areaList.size() > 0) {
            areaList.sort((a, b) -> (int) (Long.parseLong(b.getCode()) - Long.parseLong(a.getCode())));
            newCode = Long.parseLong(areaList.stream().findFirst().orElse(null).getCode()) + 1 + "";
        } else {
            newCode = parent + "000001";
        }
        area.setParent(parent);
        short level = 4;
        area.setLevel(level);
        area.setCode((newCode));
        area.setCreateTime(new Date());
        areaMapper.insert(area);
    }
}
