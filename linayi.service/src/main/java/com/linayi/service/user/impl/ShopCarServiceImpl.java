package com.linayi.service.user.impl;

import com.linayi.dao.address.ReceiveAddressMapper;
import com.linayi.dao.area.SmallCommunityMapper;
import com.linayi.dao.goods.AttributeMapper;
import com.linayi.dao.goods.GoodsSkuMapper;
import com.linayi.dao.user.ShoppingCarMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.promoter.OpenMemberInfo;
import com.linayi.entity.user.ReceiveAddress;
import com.linayi.entity.user.ShoppingCar;
import com.linayi.entity.user.User;
import com.linayi.service.goods.BrandService;
import com.linayi.service.goods.SupermarketGoodsService;
import com.linayi.service.promoter.OpenMemberInfoService;
import com.linayi.service.user.ShopCarService;
import com.linayi.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ShopCarServiceImpl implements ShopCarService {
    @Autowired
    private ShoppingCarMapper shoppingCarMapper;
    @Autowired
    private GoodsSkuMapper goodsSkuMapper;
    @Autowired
    private SupermarketGoodsService supermarketGoodsService;
    @Autowired
    private ReceiveAddressMapper receiveAddressMapper;
    @Autowired
    private SmallCommunityMapper smallCommunityMapper;
    @Autowired
    private OpenMemberInfoService openMemberInfoService;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void addShopCar(ShoppingCar shoppingCar) {
        User user = userMapper.selectUserByuserId(shoppingCar.getUserId());
        Integer receiveAddressId = user.getDefaultReceiveAddressId();
        shoppingCar.setReceiveAddressId(receiveAddressId);
        ShoppingCar shopCar = shoppingCarMapper.getShopCar(shoppingCar);
        if (shopCar != null){
            shopCar.setQuantity(shopCar.getQuantity() + shoppingCar.getQuantity());
            shoppingCarMapper.updateShopCar(shopCar);
        }else {
            shoppingCarMapper.insert(shoppingCar);
        }
    }

    @Override
    public Map<String, Object> shopCarlistAdd(ShoppingCar shoppingCar, HttpServletRequest request) {
        User user = userMapper.selectUserByuserId(shoppingCar.getUserId());
        Integer receiveAddressId = user.getDefaultReceiveAddressId();
        shoppingCar.setReceiveAddressId(receiveAddressId);
        List<ShoppingCar> ShoppingCars = shoppingCarMapper.getAllCarByReceiveAddressId(shoppingCar);
        Map<String, Object> map = new HashMap<>();
        Integer totalPrice = 0;//合计总价格
        ReceiveAddress receiveAddress = new ReceiveAddress();
        receiveAddress.setReceiveAddressId(receiveAddressId);
        ReceiveAddress receiveAddress1 = receiveAddressMapper.selectAddbyacGdAdId(receiveAddress);
        SmallCommunity smallCommunity = new SmallCommunity();
        smallCommunity.setSmallCommunityId(receiveAddress1.getAddressOne());
        smallCommunity = smallCommunityMapper.getSmallCommunity(smallCommunity);
        for (ShoppingCar car : ShoppingCars) {
            //查出商品的最高价格最低价格
            List<SupermarketGoods> supermarketGoodsList = supermarketGoodsService.getSupermarketGoodsList(car.getGoodsSkuId(), smallCommunity.getCommunityId());
            Integer minPrice = 0;
            if (supermarketGoodsList != null && supermarketGoodsList.size() > 0){
                OpenMemberInfo theLastOpenMemberInfo = openMemberInfoService.getTheLastOpenMemberInfo(shoppingCar.getUserId());
                minPrice = supermarketGoodsList.get(MemberPriceUtil.memberPriceByLevel(theLastOpenMemberInfo,supermarketGoodsList.size())).getPrice();
            }
            car.setMinPrice(getpriceString(minPrice));
            car.setMinSupermarketName(supermarketGoodsList.get(supermarketGoodsList.size() - 1).getSupermarketName());
            GoodsSku goodsSku = goodsSkuMapper.getGoodsById(car.getGoodsSkuId());

            //图片信息处理
            car.setGoodsSkuImage(ImageUtil.dealToShow(goodsSku.getImage()));
            car.setGoodsName(goodsSku.getFullName());
            if ("SELECTED".equals(car.getSelectStatus())){
                //List<SupermarketGoods> supermarketGoodss = getSupermarketGoodsByGoodsSkuId(car);
//                car.setHeJiPrice(getpriceString(car.getQuantity() * supermarketGoods.getPrice()));
                totalPrice += car.getQuantity() *  minPrice;
            }

        }
        map.put("ShoppingCars",ShoppingCars);
        map.put("totalPrice",getpriceString(totalPrice));
        return map;
    }

    public String getpriceString(Integer Price) {
        StringBuffer sb = new StringBuffer(Price + "");
        if(sb.length() >= 3){
            sb.insert(sb.toString().length() - 2, ".");
        }else if(sb.length() == 2){
            return "0." + sb.toString();
        }else if(sb.length() == 1){
            return "0.0" + sb.toString();
        }
        return sb.toString();
    }

    /**
     * 根据购物车商品id查看超市商品价格表
     * @param car
     * @return
     */
    public List<SupermarketGoods> getSupermarketGoodsByGoodsSkuId(ShoppingCar car) {
        SupermarketGoods superGoods = new SupermarketGoods();
        superGoods.setGoodsSkuId(Long.parseLong(car.getGoodsSkuId() + ""));
        return supermarketGoodsService.getSupermarketGoods(superGoods);
    }

    @Override
    public void updateGoodsNum(ShoppingCar shoppingCar) {
        shoppingCarMapper.updateShopCar(shoppingCar);
    }

    @Transactional
    @Override
    public void updateAllCarstatus(ShoppingCar car) {
        User user = userMapper.selectUserByuserId(car.getUserId());
        Integer receiveAddressId = user.getDefaultReceiveAddressId();
        car.setReceiveAddressId(receiveAddressId);
        String selectStatus1 = car.getSelectStatus();
        car.setSelectStatus(null);
        List<ShoppingCar> ShoppingCars = shoppingCarMapper.getAllCarByReceiveAddressId(car);
        for (ShoppingCar shoppingCar : ShoppingCars) {
            ShoppingCar shoppingCar1 = new ShoppingCar();
            shoppingCar1.setShoppingCarId(shoppingCar.getShoppingCarId());
            if (CheckUtil.isNotNullEmpty(selectStatus1)){
                shoppingCar1.setSelectStatus(selectStatus1);
                shoppingCarMapper.updateShopCar(shoppingCar1);
            }
        }
    }

    @Override
    public void deleteCarById(Object shoppingCarId) {
        shoppingCarMapper.deleteCarById(Integer.valueOf("" + shoppingCarId) );
    }

    @Override
    public Map<String, Object> toOrder(ShoppingCar shoppingCar, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        User user = userMapper.selectUserByuserId(shoppingCar.getUserId());
        Integer receiveAddressId = user.getDefaultReceiveAddressId();
        shoppingCar.setReceiveAddressId(receiveAddressId);
        shoppingCar.setSelectStatus("SELECTED");
        //所有购物车
        List<ShoppingCar> shoppingCars = shoppingCarMapper.getAllCarByReceiveAddressId(shoppingCar);
        ReceiveAddress receiveAddress = new ReceiveAddress();
        receiveAddress.setReceiveAddressId(shoppingCar.getReceiveAddressId());
        ReceiveAddress receiveAddress1 = receiveAddressMapper.selectAddbyacGdAdId(receiveAddress);
        SmallCommunity smallCommunity = new SmallCommunity();
        smallCommunity.setSmallCommunityId(receiveAddress1.getAddressOne());
        smallCommunity = smallCommunityMapper.getSmallCommunity(smallCommunity);

        // 总价
        Integer totalPrice = 0;
        //比价优惠
        Integer offerPrice = 0;
        //服务费
        result.put("serviceFee", getpriceString(ConstantUtil.SERVICE_FEE));
        // 附加费用
        result.put("additionalFees",getpriceString(ConstantUtil.ADDITIONAL_FEES));
        totalPrice = ConstantUtil.SERVICE_FEE + ConstantUtil.ADDITIONAL_FEES;
        // 预计送达时间
        Calendar cl = Calendar.getInstance();
        cl.setTime(new Date());
        cl.add(Calendar.DAY_OF_WEEK,1);
        Date deliveryTime = cl.getTime();
        result.put("deliveryTime",deliveryTime);
        // 共多少件
        Integer totalPipce = 0;

        for (ShoppingCar car : shoppingCars) {
            totalPipce += car.getQuantity();
            GoodsSku goodsSku = goodsSkuMapper.getGoodsById(car.getGoodsSkuId());
            car.setGoodsSkuImage(ImageUtil.dealToShow(goodsSku.getImage()));
            car.setGoodsName(goodsSku.getFullName());
            //通过商品ID和网点ID查找对应的商品价格表从高到底排列
            List<SupermarketGoods> supermarketGoodsList = supermarketGoodsService.getSupermarketGoodsList(car.getGoodsSkuId(), smallCommunity.getCommunityId());
            Integer minPrice = 0;
            Integer maxPrice = 0;
            if (supermarketGoodsList != null && supermarketGoodsList.size() > 0){
                OpenMemberInfo theLastOpenMemberInfo = openMemberInfoService.getTheLastOpenMemberInfo(car.getUserId());
                minPrice = supermarketGoodsList.get(MemberPriceUtil.memberPriceByLevel(theLastOpenMemberInfo,supermarketGoodsList.size())).getPrice();
                maxPrice = supermarketGoodsList.get(0).getPrice();
            }

            car.setMinPrice(getpriceString(minPrice));
            car.setMaxPrice(getpriceString(maxPrice));
            car.setMaxSupermarketName(supermarketGoodsList.get(0).getSupermarketName());
            car.setMinSupermarketName(supermarketGoodsList.get(supermarketGoodsList.size() - 1).getSupermarketName());
            car.setSpreadRate(NumberUtil.formatDouble((maxPrice - minPrice) * 100 / Double.parseDouble(minPrice + "")) + "%");
            offerPrice += (maxPrice - minPrice) * car.getQuantity();
            if ("SELECTED".equals(car.getSelectStatus())){
//                List<SupermarketGoods> supermarketGoodss = getSupermarketGoodsByGoodsSkuId(car);
                car.setHeJiPrice(getpriceString(car.getQuantity() * minPrice));
                totalPrice += car.getQuantity() * minPrice;
            }
        }
        result.put("shopCars",shoppingCars);

        result.put("offerPrice",getpriceString(offerPrice));
        result.put("totalPrice",getpriceString(totalPrice));
        result.put("totalPipce",totalPipce);
        return result;
    }
}
