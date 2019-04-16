package com.linayi.service.community.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.linayi.dao.promoter.OpenMemberInfoMapper;
import com.linayi.enums.MemberLevel;
import com.linayi.service.goods.SupermarketGoodsService;
import com.linayi.util.MemberPriceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linayi.dao.community.CommunityMapper;
import com.linayi.dao.community.CommunitySupermarketMapper;
import com.linayi.dao.goods.CommunityGoodsMapper;
import com.linayi.dao.goods.GoodsSkuMapper;
import com.linayi.dao.goods.SupermarketGoodsMapper;
import com.linayi.entity.community.CommunitySupermarket;
import com.linayi.entity.goods.CommunityGoods;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.service.community.CommunitySupermarketService;

@Service
public class CommunitySupermarketServiceImpl implements CommunitySupermarketService {
	@Resource
	private CommunitySupermarketMapper communitySupermarketMapper;
	@Resource
	private CommunityMapper communityMapper;
	@Autowired
	private SupermarketGoodsMapper supermarketGoodsMapper;
	@Autowired
	private CommunityGoodsMapper communityGoodsMapper;
	@Autowired
	private GoodsSkuMapper goodsSkuMapper;
	@Autowired
	private OpenMemberInfoMapper OpenMemberInfoMapper;
	@Autowired
	private SupermarketGoodsService supermarketGoodsService;


	//绑定社区和超市
	@Override
	@Transactional
	public void bind(CommunitySupermarket record) {
		Integer a =communitySupermarketMapper.insert(record);
		if(a==1) {
			toUpdateCommunityPrice(record.getCommunityId());
		}

	}


	//解绑社区绑定的超市
	@Override
	@Transactional
	public void unbind(CommunitySupermarket communitySupermarket) {
		//删除社区超市关联表
		Integer a = communitySupermarketMapper.deleteCommunitySupermarket(communitySupermarket);
		if(a==1) {
		toUpdateCommunityPrice(communitySupermarket.getCommunityId());
		}
	}

	public List<Integer> getCommunityIdBysupermarketId(Integer supermarketId){
		return communitySupermarketMapper.getCommunityIdBysupermarketId(supermarketId);
	}


	@Transactional
	//更新社区商品价格表
	public void toUpdateCommunityPrice(Integer communityId,Integer goodsSkuId) {
		//根据社区id和商品id删除社区商品表
		communityGoodsMapper.delectCommunityGoods(communityId,goodsSkuId);

		//根据超市id获取超市价格表信息
		List<SupermarketGoods> supermarketGoodsSkuList = supermarketGoodsService.getSupermarketGoodsList(goodsSkuId, communityId);

		if(supermarketGoodsSkuList.size()==0) {
			return;
		}
		saveMemberPrice(communityId, supermarketGoodsSkuList, goodsSkuId);
	}


	@Transactional
	//更新社区价格
	public void toUpdateCommunityPrice(Integer communityId) {
		//根据社区id删除社区商品表
		communityGoodsMapper.delectCommunityGoods(communityId,null);
		//根据社区id获取绑定的超市id集合
		List<Integer> supermarketIdList = communitySupermarketMapper.getSupermarketIdList(communityId);
		//根据超市id获取有价格的商品id集合
		List<SupermarketGoods> goodsSkuIdList = supermarketGoodsMapper.getGoodsSkuIdBySupermarketId(supermarketIdList);

		for(SupermarketGoods i:goodsSkuIdList) {
		//根据超市id获取超市价格表信息
            List<SupermarketGoods> supermarketGoodsSkuList = supermarketGoodsService.getSupermarketGoodsList(i.getGoodsSkuId().intValue(), communityId);
			saveMemberPrice(communityId, supermarketGoodsSkuList, i.getGoodsSkuId().intValue());
		}

	}

	private void saveMemberPrice(Integer communityId, List<SupermarketGoods> supermarketGoodsSkuList, int i2) {
		CommunityGoods communityGoods = new CommunityGoods();
		Date now = new Date();
		communityGoods.setCommunityId(communityId);
		communityGoods.setCreateTime(now);
		communityGoods.setGoodsSkuId(i2);


		//普通会员最低价最高价信息
		Integer[] priceByLevel = MemberPriceUtil.supermarketPriceByLevel(MemberLevel.NORMAL, supermarketGoodsSkuList);

		//普通用户和普通会员(最近5家超市)
		communityGoods.setMinPriceNormal(priceByLevel[0]);
		communityGoods.setMinSupermarketIdNormal(priceByLevel[1]);
		communityGoods.setMaxPriceNormal(priceByLevel[2]);
		communityGoods.setMaxSupermarketIdNormal(priceByLevel[3]);

		//获取高级会员最低价最高价信息
		Integer[] priceByLevel1 = MemberPriceUtil.supermarketPriceByLevel(MemberLevel.SENIOR, supermarketGoodsSkuList);

		//高级会员(最近8家超市)
		communityGoods.setMinPriceSenior(priceByLevel1[0]);
		communityGoods.setMinSupermarketIdSenior(priceByLevel1[1]);
		communityGoods.setMaxPriceSenior(priceByLevel1[2]);
		communityGoods.setMaxSupermarketIdSenior(priceByLevel1[3]);

		//获取vip会员最低价最高价信息
		Integer[] priceByLevel2 = MemberPriceUtil.supermarketPriceByLevel(MemberLevel.SUPER, supermarketGoodsSkuList);

		//vip会员(最近12家超市)
		communityGoods.setMinPriceSuper(priceByLevel2[0]);
		communityGoods.setMinSupermarketIdSuper(priceByLevel2[1]);
		communityGoods.setMaxPriceSuper(priceByLevel2[2]);
		communityGoods.setMaxSupermarketIdSuper(priceByLevel2[3]);

		if (MemberPriceUtil.allSpermarketGoodsList.size() != 0) {
			//所有超市
			communityGoods.setMinPrice(MemberPriceUtil.allSpermarketGoodsList.get(MemberPriceUtil.allSpermarketGoodsList.size() - 1).getPrice());
			communityGoods.setMinSupermarketId(MemberPriceUtil.allSpermarketGoodsList.get(MemberPriceUtil.allSpermarketGoodsList.size() - 1).getSupermarketId());
			communityGoods.setMaxPrice(MemberPriceUtil.allSpermarketGoodsList.get(0).getPrice());
			communityGoods.setMaxSupermarketId(MemberPriceUtil.allSpermarketGoodsList.get(0).getSupermarketId());
			//重新添加新的社区商品价格表信息
			communityGoodsMapper.insertSelective(communityGoods);
		}


	}


}
