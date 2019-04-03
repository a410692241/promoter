package com.linayi.service.goods.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linayi.dao.community.CommunitySupermarketMapper;
import com.linayi.entity.promoter.OpenMemberInfo;
import com.linayi.enums.MemberLevel;
import com.linayi.service.promoter.OpenMemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linayi.dao.community.CommunityMapper;
import com.linayi.dao.correct.CorrectMapper;
import com.linayi.dao.goods.SupermarketGoodsMapper;
import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.entity.correct.Correct;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.enums.CorrectStatus;
import com.linayi.service.goods.SupermarketGoodsService;

import javax.annotation.Resource;

@Service
public class SupermarketGoodsServiceImpl implements SupermarketGoodsService {

    @Autowired
    private SupermarketGoodsMapper supermarketGoodsMapper;

    @Autowired
    private CorrectMapper correctMapper;

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private SupermarketMapper supermarketMapper;
    @Resource
    private OpenMemberInfoService openMemberInfoService;
    @Resource
    private CommunitySupermarketMapper communitySupermarketMapper;

    @Override
    public List<SupermarketGoods> getSupermarketGoods(SupermarketGoods superGoods) {
        return supermarketGoodsMapper.getSupermarketGoods(superGoods);
    }


    //根据商品id获取超市和价格集合然后降序排序加入差价率
    public Map<String, Object> getPriceSupermarketByGoodsSkuId(Integer uid, Integer goodsSkuId) {
        //获取用户的会员等级
        MemberLevel memberLevel = openMemberInfoService.getCurrentMemberLevel(uid);
        //通过用户id获取社区id
        double spreadRate = 0;
        Integer communityId = communityMapper.getcommunityIdByuserId(uid);
        //社区所有绑定超市id,根据会员等级截取
        List<Integer> supermarketIdList =  communitySupermarketMapper.getSupermarketIdList(communityId);
        if(supermarketIdList == null){
            return new HashMap<String, Object>();
        }
        if(MemberLevel.NOT_MEMBER.toString().equals(memberLevel.toString()) || MemberLevel.NORMAL.toString().equals(memberLevel.toString())) {
            supermarketIdList = supermarketIdList.size() > 5 ? supermarketIdList.subList(0,5) : supermarketIdList;
        }
        else if(MemberLevel.SENIOR.toString().equals(memberLevel.toString())) {
            supermarketIdList = supermarketIdList.size() > 8 ? supermarketIdList.subList(0,8) : supermarketIdList;
        }
        else if(MemberLevel.SUPER.toString().equals(memberLevel.toString())) {
            supermarketIdList = supermarketIdList.size() > 12 ? supermarketIdList.subList(0,12) : supermarketIdList;
        }

        //根据超市id集合获取超市价格集合
        List<SupermarketGoods> supermarketGoodsList = new ArrayList<SupermarketGoods>();
        for(Integer supermarketId : supermarketIdList) {
            SupermarketGoods currentSupermarketGoodsList = supermarketGoodsMapper.getPriceSupermarketBycommunityIdgoodsSkuIdSupermarketId(communityId,goodsSkuId,supermarketId);
            if(currentSupermarketGoodsList != null){
                supermarketGoodsList.add(currentSupermarketGoodsList);
            }
        }

        DecimalFormat df = new DecimalFormat("#.00"); //保留double小数点后2位不四舍五入
        //System.out.println("长度："+supermarketGoodsList.size());
        //List<SupermarketGoods> newsupermarketGoodsList = new ArrayList<SupermarketGoods>();
        List<String> supermarketList = supermarketMapper.getSupermarketBycommunityIdAndSupermarketIdList(communityId,supermarketIdList);

            //价格排序升序
            supermarketGoodsList.sort((a, b) -> {
                return a.getPrice() - b.getPrice();
            });

            //差价率
            spreadRate = Double.valueOf(df.format(Double.valueOf((supermarketGoodsList.get(supermarketGoodsList.size() - 1).getPrice() - supermarketGoodsList.get(0).getPrice())) / supermarketGoodsList.get(0).getPrice() * 100));

        List<SupermarketGoods> newsupermarketGoodsList = new ArrayList<>();
        newsupermarketGoodsList.addAll(supermarketGoodsList);
        //List<String> newsupermarketGoods = new ArrayList<>();

        for (String j : supermarketList) {
            boolean exist = false;
            for (SupermarketGoods i : newsupermarketGoodsList) {
                if (i.getSupermarketName().equals(j)) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                SupermarketGoods supermarketGoods = new SupermarketGoods();
                supermarketGoods.setSupermarketName(j);
                supermarketGoods.setPrice(1 - 2);
                supermarketGoodsList.add(supermarketGoods);
            }
        }


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("spreadRate", spreadRate);
        map.put("supermarketGoodsList", supermarketGoodsList);
        return map;
    }


    // 根据社区网点id和商品id获取超市和价格信息
    @Override
    public List<Supermarket> getPriceSupermarketBycommunityIdAndgoodsSkuId(Integer userId, Integer goodsSkuId) {
        // 通过用户id获取超市id集合
        List<Supermarket> supermarketList = supermarketMapper.getSupermarketByAddress(userId);
        // 遍历超市集合
        for (Supermarket supermarket : supermarketList) {
            // 通过超市id、商品id、状态为“生效”中查询纠错表
            Correct param = new Correct();
            param.setSupermarketId(supermarket.getSupermarketId());
            param.setGoodsSkuId(Long.valueOf(goodsSkuId));
            param.setStatus(CorrectStatus.AFFECTED.toString());
            Correct currentCorrect = correctMapper.query(param).stream().findFirst().orElse(null);
            // 当查询结果不为null时，表示该商品有价格
            if (currentCorrect != null) {
                Correct param1 = new Correct();
                param1.setSupermarketId(supermarket.getSupermarketId());
                param1.setGoodsSkuId(Long.valueOf(goodsSkuId));
                List<String> statusList = new ArrayList<>();
                statusList.add(CorrectStatus.WAIT_AUDIT.toString());
                statusList.add(CorrectStatus.AUDIT_SUCCESS.toString());
                param1.setStatusList(statusList);
                Correct currentCorrect1 = correctMapper.query(param1).stream().findFirst().orElse(null);
                if (currentCorrect1 != null) {
                    supermarket.setCorrectType("VIEW");
                    supermarket.setGoodsPrice(currentCorrect.getPrice().toString());
                    supermarket.setCorrectId(currentCorrect1.getCorrectId());
                } else {
                    supermarket.setCorrectType("CORRECT");
                    supermarket.setGoodsPrice(currentCorrect.getPrice().toString());
                    supermarket.setCorrectId(currentCorrect.getCorrectId());
                }

                // 否则表示该商品无价格
            } else {
                // 通过超市id和商品id查询纠错表，如果结果为null则显示分享按钮
                List<Correct> correctList = correctMapper.selectCorrect(supermarket.getSupermarketId(),
                        Long.valueOf(goodsSkuId));
                if (correctList.size() <= 0) {
                    supermarket.setCorrectType("SHARE");
                    // 否则遍历集合
                } else {
                    Correct param2 = new Correct();
                    param2.setSupermarketId(supermarket.getSupermarketId());
                    param2.setGoodsSkuId(Long.valueOf(goodsSkuId));
                    List<String> statusList = new ArrayList<>();
                    statusList.add(CorrectStatus.WAIT_AUDIT.toString());
                    statusList.add(CorrectStatus.AUDIT_SUCCESS.toString());
                    param2.setStatusList(statusList);
                    Correct currentCorrect2 = correctMapper.query(param2).stream().findFirst().orElse(null);
                    if (currentCorrect2 != null) {
                        supermarket.setCorrectType("VIEW");
                        supermarket.setCorrectId(currentCorrect2.getCorrectId());
                    } else {
                        supermarket.setCorrectType("SHARE");
                    }
                }
            }
        }
        return supermarketList;
    }

    /**
     * 通过商品ID和网点ID查找对应的商品价格表按距离排序
     *
     * @param goodsSkuId
     * @param communityId
     * @return
     */
    @Override
    public List<SupermarketGoods> getSupermarketGoodsList(Integer goodsSkuId, Integer communityId) {
        return supermarketGoodsMapper.getPriceSupermarketBycommunityIdAndgoodsSkuId(communityId, goodsSkuId);
    }


    // 查询单个超市商品判断可以分享、纠错还是查看（后台修改价格用）
    @Override
    public Supermarket getCorrectTypeBySupermarketIdAndgoodsSkuId(Long goodsSkuId, Integer supermarketId) {
        //获取超市对象
        Supermarket supermarket = supermarketMapper.selectSupermarketBysupermarketId(supermarketId);
        // 通过超市id、商品id、状态为“生效”中查询纠错表
        Correct param = new Correct();
        param.setSupermarketId(supermarketId);
        param.setGoodsSkuId(Long.valueOf(goodsSkuId));
        param.setStatus(CorrectStatus.AFFECTED.toString());
        Correct currentCorrect = correctMapper.query(param).stream().findFirst().orElse(null);
        // 当查询结果不为null时，表示该商品有价格
        if (currentCorrect != null) {
            Correct param1 = new Correct();
            param1.setSupermarketId(supermarketId);
            param1.setGoodsSkuId(Long.valueOf(goodsSkuId));
            List<String> statusList = new ArrayList<>();
            statusList.add(CorrectStatus.WAIT_AUDIT.toString());
            statusList.add(CorrectStatus.AUDIT_SUCCESS.toString());
            param1.setStatusList(statusList);
            Correct currentCorrect1 = correctMapper.query(param1).stream().findFirst().orElse(null);
            if (currentCorrect1 != null) {
                supermarket.setCorrectType("VIEW");
                supermarket.setGoodsPrice(currentCorrect.getPrice().toString());
                supermarket.setCorrectId(currentCorrect1.getCorrectId());
            } else {
                supermarket.setCorrectType("CORRECT");
                supermarket.setGoodsPrice(currentCorrect.getPrice().toString());
                supermarket.setCorrectId(currentCorrect.getCorrectId());
            }

            // 否则表示该商品无价格
        } else {
            // 通过超市id和商品id查询纠错表，如果结果为null则显示分享按钮
            List<Correct> correctList = correctMapper.selectCorrect(supermarket.getSupermarketId(),
                    Long.valueOf(goodsSkuId));
            if (correctList.size() <= 0) {
                supermarket.setCorrectType("SHARE");
                // 否则遍历集合
            } else {
                Correct param2 = new Correct();
                param2.setSupermarketId(supermarket.getSupermarketId());
                param2.setGoodsSkuId(Long.valueOf(goodsSkuId));
                List<String> statusList = new ArrayList<>();
                statusList.add(CorrectStatus.WAIT_AUDIT.toString());
                statusList.add(CorrectStatus.AUDIT_SUCCESS.toString());
                param2.setStatusList(statusList);
                Correct currentCorrect2 = correctMapper.query(param2).stream().findFirst().orElse(null);
                if (currentCorrect2 != null) {
                    supermarket.setCorrectType("VIEW");
                    supermarket.setCorrectId(currentCorrect2.getCorrectId());
                } else {
                    supermarket.setCorrectType("SHARE");
                }
            }
        }
        return supermarket;
    }

}
