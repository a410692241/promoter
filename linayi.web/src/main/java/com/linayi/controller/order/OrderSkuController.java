package com.linayi.controller.order;

import java.util.List;
import java.util.stream.Collectors;

import com.linayi.dao.area.SmallCommunityMapper;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.order.OrdersGoods;
import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.entity.user.ReceiveAddress;
import com.linayi.enums.MemberLevel;
import com.linayi.service.address.ReceiveAddressService;
import com.linayi.service.area.SmallCommunityService;
import com.linayi.service.goods.SupermarketGoodsService;
import com.linayi.service.order.OrdersGoodsService;
import com.linayi.service.procurement.ProcurementService;
import com.linayi.util.MemberPriceUtil;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.order.OrdersSku;
import com.linayi.service.order.OrderService;
import com.linayi.util.PageResult;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/order/orderSku")
public class OrderSkuController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrdersGoodsService ordersGoodsService;
    @Autowired
    private ProcurementService procurementService;
    @Autowired
    private ReceiveAddressService receiveAddressService;
    @Autowired
    private SupermarketGoodsService supermarketGoodsService;
    

    @RequestMapping("list.do")
    @ResponseBody
    public Object showOrderSku(OrdersSku ordersSku){
        List<OrdersSku> ordersSkus =orderService.getGoodsOrderSku(ordersSku);
//        List<ProcurementTask> pt= orderService.selectProcuringByOrdersId(procurementTask);
        return ordersSkus;
    }

    @RequestMapping("pList.do")
    @ResponseBody
    public Object showAllPurchase(ProcurementTask procurementTask){
  //      List<OrdersSku> ordersAllSkus =orderService.getAllGoodsOrderSku(ordersSku);
        List<ProcurementTask> procurementTasks = orderService.selectAllProcurementTask(procurementTask);
        PageResult<ProcurementTask> ordersSkuPageResult = new PageResult<ProcurementTask>(procurementTasks,procurementTask.getTotal());
        return ordersSkuPageResult;
    }

    @RequestMapping("sPList.do")
    @ResponseBody
    public Object showAllSupermarketPrice(OrdersSku ordersSku){
        OrdersSku oS =orderService.getOrderSupermarketList(ordersSku);
//        List<SupermarketGoods> supermarketGoods = orderService.showAllPurchase(ordersSku);
        List<ProcurementTask> procurementTasks = orderService.procurementTaskList(oS);
        return procurementTasks;
    }

    @RequestMapping("orderSupermarketList.do")
    public Object orderSupermarketList(ProcurementTask procurementTask){

        ProcurementTask procurementTask1 = procurementService.getProcurementTask(procurementTask);
        List<SupermarketGoods> supermarketGoodsList = supermarketGoodsService.getSupermarketGoodsList(procurementTask1.getGoodsSkuId(), procurementTask1.getCommunityId());
        MemberPriceUtil.supermarketPriceByLevel(MemberLevel.SUPER,supermarketGoodsList);
        ModelAndView mv = new ModelAndView("jsp/order/OrderDetail");
        ProcurementTask procurementTask2 = new ProcurementTask();
        procurementTask2.setOrdersId(procurementTask1.getOrdersId());
        procurementTask2.setOrdersGoodsId(procurementTask1.getOrdersGoodsId());
        List<ProcurementTask> data = procurementService.getProcurements(procurementTask2);

        List<Integer> collect = data.stream().map(p -> p.getSupermarketId()).collect(Collectors.toList());
        List<SupermarketGoods> allSpermarketGoodsList = MemberPriceUtil.allSpermarketGoodsList;
        List<SupermarketGoods> supermarketGoodsList1 = allSpermarketGoodsList.stream().filter(supermarket -> !collect.contains(supermarket.getSupermarketId())).collect(Collectors.toList());

        mv.addObject("procurementTaskId", procurementTask.getProcurementTaskId());
        mv.addObject("spermarketGoodsList",supermarketGoodsList1);

        return mv;
    }
    //订单商品按次低价购买
    @RequestMapping("/buySecondHeigh.do")
    @ResponseBody
    public Object buySecondHeigh(ProcurementTask procurementTask){
        try {
            Integer supermarketId = procurementTask.getSupermarketId();
            if (supermarketId == null || supermarketId <=0){
                return new ResponseData("no_supermarketId");
            }
            orderService.buySecondHeigh(procurementTask);
            return new ResponseData("success");
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseData("defeat");
    }

    //取消购买订单商品
    @RequestMapping("/cancelBuy.do")
    @ResponseBody
    public Object cancelBuy(ProcurementTask procurementTask){
        try {
            procurementService.updateOrdersGoodsStatus(procurementTask);
            return new ResponseData("success");
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseData("defeat");
    }

    @RequestMapping("/orderGoodsIsDeal.do")
    @ResponseBody
    public Object orderGoodsIsDeal(ProcurementTask procurementTask){
        ProcurementTask procurement1 =null;
        try {
            procurement1 = procurementService.getProcurementTaskLast(procurementTask);
        }catch (Exception e){
            e.printStackTrace();
        }
        return procurement1;
    }
}
