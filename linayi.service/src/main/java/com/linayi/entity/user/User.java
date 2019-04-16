package com.linayi.entity.user;

import com.linayi.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * user
 * @author 
 */
public class User extends BaseEntity implements Serializable {
    /**
     * [用户id]
     */
    private Integer userId;

    /**会员等级*/
    private String memberLevel;
    
    /**下单员等级*/
    private String orderManLevel;
    
    /**
     * [昵称]
     */
    private String nickname;

    /**
     * [性别] 男：MALE  女：FEMALE
     */
    private String sex;

    /**
     * [联系电话]
     */
    private String mobile;

    /**
     * [qq]
     */
    private String qq;

    /**
     * [头像地址]
     */
    private String headImage;
    /**
    * [生日]
    */
    @DateTimeFormat( pattern = "yyyy-MM-dd")
    private String birthday;

    /**
     * [微信的openid,判别新用户]
     */
    private String weixinOpenId;

    /**
     * [email]
     */
    private String email;
    
    private String isMember;
    
    private String isOrderMan;

    private String isShop;

    /**
     * [真实姓名]
     */
    private String realName;

    /*默认收货地址*/
    private Integer defaultReceiveAddressId;

    /**
     * [创建时间]
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTime;

    /**
     * [更新时间]
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date updateTime;

	/**
	 * 查询创建时间起
	 */
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date createTimeStart;
	
	/**[开通会员信息id]*/
	private Integer openMemberInfoId;
	
	private Integer openOrderManInfoId;

	private String isRegist;
	
	/**
	 * 查询时间止
	 */
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date createTimeEnd;
	
    private String idCardFront;

    private String idCardBack;
    
    private String isSharer;
    
    private String isProcurer;

    private String isDeliverer;

    private  Integer small_community_id;

    private String name;

    private String code;

    private String receiverAddress;

    private static final long serialVersionUID = 1L;
    
    public String getIsProcurer() {
		return isProcurer;
	}

	public void setIsProcurer(String isProcurer) {
		this.isProcurer = isProcurer;
	}

	public String getIsSharer() {
		return isSharer;
	}

	public void setIsSharer(String isSharer) {
		this.isSharer = isSharer;
	}

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getBirthday() {
        return birthday;
    }
    public String setBirthday(String birthday) {
        return this.birthday = birthday;
    }

    public String getWeixinOpenId() {
        return weixinOpenId;
    }

    public void setWeixinOpenId(String weixinOpenId) {
        this.weixinOpenId = weixinOpenId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date setCreateTime(Date createTime) {
        return this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Integer getDefaultReceiveAddressId() {
        return defaultReceiveAddressId;
    }

    public void setDefaultReceiveAddressId(Integer defaultReceiveAddressId) {
        this.defaultReceiveAddressId = defaultReceiveAddressId;
    }
    
    public String getIdCardFront() {
		return idCardFront;
	}

	public void setIdCardFront(String idCardFront) {
		this.idCardFront = idCardFront;
	}

	public String getIdCardBack() {
		return idCardBack;
	}

	public void setIdCardBack(String idCardBack) {
		this.idCardBack = idCardBack;
	}

    public String getIsDeliverer() {
        return isDeliverer;
    }

    public void setIsDeliverer(String idDeliverer) {
        this.isDeliverer = idDeliverer;
    }

	public String getOrderManLevel() {
		return orderManLevel;
	}

	public void setOrderManLevel(String orderManLevel) {
		this.orderManLevel = orderManLevel;
	}

	@Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getQq() == null ? other.getQq() == null : this.getQq().equals(other.getQq()))
            && (this.getHeadImage() == null ? other.getHeadImage() == null : this.getHeadImage().equals(other.getHeadImage()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
            && (this.getWeixinOpenId() == null ? other.getWeixinOpenId() == null : this.getWeixinOpenId().equals(other.getWeixinOpenId()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getRealName() == null ? other.getRealName() == null : this.getRealName().equals(other.getRealName()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateTimeStart() == null ? other.getCreateTimeStart() == null : this.getCreateTimeStart().equals(other.getCreateTimeStart()))
            && (this.getCreateTimeEnd() == null ? other.getCreateTimeEnd() == null : this.getCreateTimeEnd().equals(other.getCreateTimeEnd()))
            && (this.getIsDeliverer() == null ? other.getIsDeliverer() == null : this.getIsDeliverer().equals(other.getIsDeliverer()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getQq() == null) ? 0 : getQq().hashCode());
        result = prime * result + ((getHeadImage() == null) ? 0 : getHeadImage().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getWeixinOpenId() == null) ? 0 : getWeixinOpenId().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getRealName() == null) ? 0 : getRealName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateTimeStart() == null) ? 0 : getCreateTimeStart().hashCode());
        result = prime * result + ((getCreateTimeEnd() == null) ? 0 : getCreateTimeEnd().hashCode());
        result = prime * result + ((getIsDeliverer() == null) ? 0 : getIsDeliverer().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", nickname=").append(nickname);
        sb.append(", sex=").append(sex);
        sb.append(", mobile=").append(mobile);
        sb.append(", qq=").append(qq);
        sb.append(", headImage=").append(headImage);
        sb.append(", birthday=").append(birthday);
        sb.append(", weixinOpenId=").append(weixinOpenId);
        sb.append(", email=").append(email);
        sb.append(", realName=").append(realName);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createTimeStart=").append(createTimeStart);
        sb.append(", createTimeEnd=").append(createTimeEnd);
        sb.append(", isDeliverer").append(isDeliverer);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public Integer getOpenMemberInfoId() {
		return openMemberInfoId;
	}

	public void setOpenMemberInfoId(Integer openMemberInfoId) {
		this.openMemberInfoId = openMemberInfoId;
	}

	public String getIsMember() {
		return isMember;
	}

	public void setIsMember(String isMember) {
		this.isMember = isMember;
	}

	public String getIsOrderMan() {
		return isOrderMan;
	}

	public void setIsOrderMan(String isOrderMan) {
		this.isOrderMan = isOrderMan;
	}

	public Integer getOpenOrderManInfoId() {
		return openOrderManInfoId;
	}

	public void setOpenOrderManInfoId(Integer openOrderManInfoId) {
		this.openOrderManInfoId = openOrderManInfoId;
	}

    public String getIsRegist() {
        return isRegist;
    }

    public void setIsRegist(String isRegist) {
        this.isRegist = isRegist;
    }

    public Integer getSmall_community_id() {
        return small_community_id;
    }

    public void setSmall_community_id(Integer small_community_id) {
        this.small_community_id = small_community_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getIsShop() {
        return isShop;
    }

    public void setIsShop(String isShop) {
        this.isShop = isShop;
    }
}