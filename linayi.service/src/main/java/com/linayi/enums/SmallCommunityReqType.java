
package com.linayi.enums;

public enum SmallCommunityReqType {
	PROCESSED("已处理"),
	NOTVIEWED("未处理");
	private String smallCommunityReqType;

	SmallCommunityReqType(String smallCommunityReqType) {
		this.smallCommunityReqType = smallCommunityReqType;
	}

	public String getSmallCommunityReqType() {
		return smallCommunityReqType;
	}

	public void setSmallCommunityReqType(String smallCommunityReqType) {
		this.smallCommunityReqType = smallCommunityReqType;
	}

	/*PROCESSED:已处理;NOTVIEWED:未查看*/

}
