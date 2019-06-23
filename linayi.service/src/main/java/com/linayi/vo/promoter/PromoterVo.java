package com.linayi.vo.promoter;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.linayi.entity.BaseEntity;
import com.linayi.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 添加内部类接口的方式
 * 1.在类上添加 @Getter 和 @Setter
 * 2.添加静态(注意是静态)内部类(类名 = 接口地址后缀+接口名,例如user/login取名UserLogin)
 * 3.类内参数属性,需要定义正确的参数类型,也可以加上示例
 */

public class PromoterVo {

    @Getter
    @Setter
    public static class OrdersObj extends PageVo {
        @ApiModelProperty(value = "用户Id",required = true,example = "1")
        public Integer memberId;
        @ApiModelProperty(value = "接受地址Id",required = false,example = "1")
        public Integer receiveAddressId;
        @ApiModelProperty(value = "查询范围",required = false,example = "ALL:全部,MONTH:本月")
        public String range;
    }

    
    /*我的团队-订单统计*/
    @Getter
    @Setter
   // @ApiModel("sd")
    public static class MyTeamOrderStatisticsObj{
        @ApiModelProperty(name = "date", value = "本月(MONTH)、全部(ALL)",required = false,example = "ALL")
        public String date;
       
    }
    
    /*我的团队-下单员列表
    @Getter
    @Setter
    //@ApiModel("sd")
    public static class OrderManListObj{
        @ApiModelProperty(name = "currentPage", value = "当前页",required = false,example = "1")
        public Integer currentPage;
       
    }*/
    
    /*会员列表-订单统计*/
    @Getter
    @Setter
    public static class MemberListOrderStatisticsObj{
    	 @ApiModelProperty(name = "date", value = "本月(MONTH)、全部(ALL)",required = false,example = "ALL")
         public String date;
		@ApiModelProperty(name = "orderManId", value = "下单员Id",required = false,example = "1")
		public Integer orderManId;
    }
    
    /*会员列表-会员列表*/
    @Getter
    @Setter
    public static class MemberListObj extends PageVo{
    	 @ApiModelProperty(name = "orderManId", value = "下单员id",required = false,example = "10")
         public String orderManId;
       
    }
    
    /*订单列表-用户详情*/
    @Getter
    @Setter
    public static class MemberDetailsObj{
    	 @ApiModelProperty(name = "mobile", value = "收货联系电话",required = false,example = "15019286959")
         public String mobile;
    	 @ApiModelProperty(name = "receiveAddressId", value = "收货地址id",required = false,example = "1")
         public String receiveAddressId;
    	 @ApiModelProperty(name = "memberId", value = "会员id",required = false,example = "1")
         public String memberId;
    	 @ApiModelProperty(name = "date", value = "本月(MONTH)、全部(ALL)",required = false,example = "ALL")
         public String date;
    	 @ApiModelProperty(name = "type", value = "memberInfo(下单员列表进入)或者addressInfo(地址列表进入)",required = false,example = "addressInfo")
         public String type;
       
       
    }

  
    public static class customerAddressList{
        @ApiModelProperty(name = "pageSize", value = "页的大小",required = true,example = "10")
        public Integer pageSize;
        @ApiModelProperty(name = "currentPage", value = "当前页",required = true,example = "1")
        public Integer currentPage;
        
        public Integer getPageSize() {
    	    return pageSize;
    	}

    	public void setPageSize(Integer pageSize) {
    	    this.pageSize = pageSize;
    	 }
    	public Integer getCurrentPage() {
    	    return currentPage;
    	}

    	public void setCurrentPage(Integer currentPage) {
    	    this.currentPage = currentPage;
    	 }
    }
    @Getter
    @Setter
    public static class addCustomerAddress{
    	@ApiModelProperty(name = "receiverName", value = "收货人姓名",required = true,example = "赵大豪")
        public String receiverName;
    	@ApiModelProperty(name = "sex", value = "性别 0 女 1 男",required = true,example = "1")
    	public String sex;
    	@ApiModelProperty(name = "mobile", value = "收货人手机号",required = true,example = "13582459865")
    	public String mobile;
    	@ApiModelProperty(name = "addressOne", value = "省市区街道code",required = true,example = "3")
    	 public Integer addressOne;
    	@ApiModelProperty(name = "addressTwo", value = "详细地址",required = true,example = "豪大大集团")
    	public String addressTwo;
    }
    
    public static class helpCustomerUpOrder{
    	@ApiModelProperty(name = "receiveAddressId", value = "顾客的地址ID",required = true,example = "1")
    	public Integer receiveAddressId;
    	
    	public Integer getReceiveAddressId() {
    	    return receiveAddressId;
    	}

    	public void setReceiveAddressId(Integer receiveAddressId) {
    	    this.receiveAddressId = receiveAddressId;
    	 }
    }
  
    public static class statisticalOrder{
    	@ApiModelProperty(name = "date", value = "状态",required = true,example = "MONTH或者ALL")
    	public String date;
    	
    	public String getDate() {
     	   return date;
     	 }

     	public void setDate(String date) {
     	   this.date = date;
     	}
    }

    public static class openPromoterDuration{
    	@ApiModelProperty(name = "promoterDuration", value = "开通会员时间", required = true, example = "12")
    	public Integer promoterDuration;
    	@ApiModelProperty(name = "memberLevel", value = "开通会员等级", required = true, example = "SENIOR")
    	public String memberLevel;
    	@ApiModelProperty(name = "userId", value = "开通会员Id", required = true, example = "1")
    	public Integer userId;
    	
    	public Integer getUserId(){
    		return userId;
    	}
    	
    	public void setUserId(Integer userId){
    		this.userId=userId;
    	}
    	
    	public String getMemberLevel(){
    		return memberLevel;
    	}
    	
    	public void setMemberLevel(String memberLevel){
    		this.memberLevel=memberLevel;
    	}
    
    	public Integer getPromoterDuration() {
     	   return promoterDuration;
     	 }

     	public void setPromoterDuration(Integer promoterDuration) {
     	   this.promoterDuration = promoterDuration;
     	}
    }
    @Getter
    @Setter
    public static class addPromoterAddress{
    	@ApiModelProperty(name = "userId", value = "会员ID",required = true,example = "1")
        public Integer userId;
    	@ApiModelProperty(name = "receiverName", value = "收货人姓名",required = true,example = "赵大豪")
        public String receiverName;
    	@ApiModelProperty(name = "sex", value = "性别 0 女 1 男",required = true,example = "1")
    	public String sex;
    	@ApiModelProperty(name = "mobile", value = "收货人手机号",required = true,example = "13582459865")
    	public String mobile;
    	@ApiModelProperty(name = "addressOne", value = "省市区街道code",required = true,example = "3")
    	 public Integer addressOne;
    	@ApiModelProperty(name = "addressTwo", value = "详细地址",required = true,example = "豪大大集团")
    	public String addressTwo;
    }
  
    public static class openPromoter{
    	@ApiModelProperty( value = "会员手机号", required = true, example = "136585484531")
    	public String mobile;

    	public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    	public String getMobile() {
            return mobile;
        }
    }

	@Setter
	@Getter
	@ApiModel("搜索参数类")
	public static class EsConfig  extends BaseEntity{
		@ApiModelProperty(value = "搜索框查询的关键字",required = true,example = "乐事薯片")
		public String key;
		@ApiModelProperty(value = "条形码",example = "\"123456789\"")
		public String barcode;
		@ApiModelProperty(value = "分类id",example = "1")
		public Integer categoryId;
		@ApiModelProperty(value = "排序方式:(NORMAL:综合;PRICE_UP:价格升序;PRICE_DOWN:价格降序;SPREAD_DOWN:价差降序)",example = "PRICE_UP")
		public String orderType;
		@ApiModelProperty(hidden = true)
		public Integer userId;
		@ApiModelProperty(value = "每页大小",example = "10")
		public Integer pageSize;
		@ApiModelProperty(value = "当前页(不要小于1)",example = "1")
		public Integer currentPage;
	}

	@Setter
	@Getter
	@ApiModel("搜索")
	public static class SearchSmallCommunityByKey {
		@ApiModelProperty(value = "搜索框查询的关键字",required = true,example = "中航")
		public String key;
		@ApiModelProperty(value = "每页大小",example = "10")
		public Integer pageSize;
		@ApiModelProperty(value = "当前页(不要小于1)",example = "1")
		public Integer currentPage;
	}

}
