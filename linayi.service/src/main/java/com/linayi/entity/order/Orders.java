package com.linayi.entity.order;

import com.linayi.entity.BaseEntity;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.user.ShoppingCar;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
@ApiModel(value = "订单实体类", description = "订单对象")
@Data
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
    private Integer orderGoodsTotalPrice;//商品总价
    @ApiModelProperty(hidden = true)
    private String createDate;//创建时间2
    @ApiModelProperty(hidden = true)
    private String createDateStr; //创建时间1
    @ApiModelProperty(value = "接受完整地址")
    private String address; //接受完整地址
    @ApiModelProperty(value = "网点联系电话")
    private String communityPhone;//网点联系电话
    @ApiModelProperty(value = "发货时间")
    private Date deliveryTime;//发货时间
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
    @ApiModelProperty(value = "采买员名字")
    private String buyUserName;//采买员名字

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
    @ApiModelProperty(value = "小区Id", hidden = true)
    private Integer smallCommunityId;
    @ApiModelProperty(value = "小区Id集合")
    private List<Integer> smallCommunityIdList;
    private String sex;
    @ApiModelProperty(value = "实际采买数量")
    private Integer actualQuantity;

    @ApiModelProperty(value = "合计数量")
    private Integer totalQuantity;

    List<OrdersGoods> ordersGoodsList;
}
