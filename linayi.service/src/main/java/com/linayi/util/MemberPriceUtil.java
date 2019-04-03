package com.linayi.util;

import com.linayi.entity.goods.CommunityGoods;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.promoter.OpenMemberInfo;
import com.linayi.enums.MemberLevel;

import java.util.*;
import java.util.stream.Collectors;

public class MemberPriceUtil {

    //普通会员：NORMAL  高级会员：SENIOR  超级VIP：SUPER

    public static List<SupermarketGoods> supermarketGoods = new ArrayList<>();

    public final static Map<String,Integer> levelAndSupermarketNum = new HashMap<>();
    static {
        levelAndSupermarketNum.put(MemberLevel.NOT_MEMBER.toString(),Integer.parseInt(PropertiesUtil.getValueByKey("not_member_supermarket_num")));
        levelAndSupermarketNum.put(MemberLevel.NORMAL.toString(),Integer.parseInt(PropertiesUtil.getValueByKey("normal_supermarket_num")));
        levelAndSupermarketNum.put(MemberLevel.SENIOR.toString(),Integer.parseInt(PropertiesUtil.getValueByKey("senior_supermarket_num")));
        levelAndSupermarketNum.put(MemberLevel.SUPER.toString(),Integer.parseInt(PropertiesUtil.getValueByKey("super_supermarket_num")));
    }

    /**
     * 根据用户会员等级返回[0]最低价 [1]最低价超市Id [2]最高价 [3]最高价超市Id数组
     * @param memberLevel
     * @param supermarketGoodsList
     * @return
     */
    public static Integer[] supermarketPriceByLevel(String memberLevel, List<SupermarketGoods> supermarketGoodsList){
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

        List<SupermarketGoods> collect = supermarketGoodsList.subList(0, num).stream().sorted(Comparator.comparing(SupermarketGoods::getPrice).reversed()).collect(Collectors.toList());
        supermarketGoods = collect;
        arr [0] = collect.get(num - 1).getPrice();
        arr [1] = collect.get(num - 1).getSupermarketId();
        arr [2] = collect.get(0).getPrice();
        arr [3] = collect.get(0).getSupermarketId();
        return arr;
    }

    /**
     * 根据用户会员等级返回[0]最低价 [1]最低价超市Id [2]最高价 [3]最高价超市Id数组
     *
     * @param memberLevel
     * @param communityGoods
     * @return
     */
    public static Integer [] supermarketIdAndPriceByLevel(String memberLevel, CommunityGoods communityGoods){
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
