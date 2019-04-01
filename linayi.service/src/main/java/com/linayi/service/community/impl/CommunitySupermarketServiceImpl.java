package com.linayi.service.community.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.linayi.dao.promoter.OpenMemberInfoMapper;
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
	
	
	//更新社区商品价格表
	public void toUpdateCommunityPrice(Integer communityId,Integer goodsSkuId) {
		//根据社区id和商品id删除社区商品表
		communityGoodsMapper.delectCommunityGoods(communityId,goodsSkuId);
		//根据社区id获取绑定的超市id集合(根据超市里社区由近到远的顺序)
		List<Integer> supermarketIdList = communitySupermarketMapper.getSupermarketIdList(communityId);
		List<SupermarketGoods> supermarketGoodsSkuList = new ArrayList<SupermarketGoods>();
		for(Integer supermarketId : supermarketIdList){
			SupermarketGoods supermarketGoods = supermarketGoodsMapper.getSupermarketGoodsBysupermarketIdAndgoodsSkuId(goodsSkuId,supermarketId);
			supermarketGoodsSkuList.add(supermarketGoods);
		}
		
		if(supermarketGoodsSkuList.size()==0) {
			return;
		}
		//所有超市价格价格排序(升序)
		supermarketGoodsSkuList.sort((a, b) -> {
			return a.getPrice() - b.getPrice();
		});

		//用户和普通会员最近5家超市,再排序价格
		List<SupermarketGoods> supermarketGoodsSkuListForNormal = new ArrayList<SupermarketGoods>();
		if(supermarketGoodsSkuList.size() > 5){
			supermarketGoodsSkuListForNormal = supermarketGoodsSkuList.subList(0,5);
		}else{
			supermarketGoodsSkuListForNormal = supermarketGoodsSkuList;
		}
		//所有超市价格价格排序(升序)
		supermarketGoodsSkuListForNormal.sort((a, b) -> {
			return a.getPrice() - b.getPrice();
		});

		//高级会员最近8家超市,再排序价格
		List<SupermarketGoods> supermarketGoodsSkuListForSenior = new ArrayList<SupermarketGoods>();
		if(supermarketGoodsSkuList.size() > 8){
			supermarketGoodsSkuListForSenior = supermarketGoodsSkuList.subList(0,8);
		}else{
			supermarketGoodsSkuListForSenior = supermarketGoodsSkuList;
		}
		//所有超市价格价格排序(升序)
		supermarketGoodsSkuListForSenior.sort((a, b) -> {
			return a.getPrice() - b.getPrice();
		});

		//vip会员最近8家超市,再排序价格
		List<SupermarketGoods> supermarketGoodsSkuListForSuper = new ArrayList<SupermarketGoods>();
		if(supermarketGoodsSkuList.size() > 12){
			supermarketGoodsSkuListForSuper = supermarketGoodsSkuList.subList(0,12);
		}else{
			supermarketGoodsSkuListForSuper = supermarketGoodsSkuList;
		}
		//所有超市价格价格排序(升序)
		supermarketGoodsSkuListForSuper.sort((a, b) -> {
			return a.getPrice() - b.getPrice();
		});

		CommunityGoods communityGoods = new CommunityGoods();
		Date now = new Date();
		communityGoods.setCommunityId(communityId);
		communityGoods.setCreateTime(now);
		communityGoods.setGoodsSkuId(goodsSkuId);
		//所有超市
		communityGoods.setMaxPrice(supermarketGoodsSkuList.get(supermarketGoodsSkuList.size()-1).getPrice());
		communityGoods.setMaxSupermarketId(supermarketGoodsSkuList.get(supermarketGoodsSkuList.size()-1).getSupermarketId().intValue());
		communityGoods.setMinPrice(supermarketGoodsSkuList.get(0).getPrice());
		communityGoods.setMinSupermarketId(supermarketGoodsSkuList.get(0).getSupermarketId());
		//普通用户和普通会员(最近5家超市)
		communityGoods.setMinPriceNormal(supermarketGoodsSkuListForNormal.get(0).getPrice());
		communityGoods.setMinSupermarketIdNormal(supermarketGoodsSkuListForNormal.get(0).getSupermarketId());
		communityGoods.setMaxPriceNormal(supermarketGoodsSkuListForNormal.get(supermarketGoodsSkuList.size()-1).getPrice());
		communityGoods.setMaxSupermarketIdNormal(supermarketGoodsSkuListForNormal.get(supermarketGoodsSkuList.size()-1).getSupermarketId().intValue());
		//高级会员(最近8家超市)
		communityGoods.setMinPriceSenior(supermarketGoodsSkuListForSenior.get(0).getPrice());
		communityGoods.setMinSupermarketIdSenior(supermarketGoodsSkuListForSenior.get(0).getSupermarketId());
		communityGoods.setMaxPriceSenior(supermarketGoodsSkuListForSenior.get(supermarketGoodsSkuList.size()-1).getPrice());
		communityGoods.setMaxSupermarketIdSenior(supermarketGoodsSkuListForSenior.get(supermarketGoodsSkuList.size()-1).getSupermarketId().intValue());
		//vip会员(最近12家超市)
		communityGoods.setMinPriceSuper(supermarketGoodsSkuListForSuper.get(0).getPrice());
		communityGoods.setMinSupermarketIdSuper(supermarketGoodsSkuListForSuper.get(0).getSupermarketId());
		communityGoods.setMaxPriceSuper(supermarketGoodsSkuListForSuper.get(supermarketGoodsSkuList.size()-1).getPrice());
		communityGoods.setMaxSupermarketIdSuper(supermarketGoodsSkuListForSuper.get(supermarketGoodsSkuList.size()-1).getSupermarketId().intValue());
		//重新添加新的社区商品价格表信息
		communityGoodsMapper.insertSelective(communityGoods);
		
	}


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
		List<SupermarketGoods> supermarketGoodsSkuList = supermarketGoodsMapper.getSupermarketGoodsBysupermarketIdListAndgoodsSkuId(i.getGoodsSkuId().intValue(), supermarketIdList);
			
		if(supermarketGoodsSkuList.size()>0) {
			//价格排序(升序)
		    supermarketGoodsSkuList.sort((a, b) -> {
				return a.getPrice() - b.getPrice();
			});
			CommunityGoods communityGoods = new CommunityGoods();
			Date now = new Date();
			communityGoods.setCommunityId(communityId);
			communityGoods.setCreateTime(now);
			communityGoods.setGoodsSkuId(i.getGoodsSkuId().intValue());
			communityGoods.setMaxPrice(supermarketGoodsSkuList.get(supermarketGoodsSkuList.size()-1).getPrice());
			communityGoods.setMaxSupermarketId(supermarketGoodsSkuList.get(supermarketGoodsSkuList.size()-1).getSupermarketId());
			communityGoods.setMinPrice(supermarketGoodsSkuList.get(0).getPrice());
			communityGoods.setMinSupermarketId(supermarketGoodsSkuList.get(0).getSupermarketId());
			//重新添加新的社区商品价格表信息
			communityGoodsMapper.insertSelective(communityGoods);
			//supermarketGoodsSkuList.clear();
			}
		}

	}


}
