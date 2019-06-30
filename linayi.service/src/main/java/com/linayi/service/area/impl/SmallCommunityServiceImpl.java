package com.linayi.service.area.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.linayi.dao.user.UserMapper;
import com.linayi.entity.user.User;
import com.linayi.enums.RemoveType;
import com.linayi.enums.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linayi.dao.area.AreaMapper;
import com.linayi.dao.area.SmallCommunityMapper;
import com.linayi.dao.community.CommunityMapper;
import com.linayi.entity.area.Area;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.community.Community;
import com.linayi.service.area.SmallCommunityService;
import org.springframework.transaction.annotation.Transactional;
import sun.security.x509.UniqueIdentity;

@Service
public class SmallCommunityServiceImpl implements SmallCommunityService {

	@Resource
	private SmallCommunityMapper smallCommunityMapper;
	@Resource
	private AreaMapper areaMapper;
	@Resource
	private CommunityMapper communityMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	public int insert(SmallCommunity record) {
		Date date = new Date();
		record.setCreateTime(date);
		record.setStatus("NORMAL");
		return smallCommunityMapper.insert(record);
	}

	@Override
	public Area provinceCityAndRegion(String areaCode) {

		Area areaGradGradParent = new Area();
		areaGradGradParent.setCode(areaCode.substring(0, 4));
		areaGradGradParent = areaMapper.getAreaInfo(areaGradGradParent);

		Area areaGradParent = new Area();
		areaGradParent.setCode(areaCode.substring(0, 6));
		areaGradParent = areaMapper.getAreaInfo(areaGradParent);

		Area areaParent = new Area();
		areaParent.setCode(areaCode.substring(0, 8));
		areaParent = areaMapper.getAreaInfo(areaParent);

		Area area = new Area();
		area.setCode(areaCode);
		area = areaMapper.getAreaInfo(area);

		ArrayList<Area> areaList = new ArrayList<>();
		ArrayList<Area> parentList = new ArrayList<>();
		ArrayList<Area> areaGradList = new ArrayList<>();

		areaList.add(area);
		areaParent.setChild(areaList);
		parentList.add(areaParent);
		areaGradParent.setChild(parentList);
		areaGradList.add(areaGradParent);
		areaGradGradParent.setChild(areaGradList);
		return areaGradGradParent;
	}

	@Override
	public List<SmallCommunity> getSmallCommunity(String streetName, SmallCommunity smallCommunity, Integer communityId,
			String type) {

		List<SmallCommunity> smallCommunityList = new ArrayList<>();
		smallCommunityList = smallCommunityList(streetName, smallCommunity, communityId,type);
		
		// 遍历小区集合
		for (int i = 0; i < smallCommunityList.size(); i++) {
			String code = smallCommunityList.get(i).getAreaCode();
			streetName = getAreaNameByAreaCode(code);
			Community community = new Community();
			community.setCommunityId(smallCommunityList.get(i).getCommunityId());

			if (communityMapper.getCommunityList(community).size() > 0) {
				community = communityMapper.getCommunityList(community).get(0);
			}
			// 判断每个小区所属的社区是否为空
			if (community != null) {
				String communityName = community.getName();
				smallCommunityList.get(i).setCommunityName(communityName);
				smallCommunityList.get(i).setType("bind");
			}
			smallCommunityList.get(i).setAreaName(streetName);

			if (smallCommunityList.get(i).getCommunityName() == null) {
				smallCommunityList.get(i).setType("unBind");
			}
			if (smallCommunityList.get(i).getCommunityId() == null) {
				smallCommunityList.get(i).setCommunityName("");
				smallCommunityList.get(i).setType("unBind");
			}
			// 判断前端传过来的的社区ID是否为空，不为空，跟每个小区所属的社区Id对比
			if (communityId != null) {
				if (smallCommunityList.get(i).getCommunityId() != null
						&& smallCommunityList.get(i).getCommunityId().equals(communityId)) {
					smallCommunityList.get(i).setType("bind");
				} else {
					smallCommunityList.get(i).setType("unBind");
				}
			}
		}

		//添加操作人
		List<User> users = userMapper.selectUserList(new User());
		Map<Integer, List<User>> userMap = users.stream().collect(Collectors.groupingBy(User::getUserId));
		smallCommunityList.stream().forEach(item -> {
			Integer creatorId = item.getCreatorId();
			if(creatorId != null){
				User user = userMap.get(creatorId).stream().findFirst().orElse(null);
				item.setMobile(user.getMobile());
			}
		});
		return smallCommunityList;

	}

	/** 获得全部小区信息*/
	private List<SmallCommunity> smallCommunityList(String streetName, SmallCommunity smallCommunity,
			Integer communityId,String type) {
		List<SmallCommunity> smallCommunityList = new ArrayList<>();
		// 通过街道名字搜索街道code
		List<String> areaCodeList = null;
		if (streetName != null && !"".equals(streetName)) {
			areaCodeList = areaMapper.getStreetCodeByStreetName(streetName);
			if (areaCodeList.size() == 0) {
				return smallCommunityList;
			}
		}
		//
		List<Integer> communityIdList = null;
		if (smallCommunity.getCommunityName() != null && !"".equals(smallCommunity.getCommunityName())) {
			communityIdList = communityMapper.getCommunityIdList(smallCommunity.getCommunityName());
			if (communityIdList.size() == 0) {
				return smallCommunityList;
			}
		}
		// 通过社区名搜索社区ID
		smallCommunity.setCommunityIdList(communityIdList);
		smallCommunity.setAreaCodeList(areaCodeList);
		smallCommunity.setCommunityId(null);
		
		//在小区列表 获取绑定状态的小区的数据
		if(communityId != null){
			if("showBind".equals(type)){
				smallCommunity.setType(null);
				smallCommunity.setCommunityId(communityId);
			}
			if("showUnbind".equals(type)){
				smallCommunity.setType(null);
				communityId = -communityId;
				smallCommunity.setCommunityId(communityId);
			}
		}
		// 根据搜索条件获取小区的数量
		smallCommunityList = smallCommunityMapper.getSmallCommunityList(smallCommunity);

		return smallCommunityList;
	}

	@Override
	public List<Community> getAllCommunity(Community community) {
		return communityMapper.getCommunityList(community);
	}

	@Override
	public SmallCommunity getSmallCommunityBySmallCommunityId(SmallCommunity smallCommunity) {
		return smallCommunityMapper.getSmallCommunity(smallCommunity);
	}

	@Override
	public Integer updateSmallCommunity(SmallCommunity smallCommunity) {
		return smallCommunityMapper.updateSmallCommunity(smallCommunity);
	}

	@Override
	public Integer removeBind(SmallCommunity smallCommunity) {
		return smallCommunityMapper.removeBind(smallCommunity);
	}

	@Override
	public Integer removeSmallCommunity(Integer smallCommunityId) {
		return smallCommunityMapper.removeSmallCommunity(smallCommunityId);
	}

	@Override
	public List<SmallCommunity> getSmallCommunityByAreaCode(String areaCode) throws Exception {
		//通过获取配送区域的服务点id再在小区表获取具有这些id的小区
		List<Community> communities = communityMapper.getCommunityByAreaCode(areaCode);
		if (communities.size() == 0) {
			throw new Exception("该小区暂无服务点!");
		}
		Set<Integer> communityIdList = communities.stream().collect(Collectors.mapping(Community::getCommunityId, Collectors.toSet()));
		SmallCommunity smallCommunity = new SmallCommunity();
		smallCommunity.setCommunityIdList(new ArrayList<>(communityIdList));
		List<SmallCommunity> smallCommunityList = smallCommunityMapper.getSmallCommunityList(smallCommunity);
		return smallCommunityList;
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
	@Transactional
	public void addSmallCommunity(SmallCommunity smallCommunity) {
    	//查询该街道下属于哪个网点
		String areaCode = smallCommunity.getAreaCode();
		Area area = areaMapper.selectByPrimaryKey(areaCode);
		if(area != null){
			Integer communityId = area.getCommunityId();
			smallCommunity.setCommunityId(communityId);
		}
		smallCommunity.setSource(SourceType.USER.name());
		smallCommunity.setStatus(RemoveType.NORMAL.name());
		smallCommunity.setCreateTime(new Date());
		smallCommunity.setUpdateTime(new Date());
		smallCommunityMapper.insert(smallCommunity);
	}
}
