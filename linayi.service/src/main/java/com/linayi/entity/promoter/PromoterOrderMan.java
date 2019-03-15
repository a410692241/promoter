package com.linayi.entity.promoter;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.linayi.entity.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

public class PromoterOrderMan extends BaseEntity{
	private Integer promoterOrderManId;

    private Integer promoterId;

    @ApiModelProperty(name = "orderManId", value = "下单员id")
    private Integer orderManId;

    @ApiModelProperty(name = "identity", value = "下单员类型（下单员or法人）")
    private String identity;

    private Date updateTime;

    @ApiModelProperty(name = "createTime", value = "创建时间")
    private Date createTime;
    
    @ApiModelProperty(name = "headImage", value = "头像")
    private String headImage;	//头像(首页)
    
    @ApiModelProperty(name = "homePageIncome", value = "首页总成交额")
    private Integer homePageIncome;	//总成交额(首页)
    
    @ApiModelProperty(name = "numberOfOrders", value = "订单数")
    private Integer numberOfOrders;	//订单数(通用)
    
    @ApiModelProperty(name = "totalSum", value = "订单合计金额")
    private Integer totalSum;	//订单合计金额(通用)
    
    @ApiModelProperty(name = "numberOfMembers", value = "会员数量")
    private Integer numberOfMembers;	//会员数量(通用)
    
    @ApiModelProperty(name = "numberOfOrderMan", value = "下单员数量")
    private Integer numberOfOrderMan;	//下单员数量(通用)
    
    @ApiModelProperty(name = "numberOfUser", value = "客户数量")
    private Integer numberOfUser;	//客户数量(合计客户)
    
    @ApiModelProperty(name = "orderProfit", value = "订单额返利")
    private Integer orderProfit;	//订单额返利
    
    @ApiModelProperty(name = "orderStatisticsData3", value = "订单统计接口第三个显示数据")
    private Integer orderStatisticsData3;	//订单统计接口第三个显示数据（适应前端框架，将每个订单统计页面第三个数据存到属性返回给前端）
   
    @ApiModelProperty(name = "nickname", value = "昵称")
    private String nickname;	//昵称
   
   @ApiModelProperty(name = "openMemberTime", value = "会员开通时间")
   @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm" )
   private Date openMemberTime;	//会员开通时间
   
   @ApiModelProperty(name = "mobile", value = "电话号码")
   private String mobile;	//电话号码
   
   @ApiModelProperty(name = "address", value = "地址")
   private String address;	//地址
   
   private Integer userId;
   
   private String date;	//用于查询订单统计时时查询本月还是全部	本月MONTH 	全部:ALL
   

    public Integer getPromoterOrderManId() {
        return promoterOrderManId;
    }

    public void setPromoterOrderManId(Integer promoterOrderManId) {
        this.promoterOrderManId = promoterOrderManId;
    }

    public Integer getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(Integer promoterId) {
        this.promoterId = promoterId;
    }

    public Integer getOrderManId() {
        return orderManId;
    }

    public void setOrderManId(Integer orderManId) {
        this.orderManId = orderManId;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	

	public Integer getHomePageIncome() {
		return homePageIncome;
	}

	public void setHomePageIncome(Integer homePageIncome) {
		this.homePageIncome = homePageIncome;
	}

	public Integer getNumberOfOrders() {
		return numberOfOrders;
	}

	public void setNumberOfOrders(Integer numberOfOrders) {
		this.numberOfOrders = numberOfOrders;
	}

	public Integer getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(Integer totalSum) {
		this.totalSum = totalSum;
	}

	public Integer getNumberOfMembers() {
		return numberOfMembers;
	}

	public void setNumberOfMembers(Integer numberOfMembers) {
		this.numberOfMembers = numberOfMembers;
	}

	public Integer getNumberOfOrderMan() {
		return numberOfOrderMan;
	}

	public void setNumberOfOrderMan(Integer numberOfOrderMan) {
		this.numberOfOrderMan = numberOfOrderMan;
	}

	public Integer getNumberOfUser() {
		return numberOfUser;
	}

	public void setNumberOfUser(Integer numberOfUser) {
		this.numberOfUser = numberOfUser;
	}

	public Integer getOrderProfit() {
		return orderProfit;
	}

	public void setOrderProfit(Integer orderProfit) {
		this.orderProfit = orderProfit;
	}

	public Integer getOrderStatisticsData3() {
		return orderStatisticsData3;
	}

	public void setOrderStatisticsData3(Integer orderStatisticsData3) {
		this.orderStatisticsData3 = orderStatisticsData3;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getOpenMemberTime() {
		return openMemberTime;
	}

	public void setOpenMemberTime(Date openMemberTime) {
		this.openMemberTime = openMemberTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
    
    
}