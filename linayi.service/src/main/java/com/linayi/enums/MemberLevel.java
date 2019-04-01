
package com.linayi.enums;

public enum MemberLevel {
	NORMAL("普通会员"),// 普通会员
	SENIOR("高级会员"),	// 高级会员
	SUPER("超级VIP");	// 超级VIP

	/**
	 * [价格类型] 正常价：NORMAL  促销价：PROMOTION  处理价：DEAL  会员价：MEMBER
	 */
	private String memberLevelName;

	private MemberLevel(String memberLevelName) {
		this.memberLevelName = memberLevelName;
	}

	public String getMemberLevelName() {
		return memberLevelName;
	}

	public void setMemberLevelName(String memberLevelName) {
		this.memberLevelName = memberLevelName;
	}
}
