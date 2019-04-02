package com.linayi.util;

import com.linayi.entity.goods.CommunityGoods;
import com.linayi.entity.promoter.OpenMemberInfo;

public class MemberPriceUtil {

    //普通会员：NORMAL  高级会员：SENIOR  超级VIP：SUPER

    /**
     * 根据用户会员等级返回比价超市的数量
     * @param theLastOpenMemberInfo
     * @param supermarketSize
     * @return
     */
    public static Integer supermarketPriceByLevel(OpenMemberInfo theLastOpenMemberInfo, Integer supermarketSize){

        if(theLastOpenMemberInfo == null){
            return supermarketSize > 5 ? 5 : supermarketSize;
        }

        String memberLevel = theLastOpenMemberInfo.getMemberLevel();

        if("NORMAL".equals(memberLevel)){
            return supermarketSize > 5 ? 5 : supermarketSize;
        }

        if("SENIOR".equals(memberLevel)){
            return supermarketSize > 8 ? 8 : supermarketSize;
        }

        return supermarketSize > 12 ? 12 : supermarketSize;
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
