package com.linayi.entity.area;

import java.util.Date;
import java.util.List;

import com.linayi.entity.BaseEntity;

public class SmallCommunity extends BaseEntity{
	private Integer smallCommunityId;

    private String areaCode;

    private String name;

    private Integer communityId;

    private String status;

    private Date createTime;

    private Date updateTime;
    
    private String areaName;
    
    private String communityName;
    
    private List<Integer> communityIdList;
    
    private List<String> areaCodeList;
    
    private String type;
    
    private Integer delivererId;
    
	public Integer getDelivererId() {
		return delivererId;
	}

	public void setDelivererId(Integer delivererId) {
		this.delivererId = delivererId;
	}

	public Integer getSmallCommunityId() {
        return smallCommunityId;
    }

    public void setSmallCommunityId(Integer smallCommunityId) {
        this.smallCommunityId = smallCommunityId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public List<Integer> getCommunityIdList() {
		return communityIdList;
	}

	public void setCommunityIdList(List<Integer> communityIdList) {
		this.communityIdList = communityIdList;
	}

	public List<String> getAreaCodeList() {
		return areaCodeList;
	}

	public void setAreaCodeList(List<String> areaCodeList) {
		this.areaCodeList = areaCodeList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}