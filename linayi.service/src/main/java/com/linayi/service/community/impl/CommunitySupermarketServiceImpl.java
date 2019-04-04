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
		//根据社区id获取绑定的超市id集合(根据超市里社区由近到远的顺序)
		List<Integer> supermarketIdList = communitySupermarketMapper.getSupermarketIdList(communityId);
		List<SupermarketGoods> supermarketGoodsSkuList = new ArrayList<SupermarketGoods>();
		for(Integer supermarketId : supermarketIdList){
			SupermarketGoods supermarketGoods = supermarketGoodsMapper.getSupermarketGoodsBysupermarketIdAndgoodsSkuId(goodsSkuId,supermarketId);
			if(supermarketGoods != null){
				supermarketGoodsSkuList.add(supermarketGoods);
			}
		}

		if(supermarketGoodsSkuList.size()==0) {
			return;
		}

		List<SupermarketGoods> currentSupermarketGoodsSkuList = new ArrayList<SupermarketGoods>();
		//所有超市价格价格排序(升序)
		if(supermarketGoodsSkuList.size()>0) {
			currentSupermarketGoodsSkuList.addAll(supermarketGoodsSkuList);
			currentSupermarketGoodsSkuList.sort((a, b) -> {
				return a.getPrice() - b.getPrice();
			});
		}


		//普通会员最低价最高价信息
		MemberPriceUtil.supermarketPriceByLevel(MemberLevel.NORMAL,supermarketGoodsSkuList);
		List<SupermarketGoods> supermarketGoodsSkuListForNormal = MemberPriceUtil.supermarketGoods;
//		List<SupermarketGoods> supermarketGoodsSkuListForNormal =
//				supermarketGoodsSkuList.size() > MemberPriceUtil.levelAndSupermarketNum.get(MemberLevel.NORMAL.toString()) ? supermarketGoodsSkuList.subList(0,MemberPriceUtil.levelAndSupermarketNum.get(MemberLevel.NORMAL.toString())) : supermarketGoodsSkuList;
//		//所有超市价格价格排序(升序)
//		supermarketGoodsSkuListForNormal.sort((a, b) -> {
//			return a.getPrice() - b.getPrice();
//		});

		//获取高级会员最低价最高价信息
		MemberPriceUtil.supermarketPriceByLevel(MemberLevel.SENIOR,supermarketGoodsSkuList);
		List<SupermarketGoods> supermarketGoodsSkuListForSenior = MemberPriceUtil.supermarketGoods;
//		List<SupermarketGoods> supermarketGoodsSkuListForSenior =
//				supermarketGoodsSkuList.size() > MemberPriceUtil.levelAndSupermarketNum.get(MemberLevel.SENIOR.toString()) ? supermarketGoodsSkuList.subList(0,MemberPriceUtil.levelAndSupermarketNum.get(MemberLevel.SENIOR.toString())) : supermarketGoodsSkuList;
//		//所有超市价格价格排序(升序)
//		supermarketGoodsSkuListForSenior.sort((a, b) -> {
//			return a.getPrice() - b.getPrice();
//		});

		//获取vip会员最低价最高价信息
		MemberPriceUtil.supermarketPriceByLevel(MemberLevel.SUPER,supermarketGoodsSkuList);
		List<SupermarketGoods> supermarketGoodsSkuListForSuper = MemberPriceUtil.supermarketGoods;
//		List<SupermarketGoods> supermarketGoodsSkuListForSuper =
//				supermarketGoodsSkuList.size() > MemberPriceUtil.levelAndSupermarketNum.get(MemberLevel.SUPER.toString()) ? supermarketGoodsSkuList.subList(0,MemberPriceUtil.levelAndSupermarketNum.get(MemberLevel.SUPER.toString())) : supermarketGoodsSkuList;
//		//所有超市价格价格排序(升序)
//		supermarketGoodsSkuListForSuper.sort((a, b) -> {
//			return a.getPrice() - b.getPrice();
//		});

		CommunityGoods communityGoods = new CommunityGoods();
		Date now = new Date();
		communityGoods.setCommunityId(communityId);
		communityGoods.setCreateTime(now);
		communityGoods.setGoodsSkuId(goodsSkuId);
		//所有超市
		communityGoods.setMaxPrice(currentSupermarketGoodsSkuList.get(currentSupermarketGoodsSkuList.size()-1).getPrice());
		communityGoods.setMaxSupermarketId(currentSupermarketGoodsSkuList.get(currentSupermarketGoodsSkuList.size()-1).getSupermarketId().intValue());
		communityGoods.setMinPrice(currentSupermarketGoodsSkuList.get(0).getPrice());
		communityGoods.setMinSupermarketId(currentSupermarketGoodsSkuList.get(0).getSupermarketId());
		//普通用户和普通会员(最近5家超市)
		communityGoods.setMinPriceNormal(supermarketGoodsSkuListForNormal.get(supermarketGoodsSkuListForNormal.size() - 1).getPrice());
		communityGoods.setMinSupermarketIdNormal(supermarketGoodsSkuListForNormal.get(supermarketGoodsSkuListForNormal.size() - 1).getSupermarketId());
		communityGoods.setMaxPriceNormal(supermarketGoodsSkuListForNormal.get(0).getPrice());
		communityGoods.setMaxSupermarketIdNormal(supermarketGoodsSkuListForNormal.get(0).getSupermarketId().intValue());
		//高级会员(最近8家超市)
		communityGoods.setMinPriceSenior(supermarketGoodsSkuListForSenior.get(supermarketGoodsSkuListForSenior.size() - 1).getPrice());
		communityGoods.setMinSupermarketIdSenior(supermarketGoodsSkuListForSenior.get(supermarketGoodsSkuListForSenior.size() - 1).getSupermarketId());
		communityGoods.setMaxPriceSenior(supermarketGoodsSkuListForSenior.get(0).getPrice());
		communityGoods.setMaxSupermarketIdSenior(supermarketGoodsSkuListForSenior.get(0).getSupermarketId().intValue());
		//vip会员(最近12家超市)
		communityGoods.setMinPriceSuper(supermarketGoodsSkuListForSuper.get(supermarketGoodsSkuListForSuper.size() - 1).getPrice());
		communityGoods.setMinSupermarketIdSuper(supermarketGoodsSkuListForSuper.get(supermarketGoodsSkuListForSuper.size() - 1).getSupermarketId());
		communityGoods.setMaxPriceSuper(supermarketGoodsSkuListForSuper.get(0).getPrice());
		communityGoods.setMaxSupermarketIdSuper(supermarketGoodsSkuListForSuper.get(0).getSupermarketId().intValue());
		//重新添加新的社区商品价格表信息
		communityGoodsMapper.insertSelective(communityGoods);

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
//			List<SupermarketGoods> supermarketGoodsSkuList = new ArrayList<SupermarketGoods>();
//			for(Integer supermarketId : supermarketIdList){
//////				SupermarketGoods supermarketGoods = supermarketGoodsMapper.getSupermarketGoodsBysupermarketIdAndgoodsSkuId(i.getGoodsSkuId().intValue(),supermarketId);
//////				if(supermarketGoods != null){
//////					supermarketGoodsSkuList.add(supermarketGoods);
//////				}
//////			}
            List<SupermarketGoods> supermarketGoodsSkuList = supermarketGoodsService.getSupermarketGoodsList(i.getGoodsSkuId().intValue(), communityId);

            //List<SupermarketGoods> currentSupermarketGoodsSkuList = new ArrayList<SupermarketGoods>();
			/*if(supermarketGoodsSkuList.size()>0) {
				currentSupermarketGoodsSkuList.addAll(supermarketGoodsSkuList);
				//价格排序(升序)
				currentSupermarketGoodsSkuList.sort((a, b) -> {
					return a.getPrice() - b.getPrice();
				});
			}*/


			//普通会员最低价最高价信息
			MemberPriceUtil.supermarketPriceByLevel(MemberLevel.NORMAL,supermarketGoodsSkuList);
			List<SupermarketGoods> supermarketGoodsSkuListForNormal = MemberPriceUtil.allSupermarketGoods;
			/*List<SupermarketGoods> supermarketGoodsSkuListForNormal =
					supermarketGoodsSkuList.size() > MemberPriceUtil.levelAndSupermarketNum.get(MemberLevel.NORMAL.toString()) ? supermarketGoodsSkuList.subList(0,MemberPriceUtil.levelAndSupermarketNum.get(MemberLevel.NORMAL.toString())) : supermarketGoodsSkuList;
			//所有超市价格价格排序(升序)
			supermarketGoodsSkuListForNormal.sort((a, b) -> {
				return a.getPrice() - b.getPrice();
			});*/

			//获取高级会员最低价最高价信息
			MemberPriceUtil.supermarketPriceByLevel(MemberLevel.SENIOR,supermarketGoodsSkuList);
			List<SupermarketGoods> supermarketGoodsSkuListForSenior = MemberPriceUtil.allSupermarketGoods;
			/*List<SupermarketGoods> supermarketGoodsSkuListForSenior =
					supermarketGoodsSkuList.size() > MemberPriceUtil.levelAndSupermarketNum.get(MemberLevel.SENIOR.toString()) ? supermarketGoodsSkuList.subList(0,MemberPriceUtil.levelAndSupermarketNum.get(MemberLevel.SENIOR.toString())) : supermarketGoodsSkuList;
			//所有超市价格价格排序(升序)
			supermarketGoodsSkuListForSenior.sort((a, b) -> {
				return a.getPrice() - b.getPrice();
			});*/

			//获取vip会员最低价最高价信息
			MemberPriceUtil.supermarketPriceByLevel(MemberLevel.SUPER,supermarketGoodsSkuList);
			List<SupermarketGoods> supermarketGoodsSkuListForSuper = MemberPriceUtil.allSupermarketGoods;
			/*List<SupermarketGoods> supermarketGoodsSkuListForSuper =
					supermarketGoodsSkuList.size() > MemberPriceUtil.levelAndSupermarketNum.get(MemberLevel.SUPER.toString()) ? supermarketGoodsSkuList.subList(0,MemberPriceUtil.levelAndSupermarketNum.get(MemberLevel.SUPER.toString())) : supermarketGoodsSkuList;
			//所有超市价格价格排序(升序)
			supermarketGoodsSkuListForSuper.sort((a, b) -> {
				return a.getPrice() - b.getPrice();
			});
*/
			CommunityGoods communityGoods = new CommunityGoods();
			Date now = new Date();
			communityGoods.setCommunityId(communityId);
			communityGoods.setCreateTime(now);
			communityGoods.setGoodsSkuId(i.getGoodsSkuId().intValue());
			//所有超市
			communityGoods.setMaxPrice(MemberPriceUtil.allSpermarketGoodsList.get(MemberPriceUtil.allSpermarketGoodsList.size()-1).getPrice());
			communityGoods.setMaxSupermarketId(MemberPriceUtil.allSpermarketGoodsList.get(MemberPriceUtil.allSpermarketGoodsList.size()-1).getSupermarketId());
			communityGoods.setMinPrice(MemberPriceUtil.allSpermarketGoodsList.get(0).getPrice());
			communityGoods.setMinSupermarketId(MemberPriceUtil.allSpermarketGoodsList.get(0).getSupermarketId());
			//普通用户和普通会员(最近5家超市)
			communityGoods.setMinPriceNormal(supermarketGoodsSkuListForNormal.get(supermarketGoodsSkuListForNormal.size() - 1).getPrice());
			communityGoods.setMinSupermarketIdNormal(supermarketGoodsSkuListForNormal.get(supermarketGoodsSkuListForNormal.size() - 1).getSupermarketId());
			communityGoods.setMaxPriceNormal(supermarketGoodsSkuListForNormal.get(0).getPrice());
			communityGoods.setMaxSupermarketIdNormal(supermarketGoodsSkuListForNormal.get(0).getSupermarketId().intValue());
			//高级会员(最近8家超市)
			communityGoods.setMinPriceSenior(supermarketGoodsSkuListForSenior.get(supermarketGoodsSkuListForSenior.size() - 1).getPrice());
			communityGoods.setMinSupermarketIdSenior(supermarketGoodsSkuListForSenior.get(supermarketGoodsSkuListForSenior.size() - 1).getSupermarketId());
			communityGoods.setMaxPriceSenior(supermarketGoodsSkuListForSenior.get(0).getPrice());
			communityGoods.setMaxSupermarketIdSenior(supermarketGoodsSkuListForSenior.get(0).getSupermarketId().intValue());
			//vip会员(最近12家超市)
			communityGoods.setMinPriceSuper(supermarketGoodsSkuListForSuper.get(supermarketGoodsSkuListForSuper.size() - 1).getPrice());
			communityGoods.setMinSupermarketIdSuper(supermarketGoodsSkuListForSuper.get(supermarketGoodsSkuListForSuper.size() - 1).getSupermarketId());
			communityGoods.setMaxPriceSuper(supermarketGoodsSkuListForSuper.get(0).getPrice());
			communityGoods.setMaxSupermarketIdSuper(supermarketGoodsSkuListForSuper.get(0).getSupermarketId().intValue());
			//重新添加新的社区商品价格表信息
			communityGoodsMapper.insertSelective(communityGoods);
			//supermarketGoodsSkuList.clear();

		}

	}


}
