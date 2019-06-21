package com.linayi.entity.user;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.linayi.entity.BaseEntity;

public class AuthenticationApply extends BaseEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 722130843798947206L;

	private Integer applyId;

    private Integer userId;

    //申请家庭服务师——被邀请人的user_id
    private Integer applierId;

    private String authenticationType;

    private String realName;

    private String mobile;

    private String idCardFront;

    private String idCardBack;

    private String status;

    private Date createTime;

    private Date updateTime;
    
    private String areaCode;
    
    private String address;
    
    private Integer supermarketId;
    
    private Integer smallCommunityId;
    
    private String supermarketName;
    //代言人部分属性
    private String nickname;

    private String specialty;

    private String hobby;

    private String motto;

    private String image;
    
    private Integer promoterId;
    
    private String identity;

    private Integer orderManId;
    
	public Integer getPromoterId() {
		return promoterId;
	}

	public void setPromoterId(Integer promoterId) {
		this.promoterId = promoterId;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getSupermarketName() {
		return supermarketName;
	}

	public void setSupermarketName(String supermarketName) {
		this.supermarketName = supermarketName;
	}

	public Integer getSmallCommunityId() {
		return smallCommunityId;
	}

	public void setSmallCommunityId(Integer smallCommunityId) {
		this.smallCommunityId = smallCommunityId;
	}

	/**
	 * 查询创建时间起
	 */
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date createTimeStart;

	/**
	 * 查询时间止
	 */
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date createTimeEnd;
	
    public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getSupermarketId() {
		return supermarketId;
	}

	public void setSupermarketId(Integer supermarketId) {
		this.supermarketId = supermarketId;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType == null ? null : authenticationType.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront == null ? null : idCardFront.trim();
    }

    public String getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack == null ? null : idCardBack.trim();
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public Integer getApplierId() {
        return applierId;
    }

    public void setApplierId(Integer applierId) {
        this.applierId = applierId;
    }

    public Integer getOrderManId() {
        return orderManId;
    }

    public void setOrderManId(Integer orderManId) {
        this.orderManId = orderManId;
    }

    @Override
	public String toString() {
		return "AuthenticationApply [applyId=" + applyId + ", userId=" + userId + ", authenticationType="
				+ authenticationType + ", realName=" + realName + ", mobile=" + mobile + ", idCardFront=" + idCardFront
				+ ", idCardBack=" + idCardBack + ", status=" + status + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", areaCode=" + areaCode + ", address=" + address + ", supermarketId=" + supermarketId
				+ ", smallCommunityId=" + smallCommunityId + ", supermarketName=" + supermarketName + ", nickname="
				+ nickname + ", specialty=" + specialty + ", hobby=" + hobby + ", motto=" + motto + ", image=" + image
				+ ", promoterId=" + promoterId + ", identity=" + identity + ", createTimeStart=" + createTimeStart
				+ ", createTimeEnd=" + createTimeEnd + "]";
	}
    
}