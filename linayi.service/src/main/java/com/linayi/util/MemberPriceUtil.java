package com.linayi.util;

import com.linayi.entity.promoter.OpenMemberInfo;

public class MemberPriceUtil {

    //普通会员：NORMAL  高级会员：SENIOR  超级VIP：SUPER


    public static Integer memberPriceByLevel(OpenMemberInfo theLastOpenMemberInfo, Integer supermarketSize){

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
}
