package com.linayi.service.promoter;

import java.util.Date;

import com.linayi.entity.promoter.OrderManMember;
import com.linayi.entity.user.AuthenticationApply;
import com.linayi.entity.user.User;

public interface OrderManMemberService {
	/**
	 * 邀请成为会员
	 * @param mobile
	 * @param memberLevel
	 * @return
	 */
	User insertMember(String mobile,Integer uid);

	/**
	 * 统计会员的订单数和金额
	 * @param receiveAddressId
	 * @param range 时间范围  全部:ALL 本月:MONTH
	 * @return
	 */
	OrderManMember getOrderDatail(Integer receiveAddressId, String range);
	 /**
     * 根据主键Id更新会员有效时间
     * @param openMemberInfo
     * @return
     */
    Integer updateValidTimeById(Integer uid,Integer userId,String memberLevel,Integer promoterDuration);

    void auditMember(AuthenticationApply authenticationApply);
    
    /**
	 * 用户列表-用户详情
	 * @param orderManMember
	 * @return
	 */
	OrderManMember memberDetails(OrderManMember orderManMember);
	
	/**
	 * 处理会员是否失效
	 * @param date
	 */
	void updateMemberInfo(Date date);
}
