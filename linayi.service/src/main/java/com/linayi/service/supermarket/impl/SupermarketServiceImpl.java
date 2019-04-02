package com.linayi.service.supermarket.impl;

import com.linayi.dao.area.AreaMapper;
import com.linayi.dao.community.CommunitySupermarketMapper;
import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.entity.area.Area;
import com.linayi.entity.promoter.OpenMemberInfo;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.enums.MemberLevel;
import com.linayi.service.promoter.OpenMemberInfoService;
import com.linayi.service.supermarket.SupermarketService;
import com.linayi.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SupermarketServiceImpl implements SupermarketService {

	@Autowired
	private SupermarketMapper supermarketMapper;
	@Autowired
	private AreaMapper areaMapper;
	@Resource
	private CommunitySupermarketMapper communitySupermarketMapper;
	@Resource
	private OpenMemberInfoService openMemberInfoService;

	@Override

	public List<Supermarket> selectAll(Supermarket supermarket,Integer communityId,String type) {

		List<Integer> supermarketIdList = communitySupermarketMapper.getSupermarketIdList(communityId);

		List<Supermarket> list = new ArrayList<>();
		//通过社区Id和绑定状态获取超市信息
		if("showBind".equals(type)){
			if(supermarketIdList.size()>0){
				supermarket.setSupermarketIdList(supermarketIdList);
				list = supermarketMapper.selectSupermarketList(supermarket);
				for(int i=0;i<list.size();i++){
					list.get(i).setType("bind");
				}
				return list;
			}
			return list;
		}
		if("showUnbind".equals(type)){
			supermarket.setSupermarketIdList(supermarketIdList);
			list = supermarketMapper.selectSupermarketListTwo(supermarket);
			for(int i=0;i<list.size();i++){
				list.get(i).setType("unBind");
			}
			return list;
		}

		list = supermarketMapper.selectAll(supermarket);
		for(int i = 0;i<list.size();i++){
			Integer sId = list.get(i).getSupermarketId();

			if(supermarketIdList.size()>0){
				list.get(i).setType("bind");
			}
			list.get(i).setAreaName(selectSupermarketBysupermarketId(sId).getAreaName());
			for(int j=0;j<supermarketIdList.size();j++){
				Integer supermarketId = supermarketIdList.get(j);
				if(supermarketId !=null && sId == supermarketId){
					list.get(i).setAreaName(selectSupermarketBysupermarketId(sId).getAreaName());
					list.get(i).setType("bind");
					break;
				}else{
					list.get(i).setAreaName(selectSupermarketBysupermarketId(sId).getAreaName());
					list.get(i).setType("unBind");
				}
			}
		}
		return list;
	}

	@Override
	public void insertSupermarket(Supermarket supermarket) {
		supermarketMapper.insertSupermarket(supermarket);
	}

	@Override
	public void updateSupermarketBysupermarketId(Supermarket supermarket) {
		supermarketMapper.updateSupermarketBysupermarketId(supermarket);
	}

	@Override
	public void insertSupermarket(CommonsMultipartFile logoFile, Supermarket supermarket) {
		String realPath = null;
		if(logoFile != null){
			//需要上传logo图片
			try {
				realPath = ImageUtil.handleUpload(logoFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		supermarket.setStatus("NORMAL");
		supermarket.setCreateTime(new Date());
		supermarket.setUpdateTime(new Date());
		supermarket.setLogo(realPath);
		insertSupermarket(supermarket);
	}

	@Override
	public Supermarket selectSupermarketBysupermarketId(Integer supermarketId) {
		Supermarket supermarket = supermarketMapper.selectSupermarketBysupermarketId(supermarketId);

		try {
			supermarket.setLogo(ImageUtil.dealToShow(supermarket.getLogo()));
			//通过areacode设置地区名
			Area area =getAreaNameByCode(supermarket.getAreaCode());
			StringBuilder sb = new StringBuilder();
			sb.append(area.getName()+";");
			if(area != null && area.getName() != null){
				while(true){
					area = getAreaNameByCode(area.getParent());
					if(area != null && !("-1".equals(area.getParent()))){
						sb.append(area.getName()+";");
					}else{
						break;
					}
				}
			}
			String[] areaStrS = sb.toString().split(";");
			StringBuilder sb2 = new StringBuilder();
			for (int i=areaStrS.length-1; i >= 0; i--) {
				sb2.append(areaStrS[i]);
			}
			supermarket.setAreaName(sb2.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supermarket;
	}

	private Area getAreaNameByCode(String code){
		return areaMapper.selectByPrimaryKey(code);

	}

	@Override
	public void deleteSupermarketrBysupermarketId(Integer supermarketId) {
		Supermarket supermarket = supermarketMapper.selectSupermarketBysupermarketId(supermarketId);

		if(supermarket.getLogo() != null){
			File file = new File(supermarket.getLogo());
			if(file.isFile() && file.exists()){
				file.delete();
			}
		}
		supermarketMapper.deleteSupermarketrBysupermarketId(supermarketId);
	}

	@Override
	public void updateSupermarketBysupermarketId(CommonsMultipartFile logoFile,Supermarket supermarket) {
		String realPath = null;
		if(logoFile != null){
			//需要上传logo图片
			try {
				realPath = ImageUtil.handleUpload(logoFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        supermarket.setLogo(realPath);
        supermarket.setStatus("NORMAL");
        supermarket.setUpdateTime(new Date());
		supermarketMapper.updateSupermarketBysupermarketId(supermarket);

	}

	 @Override
		public List<Supermarket> getSupermarketByAddress(Integer userId) {

		//根据用户id获取绑定的超市集合(已按距离由近到远排序)
		 List<Supermarket> supermarketList = supermarketMapper.getSupermarketByAddress(userId);

		 //获取用户的会员等级
		 OpenMemberInfo openMemberInfo = openMemberInfoService.getTheLastOpenMemberInfo(userId);
		 //普通用户和普通会员(5家超市)
		 if(openMemberInfo == null && MemberLevel.NORMAL.toString().equals(openMemberInfo.getMemberLevel())){
			 supermarketList = supermarketList.size()>5 ? supermarketList.subList(0, 5) : supermarketList;
		 }
		 //高级会员(8家超市)
		 if(MemberLevel.SENIOR.toString().equals(openMemberInfo.getMemberLevel())){
			 supermarketList = supermarketList.size()>8 ? supermarketList.subList(0, 8) : supermarketList;
		 }
		 //超级VIP(12家超市)
		 if(MemberLevel.SUPER.toString().equals(openMemberInfo.getMemberLevel())){
			 supermarketList = supermarketList.size()>12 ? supermarketList.subList(0, 12) : supermarketList;
		 }

		 System.out.println(supermarketList.size());
		 for(Supermarket i:supermarketList) {
			 i.setLogo(ImageUtil.dealToShow(i.getLogo()));
		 }
			return supermarketList;
		}

	@Override
	public Supermarket getSupermarketByProcurerId(Integer userId) {
		return supermarketMapper.getSupermarketByProcurerId(userId);
	}

	@Override
	public Supermarket getSupermarketByUserId(Integer userId) {
		Supermarket supermarket = new Supermarket();
		supermarket.setUserId(userId);
		List<Supermarket> supermarketList = supermarketMapper.selectSupermarketList(supermarket);
		return supermarketList != null && supermarketList.size() > 0 ? supermarketList.get(0) : null;
	}

	@Override
	public Supermarket getSupermarketById(Integer SupermarketId) {
		return supermarketMapper.selectSupermarketBysupermarketId(SupermarketId);
	}
}
