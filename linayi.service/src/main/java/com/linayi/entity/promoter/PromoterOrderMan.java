package com.linayi.entity.promoter;

import com.linayi.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class PromoterOrderMan extends BaseEntity {
    private Integer promoterOrderManId;

    private Integer promoterId;

    @ApiModelProperty(name = "orderManId", value = "下单员id")
    private Integer orderManId;

    @ApiModelProperty(name = "identity", value = "下单员类型（下单员or法人）")
    private String identity;

    private Date updateTime;

    @ApiModelProperty(name = "createTime", value = "创建时间")
    private Date createTime;

    @ApiModelProperty(name = "parentType", value = "上级类型")
    private String parentType;

    @ApiModelProperty(name = "headImage", value = "头像")
    private String headImage;            //头像(首页)

    @ApiModelProperty(name = "homePageIncome", value = "首页总成交额")
    private Integer homePageIncome;            //总成交额(首页)

    @ApiModelProperty(name = "numberOfOrders", value = "订单数")
    private Integer numberOfOrders;            //订单数(通用)

    @ApiModelProperty(name = "totalSum", value = "订单合计金额")
    private Integer totalSum;            //订单合计金额(通用)

    @ApiModelProperty(name = "totalHundredSum", value = "订单合计大于或等于100金额")
    private Integer totalHundredSum;            //订单合计大于或等于100金额(通用)

    @ApiModelProperty(name = "numberOfMembers", value = "会员数量")
    private Integer numberOfMembers;            //会员数量(通用)

    @ApiModelProperty(name = "numberOfOrderMan", value = "下单员数量")
    private Integer numberOfOrderMan;            //下单员数量(通用)

    @ApiModelProperty(name = "numberOfUser", value = "客户数量")
    private Integer numberOfUser;            //客户数量(合计客户)

    @ApiModelProperty(name = "orderProfit", value = "订单额返利")
    private Integer orderProfit;            //订单额返利

    @ApiModelProperty(name = "orderStatisticsData3", value = "订单统计接口第三个显示数据")
    private Integer orderStatisticsData3;            //订单统计接口第三个显示数据（适应前端框架，将每个订单统计页面第三个数据存到属性返回给前端）

    @ApiModelProperty(name = "nickname", value = "昵称")
    private String nickname;            //昵称

    @ApiModelProperty(name = "openMemberTime", value = "会员开通时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date openMemberTime;            //会员开通时间

    @ApiModelProperty(name = "mobile", value = "电话号码")
    private String mobile;            //电话号码

    @ApiModelProperty(name = "address", value = "地址")
    private String address;            //地址

    private Integer userId;

    private String date;            //用于查询订单统计时时查询本月还是全部	本月MONTH 	全部:ALL

    private Integer salesId;
}
