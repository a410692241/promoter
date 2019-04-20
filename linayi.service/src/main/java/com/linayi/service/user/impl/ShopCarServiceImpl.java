package com.linayi.service.user.impl;

import com.linayi.dao.address.ReceiveAddressMapper;
import com.linayi.dao.area.SmallCommunityMapper;
import com.linayi.dao.goods.AttributeMapper;
import com.linayi.dao.goods.CommunityGoodsMapper;
import com.linayi.dao.goods.GoodsSkuMapper;
import com.linayi.dao.user.ShoppingCarMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.goods.CommunityGoods;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.promoter.OpenMemberInfo;
import com.linayi.entity.user.ReceiveAddress;
import com.linayi.entity.user.ShoppingCar;
import com.linayi.entity.user.User;
import com.linayi.enums.MemberLevel;
import com.linayi.service.community.CommunityService;
import com.linayi.service.goods.BrandService;
import com.linayi.service.goods.CommunityGoodsService;
import com.linayi.service.goods.SupermarketGoodsService;
import com.linayi.service.promoter.OpenMemberInfoService;
import com.linayi.service.supermarket.SupermarketService;
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
    @Autowired
    private CommunityGoodsService communityGoodsService;
    @Autowired
    private SupermarketService supermarketService;

    @Override
    public String addShopCar(ShoppingCar shoppingCar) {
        User user = userMapper.selectUserByuserId(shoppingCar.getUserId());
        Integer receiveAddressId = user.getDefaultReceiveAddressId();
        shoppingCar.setReceiveAddressId(receiveAddressId);
        ShoppingCar shopCar = shoppingCarMapper.getShopCar(shoppingCar);
        ReceiveAddress receiveAddress = receiveAddressMapper.getReceiveAddressByReceiveAddressId(receiveAddressId);
        Integer smallComunityId = receiveAddress.getAddressOne();
        SmallCommunity smallCommunity = new SmallCommunity();
        smallCommunity.setSmallCommunityId(smallComunityId);
        smallCommunity = smallCommunityMapper.getSmallCommunity(smallCommunity);
        Integer communityId = smallCommunity.getCommunityId();
        CommunityGoods communityGoods = new CommunityGoods();
        communityGoods.setCommunityId(communityId);
        communityGoods.setGoodsSkuId(shoppingCar.getGoodsSkuId());
        communityGoods = communityGoodsService.getCommunityGoods(communityGoods);
        if(communityGoods == null){
            return "no_price";
        }
        if (shopCar != null){
            shopCar.setQuantity(shopCar.getQuantity() + shoppingCar.getQuantity());
            shoppingCarMapper.updateShopCar(shopCar);
        }else {
            shoppingCarMapper.insert(shoppingCar);
        }
        return "success";
    }

    @Override
    public Map<String, Object> shopCarlistAdd(ShoppingCar shoppingCar, HttpServletRequest request) {
        User user = userMapper.selectUserByuserId(shoppingCar.getUserId());
        Integer receiveAddressId = user.getDefaultReceiveAddressId();
        ReceiveAddress receiveAddress = receiveAddressMapper.getReceiveAddressByReceiveAddressId(receiveAddressId);
        Integer smallComunityId = receiveAddress.getAddressOne();
        SmallCommunity smallCommunity = new SmallCommunity();
        smallCommunity.setSmallCommunityId(smallComunityId);
        smallCommunity = smallCommunityMapper.getSmallCommunity(smallCommunity);
        Integer communityId = smallCommunity.getCommunityId();
        shoppingCar.setReceiveAddressId(receiveAddressId);
        List<ShoppingCar> ShoppingCars = shoppingCarMapper.getAllCarByReceiveAddressId(shoppingCar);
        Map<String, Object> map = new HashMap<>();
        Integer totalPrice = 0;//合计总价格
        MemberLevel currentMemberLevel = openMemberInfoService.getCurrentMemberLevel(shoppingCar.getUserId());
        ArrayList<ShoppingCar> shoppingCars = new ArrayList<>();
        for (ShoppingCar car : ShoppingCars) {
            //查出商品的最高价格最低价格
            CommunityGoods communityGoods = new CommunityGoods();
            communityGoods.setCommunityId(communityId);
            communityGoods.setGoodsSkuId(car.getGoodsSkuId());
            communityGoods = communityGoodsService.getCommunityGoods(communityGoods);
            if(communityGoods == null){
                shoppingCars.add(car);
                continue;
            }
            Integer[] idAndPriceByLevel = MemberPriceUtil.supermarketIdAndPriceByLevel(currentMemberLevel, communityGoods);
            Integer minPrice = idAndPriceByLevel[0];
            car.setMinPrice(getpriceString(minPrice));
            car.setMinSupermarketName(supermarketService.getSupermarketById(idAndPriceByLevel[1]).getName());
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
        ShoppingCars.removeAll(shoppingCars);
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
        ReceiveAddress receiveAddress = receiveAddressMapper.getReceiveAddressByReceiveAddressId(receiveAddressId);
        Integer smallComunityId = receiveAddress.getAddressOne();
        SmallCommunity smallCommunity = new SmallCommunity();
        smallCommunity.setSmallCommunityId(smallComunityId);
        smallCommunity = smallCommunityMapper.getSmallCommunity(smallCommunity);
        Integer communityId = smallCommunity.getCommunityId();
        shoppingCar.setReceiveAddressId(receiveAddressId);
        shoppingCar.setSelectStatus("SELECTED");
        //所有购物车
        List<ShoppingCar> shoppingCars = shoppingCarMapper.getAllCarByReceiveAddressId(shoppingCar);

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
        MemberLevel currentMemberLevel = openMemberInfoService.getCurrentMemberLevel(shoppingCar.getUserId());

        for (ShoppingCar car : shoppingCars) {
            totalPipce += car.getQuantity();
            GoodsSku goodsSku = goodsSkuMapper.getGoodsById(car.getGoodsSkuId());
            car.setGoodsSkuImage(ImageUtil.dealToShow(goodsSku.getImage()));
            car.setGoodsName(goodsSku.getFullName());
            CommunityGoods communityGoods = new CommunityGoods();
            communityGoods.setCommunityId(communityId);
            communityGoods.setGoodsSkuId(car.getGoodsSkuId());
            communityGoods = communityGoodsService.getCommunityGoods(communityGoods);
            Integer[] idAndPriceByLevel = MemberPriceUtil.supermarketIdAndPriceByLevel(currentMemberLevel, communityGoods);
            Integer minPrice = idAndPriceByLevel[0];
            Integer maxPrice = idAndPriceByLevel[2];

            car.setMinPrice(getpriceString(minPrice));
            car.setMaxPrice(getpriceString(maxPrice));
            car.setMaxSupermarketName(supermarketService.getSupermarketById(idAndPriceByLevel[3]).getName());
            car.setMinSupermarketName(supermarketService.getSupermarketById(idAndPriceByLevel[1]).getName());
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
