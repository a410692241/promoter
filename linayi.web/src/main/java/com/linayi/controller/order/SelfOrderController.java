package com.linayi.controller.order;

import com.google.gson.Gson;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.order.SelfOrder;
import com.linayi.entity.user.ReceiveAddress;
import com.linayi.entity.user.User;
import com.linayi.exception.ErrorType;
import com.linayi.service.address.ReceiveAddressService;
import com.linayi.service.goods.SupermarketGoodsService;
import com.linayi.service.order.SelfOrderService;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("selfOrder")
public class SelfOrderController {

    @Resource
    private SelfOrderService selfOrderService;

    @Resource
    private SupermarketGoodsService supermarketGoodsService;

    @Autowired
    ReceiveAddressService receiveAddressService;

    @RequestMapping("/selfOrderlist.do")
    @ResponseBody
    public PageResult<SelfOrder> getRoleList(SelfOrder selfOrder) {

        List<SelfOrder> list = selfOrderService.getList(selfOrder);
        PageResult<SelfOrder> pageResult = new PageResult<>(list, selfOrder.getTotal());
        return pageResult;
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public ResponseData save(SelfOrder selfOrder) {
        ResponseData rr = null;
        try {
            selfOrderService.updateSelfOrder(selfOrder);
            rr = new ResponseData(selfOrder);
        } catch (Exception e) {
            rr = new ResponseData(ErrorType.SYSTEM_ERROR);
        }
        return rr;
    }

    @RequestMapping("/edit.do")
    public ModelAndView updateSelfOrder(SelfOrder selfOrder) {
        ModelAndView mv = new ModelAndView("jsp/order/SelfOrderEdit");
        if (selfOrder.getSelfOrderId() != null) {
            Gson gson = new Gson();
            selfOrder = selfOrderService.getSelfOrder(selfOrder);
            Map<String, Object> selfOrderMap = gson.fromJson(gson.toJson(selfOrder), Map.class);
            if (selfOrder.getMinPrice() != -1){
                selfOrderMap.put("minPrice", String.format("%.2f", selfOrder.getMinPrice() / 100.0));
            }
            if (selfOrder.getMaxPrice() != -1){
                selfOrderMap.put("maxPrice", String.format("%.2f", selfOrder.getMaxPrice() / 100.0));
            }
            mv.addObject("selfOrder", selfOrderMap);
        }
        return mv;
    }

    @RequestMapping("/share.do")
    @ResponseBody
    public ResponseData share(SelfOrder selfOrder) {
        ResponseData rr = new ResponseData(ErrorType.SYSTEM_ERROR);
        try {
            if (selfOrder.getSelfOrderId() != null || selfOrder.getUserId() != null) {
                selfOrderService.sharePrice(selfOrder);
                rr = new ResponseData(selfOrder);
            }
            return rr;
        } catch (Exception e) {
            e.printStackTrace();
            return rr;
        }
    }

    @RequestMapping("/shareTaskView.do")
    public ModelAndView shareTaskView(SelfOrder selfOrder) {
        ModelAndView mv = new ModelAndView("jsp/order/shareTaskView");
        if (selfOrder.getSelfOrderId() != null) {
            List<Map> selfOrderMessages = selfOrderService.listSelfOrderMessage(selfOrder.getSelfOrderId());
            mv.addObject("selfOrderMessages", selfOrderMessages);
        }
        return mv;
    }

    @RequestMapping("/searchGoods.do")
    @ResponseBody
    public PageResult searchGoods(GoodsSku goodsSku) {
        try {
            List<GoodsSku> goodsSkuList = selfOrderService.searchGoods(goodsSku);
            PageResult<GoodsSku> goodsSkuPageResult = new PageResult<>(goodsSkuList, goodsSku.getTotal());
            return goodsSkuPageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @RequestMapping("/searchSupermarketPrice.do")
    @ResponseBody
    public PageResult searchSupermarketPrice(
            @RequestParam("userId") Integer userId,
            @RequestParam("goodsSkuId") Integer goodsSkuId
    ) {
        try {
            Map result = supermarketGoodsService.getPriceSupermarketByGoodsSkuId(userId,
                    goodsSkuId);
            return new PageResult((List) result.get("supermarketGoodsList"), 5);
        } catch (Exception e) {
            return new PageResult(new LinkedList<>(), 0);
        }
    }

    @RequestMapping("/turnToOrder.do")
    @ResponseBody
    public ResponseData turnToOrder(
            @RequestParam("selfOrderId") Long selfOrderId,
            @RequestParam("userId") Integer userId,
            @RequestParam("goodsSkuId") Integer goodsSkuId,
            @RequestParam("num") Integer num,
            @RequestParam("amount") Integer amount,
            @RequestParam("saveAmount") Integer saveAmount,
            @RequestParam("serviceFee") Integer serviceFee,
            @RequestParam("extraFee") Integer extraFee
    ) {
        try {
            selfOrderService.turnSelfOrderToOrder(selfOrderId, userId, goodsSkuId, num, amount, saveAmount, serviceFee, extraFee);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
        return new ResponseData(new HashMap());
    }

    @RequestMapping("/contactUser.do")
    public ModelAndView contactUser(@RequestParam("userId") Integer userId) {
        ModelAndView mv = new ModelAndView("jsp/order/contactUser");
        if (userId != null) {
            User userInfo = selfOrderService.selectUserById(userId);
            ReceiveAddress address = receiveAddressService.getDefaultReceiveAddress(userInfo);
            if (address != null) {
                userInfo.setMobile(address.getMobile());
            }
            mv.addObject("userInfo", userInfo);
        }
        return mv;
    }

}
