package com.linayi.util;

import com.linayi.entity.goods.CommunityGoods;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.promoter.OpenMemberInfo;
import com.linayi.enums.MemberLevel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MemberPriceUtil {

    //普通会员：NORMAL  高级会员：SENIOR  超级VIP：SUPER

    public static List<SupermarketGoods> supermarketGoods = new ArrayList<>();

    /**
     * 根据用户会员等级返回[0]最低价 [1]最低价超市Id [2]最高价 [3]最高价超市Id数组
     * @param theLastOpenMemberInfo
     * @param supermarketGoodsList
     * @return
     */
    public static Integer[] supermarketPriceByLevel(OpenMemberInfo theLastOpenMemberInfo, List<SupermarketGoods> supermarketGoodsList){
        Integer [] arr = new Integer[4];
        Integer supermarketSize = supermarketGoodsList.size();
        Integer num = 0;
        if(theLastOpenMemberInfo == null){
            num = supermarketSize > 5 ? 5 : supermarketSize;
        }else {
            String memberLevel = theLastOpenMemberInfo.getMemberLevel();

            if(MemberLevel.NORMAL.toString().equals(memberLevel)){
                num = supermarketSize > 5 ? 5 : supermarketSize;
            }

            if(MemberLevel.SENIOR.toString().equals(memberLevel)){
                num = supermarketSize > 8 ? 8 : supermarketSize;
            }

            num = supermarketSize > 12 ? 12 : supermarketSize;
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
     * @param theLastOpenMemberInfo
     * @param communityGoods
     * @return
     */
    public static Integer [] supermarketIdAndPriceByLevel(OpenMemberInfo theLastOpenMemberInfo, CommunityGoods communityGoods){
        Integer [] arr = new Integer[4];
        if(theLastOpenMemberInfo == null){
            arr [0] = communityGoods.getMinPriceNormal();
            arr [1] = communityGoods.getMinSupermarketIdNormal();
            arr [2] = communityGoods.getMaxPriceNormal();
            arr [3] = communityGoods.getMaxSupermarketIdNormal();
            return arr;
        }

        String memberLevel = theLastOpenMemberInfo.getMemberLevel();

        if("NORMAL".equals(memberLevel)){
            arr [0] = communityGoods.getMinPriceNormal();
            arr [1] = communityGoods.getMinSupermarketIdNormal();
            arr [2] = communityGoods.getMaxPriceNormal();
            arr [3] = communityGoods.getMaxSupermarketIdNormal();
            return arr;
        }

        if("SENIOR".equals(memberLevel)){
            arr [0] = communityGoods.getMinPriceSenior();
            arr [1] = communityGoods.getMinSupermarketIdSenior();
            arr [2] = communityGoods.getMaxPriceSenior();
            arr [3] = communityGoods.getMaxSupermarketIdSenior();
            return arr;
        }
        arr [0] = communityGoods.getMinPriceSuper();
        arr [1] = communityGoods.getMinSupermarketIdSuper();
        arr [2] = communityGoods.getMaxPriceSuper();
        arr [3] = communityGoods.getMaxSupermarketIdSuper();
        return arr;
    }
}
