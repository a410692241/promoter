package com.linayi.util;

import com.linayi.entity.goods.CommunityGoods;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.promoter.OpenMemberInfo;
import com.linayi.enums.MemberLevel;

import java.util.*;
import java.util.stream.Collectors;

public class MemberPriceUtil {

    //普通会员：NORMAL  高级会员：SENIOR  超级VIP：SUPER
    //截取后所有的超市
    public static List<SupermarketGoods> supermarketGoods = new ArrayList<>();

    //所有的超市
    public static List<SupermarketGoods> allSpermarketGoodsList = new ArrayList<>();

    public final static Map<String,Integer> levelAndSupermarketNum = new HashMap<>();
    static {
        levelAndSupermarketNum.put(MemberLevel.NOT_MEMBER.toString(),Integer.parseInt(PropertiesUtil.getValueByKey("not_member_supermarket_num")));
        levelAndSupermarketNum.put(MemberLevel.NORMAL.toString(),Integer.parseInt(PropertiesUtil.getValueByKey("normal_supermarket_num")));
        levelAndSupermarketNum.put(MemberLevel.SENIOR.toString(),Integer.parseInt(PropertiesUtil.getValueByKey("senior_supermarket_num")));
        levelAndSupermarketNum.put(MemberLevel.SUPER.toString(),Integer.parseInt(PropertiesUtil.getValueByKey("super_supermarket_num")));
    }

    /**
     * 根据用户会员等级返回[0]最低价 [1]最低价超市Id [2]最高价 [3]最高价超市Id 数组
     * @param currentMemberLevel
     * @param supermarketGoodsList
     * @return
     */
    public static Integer[] supermarketPriceByLevel(MemberLevel currentMemberLevel, List<SupermarketGoods> supermarketGoodsList){

        String memberLevel = currentMemberLevel.toString();
        Integer [] arr = new Integer[4];
        Integer supermarketSize = supermarketGoodsList.size();
        Integer num = 0;
        if(MemberLevel.NOT_MEMBER.toString().equals(memberLevel)){
            num = supermarketSize > levelAndSupermarketNum.get(MemberLevel.NOT_MEMBER.toString()) ? levelAndSupermarketNum.get(MemberLevel.NOT_MEMBER.toString()) : supermarketSize;
        }else if(MemberLevel.NORMAL.toString().equals(memberLevel)){
            num = supermarketSize > levelAndSupermarketNum.get(MemberLevel.NORMAL.toString()) ? levelAndSupermarketNum.get(MemberLevel.NORMAL.toString()) : supermarketSize;
        }else if(MemberLevel.SENIOR.toString().equals(memberLevel)){
            num = supermarketSize > levelAndSupermarketNum.get(MemberLevel.SENIOR.toString()) ? levelAndSupermarketNum.get(MemberLevel.SENIOR.toString()) : supermarketSize;
        }else if(MemberLevel.SUPER.toString().equals(memberLevel)){
            num = supermarketSize > levelAndSupermarketNum.get(MemberLevel.SUPER.toString()) ? levelAndSupermarketNum.get(MemberLevel.SUPER.toString()) : supermarketSize;
        }
        final int[] sum = {0};

        List<SupermarketGoods> collects = supermarketGoodsList.stream().
                sorted((o1,o2) ->{
                    if(o1.getPrice() == null){
                        return 1;
                    }
                    if(o2.getPrice() == null){
                        return -1;
                    }
                    if((o2.getPrice()+"").equals(o1.getPrice() + "")){
                        return o2.getRank() - o1.getRank();
                    }
                    return o2.getPrice() - o1.getPrice();
                }).collect(Collectors.toList());

        collects.stream().forEach(p->{
            if(p.getPrice() == null)
                sum[0]++;
        });

        allSpermarketGoodsList = collects.subList(0,collects.size() - sum[0]);

        sum[0] = 0;
        List<SupermarketGoods> collect = supermarketGoodsList.subList(0, num).stream().
                sorted((o1,o2) ->{
                    if(o1.getPrice() == null){
                        return 1;
                    }
                    if(o2.getPrice() == null){
                        return -1;
                    }
                    if((o2.getPrice()+"").equals(o1.getPrice() + "")){
                        return o2.getRank() - o1.getRank();
                    }
                    return o2.getPrice() - o1.getPrice();
                }).collect(Collectors.toList());


        collect.stream().forEach(p->{
            if(p.getPrice() == null)
                sum[0]++;
        });

        supermarketGoods = collect.subList(0,collect.size() - sum[0]);

        if(num - 1 - sum[0] < 0){
            arr [0] = null;
            arr [1] = null;
            arr [2] = null;
            arr [3] = null;
        }else{
            arr [0] = collect.get(num - 1 - sum[0]).getPrice();
            arr [1] = collect.get(num - 1 - sum[0]).getSupermarketId();
            arr [2] = collect.get(0).getPrice();
            arr [3] = collect.get(0).getSupermarketId();
        }


        return arr;
    }

    /**
     * 根据用户会员等级返回[0]最低价 [1]最低价超市Id [2]最高价 [3]最高价超市Id数组
     *
     * @param currentMemberLevel
     * @param communityGoods
     * @return
     */
    public static Integer [] supermarketIdAndPriceByLevel(MemberLevel currentMemberLevel, CommunityGoods communityGoods){
        String memberLevel = currentMemberLevel.toString();
        Integer [] arr = new Integer[4];
        if(MemberLevel.NOT_MEMBER.toString().equals(memberLevel)){
            arr [0] = communityGoods.getMinPriceNormal();
            arr [1] = communityGoods.getMinSupermarketIdNormal();
            arr [2] = communityGoods.getMaxPriceNormal();
            arr [3] = communityGoods.getMaxSupermarketIdNormal();
        }else if(MemberLevel.NORMAL.toString().equals(memberLevel)){
            arr [0] = communityGoods.getMinPriceNormal();
            arr [1] = communityGoods.getMinSupermarketIdNormal();
            arr [2] = communityGoods.getMaxPriceNormal();
            arr [3] = communityGoods.getMaxSupermarketIdNormal();
        }else if(MemberLevel.SENIOR.toString().equals(memberLevel)){
            arr [0] = communityGoods.getMinPriceSenior();
            arr [1] = communityGoods.getMinSupermarketIdSenior();
            arr [2] = communityGoods.getMaxPriceSenior();
            arr [3] = communityGoods.getMaxSupermarketIdSenior();
        }else if(MemberLevel.SUPER.toString().equals(memberLevel)){
            arr [0] = communityGoods.getMinPriceSuper();
            arr [1] = communityGoods.getMinSupermarketIdSuper();
            arr [2] = communityGoods.getMaxPriceSuper();
            arr [3] = communityGoods.getMaxSupermarketIdSuper();
        }
        return arr;
    }
}
