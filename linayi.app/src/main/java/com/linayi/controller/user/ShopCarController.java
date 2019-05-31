package com.linayi.controller.user;

import com.linayi.controller.BaseController;
import com.linayi.entity.user.ShoppingCar;
import com.linayi.exception.ErrorType;
import com.linayi.service.user.ShopCarService;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/goods/shopCar")
public class ShopCarController extends BaseController {

    @Autowired
    private ShopCarService shopCarService;

    /**
     * 加入购物车
     * @param param
     * @return
     */
    @RequestMapping("/addShopCar.do")
    public Object addShopCar(@RequestBody Map<String, Object> param) {
        ParamValidUtil<ShoppingCar> pvu = new ParamValidUtil<>(param);
        pvu.Exist("goodsSkuId", "quantity");
        try {
            ShoppingCar shoppingCar = pvu.transObj(ShoppingCar.class);
            shoppingCar.setUserId(getUserId());
            shoppingCar.setSelectStatus("NOT_SELECTED");
            try {
                String s = shopCarService.addShopCar(shoppingCar);
                if("no_price".equals(s)){
                    return new ResponseData(ErrorType.NO_PRICE);
                }
                return new ResponseData(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseData("F",ErrorType.OPERATION_FAIL.getErrorMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData(ErrorType.SYSTEM_ERROR);
    }

    /**
     * 购物车商品列表，合计
     * @param request
     * @return
     */
    @RequestMapping("/listAdd.do")
    public Object shopCarlistAdd(HttpServletRequest request){
        Map<String, Object> relust = null;
        try {
            ShoppingCar shoppingCar = new ShoppingCar();
            shoppingCar.setUserId(getUserId());
            //查询出所有购物车信息
            relust = shopCarService.shopCarlistAdd(shoppingCar,request);
            return new ResponseData(relust);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("F", ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

    /**
     * 选中商品，修改数量
     * @param param
     * @return
     */
    @RequestMapping("/updateGoodsNum.do")
    public Object updateGoodsNum(@RequestBody Map<String, Object> param){
        ParamValidUtil<ShoppingCar> pvu = new ParamValidUtil<>(param);
           pvu.Exist("shoppingCarId");
        try {
            ShoppingCar shoppingCar = pvu.transObj(ShoppingCar.class);
            shopCarService.updateGoodsNum(shoppingCar);
           return new ResponseData("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

    /**
     *购物车全选
     * @param param
     * @return
     */
    @RequestMapping("/shopcarSelectAll.do")
    public Object shopcarSelectAll(@RequestBody Map<String, Object> param){
        ParamValidUtil<ShoppingCar> pvu = new ParamValidUtil<>(param);
            pvu.Exist("selectStatus");
        try {
            ShoppingCar shoppingCar = pvu.transObj(ShoppingCar.class);
            shoppingCar.setUserId(getUserId());
            shopCarService.updateAllCarstatus(shoppingCar);
            return new ResponseData("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

    /**
     *删除购物车
     * @param param
     * @return
     */
    @RequestMapping("/deleteShopcar.do")
    public Object deleteShopcar(@RequestBody Map<String, Object> param){
        ParamValidUtil<ShoppingCar> pvu = new ParamValidUtil<>(param);
        //pvu.Exist("shoppingCarId","accessToken");
        try {
            shopCarService.deleteCarById(param.get("shoppingCarId"));
            return new ResponseData("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

    /**
     *确认订单
     * @param request
     * @return
     */
    @RequestMapping("/toOrder.do")
    public Object toOrder(HttpServletRequest request){
        //pvu.Exist("accessToken");
        Map<String, Object> relust = null;
        try {
            ShoppingCar shoppingCar = new ShoppingCar();
            shoppingCar.setUserId(getUserId());
            relust = shopCarService.toOrder(shoppingCar,request);
            return new ResponseData(relust);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

}
