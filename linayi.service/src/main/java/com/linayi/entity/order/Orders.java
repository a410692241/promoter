package com.linayi.entity.order;

import com.linayi.entity.BaseEntity;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.user.ShoppingCar;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
@ApiModel(value = "订单实体类", description = "订单对象")
public class Orders extends BaseEntity {
    @ApiModelProperty(value = "订单Id也是订单编号")
    private Long ordersId;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "接收人")
    private String receiverName;
    @ApiModelProperty(value = "收货人联系电话")
    private String mobile;
    @ApiModelProperty(value = "地区码")
    private String addressOne;
    @ApiModelProperty(value = "小区名称")
    private String addressTwo;
    @ApiModelProperty(value = "门牌号")
    private String addressThree;
    @ApiModelProperty(value = "支付方式")
    private String payWay;
    @ApiModelProperty(value = "比价优惠")
    private Integer saveAmount;
    @ApiModelProperty(value = "服务费")
    private Integer serviceFee;
    @ApiModelProperty(value = "附加费用")
    private Integer extraFee;
    @ApiModelProperty(value = "预计送到时间")
    private Date arriveTime;
    @ApiModelProperty(value = "实际送达时间")
    private Date actualArriveTime;
    @ApiModelProperty(value = "买家留言")
    private String remark;
    @ApiModelProperty(value = "下单商品数")
    private Integer quantity;
    @ApiModelProperty(value = "下单金额")
    private Integer amount;
    @ApiModelProperty(value = "用户端订单状态", hidden = true)
    private String userStatus;
    @ApiModelProperty(value = "社区端订单状态", hidden = true)
    private String communityStatus;
    @ApiModelProperty(value = "社区Id")
    private Integer communityId;
    @ApiModelProperty(value = "社区名")
    private String communityName;
    @ApiModelProperty(value = "箱号")
    private String boxNo;//箱号
    @ApiModelProperty(value = "封箱图片")
    private String image;//封箱图片
    @ApiModelProperty(value = "配送员ID")
    private Integer delivererId;//配送员ID
    @ApiModelProperty(value = "封箱时间")
    private Date boxTime;//封箱时间
    @ApiModelProperty(value = "配送完成时间")
    private Date deliveryFinishTime;//配送完成时间
    @ApiModelProperty(hidden = true)
    private Date updateTime;
    @ApiModelProperty(hidden = true)
    private Date createTime;
    @ApiModelProperty(value = "收货地址Id")
    private Integer receiveAddressId;
    @ApiModelProperty(value = "封装订单商品详情信息")
    private List<ShoppingCar> shoppingCarList;
    @ApiModelProperty(value = "封装商品详情信息")
    private List<GoodsSku> goodsSkuList;
    @ApiModelProperty(value = "合计价格")
    private String totalPrice;//合计价格
    @ApiModelProperty(value = "实付价格")
    private String payPrice;//实付价格
    @ApiModelProperty(value = "商品总价")
    private String goodsTotalPrice;//商品总价
    @ApiModelProperty(hidden = true)
    private String createDate;//创建时间2
    @ApiModelProperty(hidden = true)
    private String createDateStr; //创建时间1
    @ApiModelProperty(value = "接受完整地址")
    private String address; //接受完整地址
    @ApiModelProperty(value = "网点联系电话")
    private String communityPhone;//网点联系电话
    @ApiModelProperty(value = "发货时间")
    private String deliveryTime;//发货时间
    @ApiModelProperty(value = "收货时间")
    private String receiptTime;//收货时间
    @ApiModelProperty(value = "支付编号")
    private String payNumber;//支付编号
    @ApiModelProperty(value = "附加费用")
    private String extraFeeString;//附加费用
    @ApiModelProperty(value = "服务费")
    private String serviceFeeString;//服务费
    @ApiModelProperty(value = "客服电话")
    private String serviceMobile;//客服电话
    @ApiModelProperty(value = "订单配送状态")
    private String deliverStatus;//订单配送状态
    @ApiModelProperty(value = "配送员名字")
    private String delivererName;//配送员名字

    @ApiModelProperty(value = "订单状态",example = "进行中：IN_PROGRESS,已取消：CANCELED ,已完成：FINISHED ,已拒收：REFUSED,已退款：REFUNDED," +
            "采买中：PROCURING,采买完成：PROCURE_FINISHED,全部已收货（社区端）：RECEIVED,已装箱：PACKED,配送中：DELIVERING,配送完成：DELIVER_FINISHED\n")
    private String status;
    @ApiModelProperty(value = "订单类型")
    private String orderType;
    @ApiModelProperty(hidden = true)
    private String range;
    @ApiModelProperty(value = "地址类型  我的地址：MINE  顾客的地址：CUSTOMER")
    private String addressType;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    @ApiModelProperty(hidden = true)
    /*创建开始时间*/
    private String createTimeStart;
    /*创建结束时间*/
    @ApiModelProperty(hidden = true)
    private String createTimeEnd;

    @ApiModelProperty(value = "顾客姓名")
    private String customerName;
    @ApiModelProperty(value = "下单员Id", hidden = true)
    private Integer orderManId;

    private Integer promoterId;

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getDelivererName() { return delivererName; }

    public void setDelivererName(String delivererName) { this.delivererName = delivererName; }

    public String getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(String deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo == null ? null : addressTwo.trim();
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay == null ? null : payWay.trim();
    }

    public Integer getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(Integer saveAmount) {
        this.saveAmount = saveAmount;
    }

    public Integer getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Integer serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Integer getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(Integer extraFee) {
        this.extraFee = extraFee;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getActualArriveTime() {
        return actualArriveTime;
    }

    public void setActualArriveTime(Date actualArriveTime) {
        this.actualArriveTime = actualArriveTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
    }

    public String getCommunityStatus() {
        return communityStatus;
    }

    public void setCommunityStatus(String communityStatus) {
        this.communityStatus = communityStatus == null ? null : communityStatus.trim();
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
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

    public Integer getReceiveAddressId() {
        return receiveAddressId;
    }

    public void setReceiveAddressId(Integer receiveAddressId) {
        this.receiveAddressId = receiveAddressId;
    }

    public List<ShoppingCar> getShoppingCarList() {
        return shoppingCarList;
    }

    public void setShoppingCarList(List<ShoppingCar> shoppingCarList) {
        this.shoppingCarList = shoppingCarList;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCommunityPhone() {
        return communityPhone;
    }

    public void setCommunityPhone(String communityPhone) {
        this.communityPhone = communityPhone;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }

    public String getAddressThree() {
        return addressThree;
    }

    public void setAddressThree(String addressThree) {
        this.addressThree = addressThree;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public String getExtraFeeString() {
        return extraFeeString;
    }

    public void setExtraFeeString(String extraFeeString) {
        this.extraFeeString = extraFeeString;
    }

    public String getServiceFeeString() {
        return serviceFeeString;
    }

    public void setServiceFeeString(String serviceFeeString) {
        this.serviceFeeString = serviceFeeString;
    }

    public String getServiceMobile() {
        return serviceMobile;
    }

    public void setServiceMobile(String serviceMobile) {
        this.serviceMobile = serviceMobile;
    }

    public String getGoodsTotalPrice() {
        return goodsTotalPrice;
    }

    public void setGoodsTotalPrice(String goodsTotalPrice) {
        this.goodsTotalPrice = goodsTotalPrice;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public Orders(Long ordersId, Integer userId, String receiverName, String mobile, String addressOne, String addressTwo,
                  String addressThree, String payWay, Integer saveAmount, Integer serviceFee, Integer extraFee, Date arriveTime,
                  Date actualArriveTime, String remark, Integer quantity, Integer amount, String userStatus, String communityStatus,
                  Integer communityId, String communityName, String boxNo, String image, Integer delivererId, Date boxTime,
                  Date deliveryFinishTime, Date updateTime, Date createTime, Integer receiveAddressId, List<ShoppingCar> shoppingCarList,
                  List<GoodsSku> goodsSkuList, String totalPrice, String payPrice, String goodsTotalPrice, String createDate,
                  String createDateStr, String address, String communityPhone, String deliveryTime, String receiptTime, String payNumber,
                  String extraFeeString, String serviceFeeString, String serviceMobile, String deliverStatus, String delivererName) {
        this.ordersId = ordersId;
        this.userId = userId;
        this.receiverName = receiverName;
        this.mobile = mobile;
        this.addressOne = addressOne;
        this.addressTwo = addressTwo;
        this.addressThree = addressThree;
        this.payWay = payWay;
        this.saveAmount = saveAmount;
        this.serviceFee = serviceFee;
        this.extraFee = extraFee;
        this.arriveTime = arriveTime;
        this.actualArriveTime = actualArriveTime;
        this.remark = remark;
        this.quantity = quantity;
        this.amount = amount;
        this.userStatus = userStatus;
        this.communityStatus = communityStatus;
        this.communityId = communityId;
        this.communityName = communityName;
        this.boxNo = boxNo;
        this.image = image;
        this.delivererId = delivererId;
        this.boxTime = boxTime;
        this.deliveryFinishTime = deliveryFinishTime;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.receiveAddressId = receiveAddressId;
        this.shoppingCarList = shoppingCarList;
        this.goodsSkuList = goodsSkuList;
        this.totalPrice = totalPrice;
        this.payPrice = payPrice;
        this.goodsTotalPrice = goodsTotalPrice;
        this.createDate = createDate;
        this.createDateStr = createDateStr;
        this.address = address;
        this.communityPhone = communityPhone;
        this.deliveryTime = deliveryTime;
        this.receiptTime = receiptTime;
        this.payNumber = payNumber;
        this.extraFeeString = extraFeeString;
        this.serviceFeeString = serviceFeeString;
        this.serviceMobile = serviceMobile;
        this.deliverStatus = deliverStatus;
        this.delivererName = delivererName;
    }

    public Orders() {
		super();
	}

    @Override
    public String toString() {
        return "Orders{" +
                "ordersId=" + ordersId +
                ", userId=" + userId +
                ", receiverName='" + receiverName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", addressOne='" + addressOne + '\'' +
                ", addressTwo='" + addressTwo + '\'' +
                ", addressThree='" + addressThree + '\'' +
                ", payWay='" + payWay + '\'' +
                ", saveAmount=" + saveAmount +
                ", serviceFee=" + serviceFee +
                ", extraFee=" + extraFee +
                ", arriveTime=" + arriveTime +
                ", actualArriveTime=" + actualArriveTime +
                ", remark='" + remark + '\'' +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", userStatus='" + userStatus + '\'' +
                ", communityStatus='" + communityStatus + '\'' +
                ", communityId=" + communityId +
                ", communityName='" + communityName + '\'' +
                ", boxNo='" + boxNo + '\'' +
                ", image='" + image + '\'' +
                ", delivererId=" + delivererId +
                ", boxTime=" + boxTime +
                ", deliveryFinishTime=" + deliveryFinishTime +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", receiveAddressId=" + receiveAddressId +
                ", shoppingCarList=" + shoppingCarList +
                ", goodsSkuList=" + goodsSkuList +
                ", totalPrice='" + totalPrice + '\'' +
                ", payPrice='" + payPrice + '\'' +
                ", goodsTotalPrice='" + goodsTotalPrice + '\'' +
                ", createDate='" + createDate + '\'' +
                ", createDateStr='" + createDateStr + '\'' +
                ", address='" + address + '\'' +
                ", communityPhone='" + communityPhone + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", receiptTime='" + receiptTime + '\'' +
                ", payNumber='" + payNumber + '\'' +
                ", extraFeeString='" + extraFeeString + '\'' +
                ", serviceFeeString='" + serviceFeeString + '\'' +
                ", serviceMobile='" + serviceMobile + '\'' +
                ", deliverStatus='" + deliverStatus + '\'' +
                ", delivererName='" + delivererName + '\'' +
                '}';
    }

    public String getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getBoxTime() {
		return boxTime;
	}

	public void setBoxTime(Date boxTime) {
		this.boxTime = boxTime;
	}

	public Date getDeliveryFinishTime() {
		return deliveryFinishTime;
	}

	public void setDeliveryFinishTime(Date deliveryFinishTime) {
		this.deliveryFinishTime = deliveryFinishTime;
	}

	public Integer getDelivererId() {
		return delivererId;
	}

	public void setDelivererId(Integer delivererId) {
		this.delivererId = delivererId;
	}

	public List<GoodsSku> getGoodsSkuList() {
		return goodsSkuList;
	}

	public void setGoodsSkuList(List<GoodsSku> goodsSkuList) {
		this.goodsSkuList = goodsSkuList;
	}


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public Integer getOrderManId() {
        return orderManId;
    }

    public void setOrderManId(Integer orderManId) {
        this.orderManId = orderManId;
    }

    public Integer getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(Integer promoterId) {
        this.promoterId = promoterId;
    }
}