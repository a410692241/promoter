package com.linayi.controller.goods;


import com.linayi.controller.BaseController;
import com.linayi.entity.account.Account;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.goods.GoodsSkuService;
import com.linayi.service.goods.SupermarketGoodsService;
import com.linayi.service.supermarket.SupermarketService;
import com.linayi.util.PageResult;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;
import com.linayi.vo.promoter.PromoterVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/goods/goods")
public class GoodsSkuController extends BaseController {
    @Autowired
    private GoodsSkuService goodsSkuService;
    @Autowired
    private SupermarketGoodsService supermarketGoodsService;
    @Autowired
    private SupermarketService supermarketService;

    @RequestMapping("/upload.do")
    @ResponseBody
    public Object upload(@RequestBody Map<String, Object> param, @RequestParam("image") MultipartFile file) {
        ParamValidUtil<Account> pa = new ParamValidUtil<>(param);
        Account account = pa.transObj(Account.class);
        System.out.println();
        return null;

    }

    //根据用户地址获取绑定的超市对象集合
    @RequestMapping("/supermarket.do")
    @ResponseBody
    public Object addShopCar() {
        try {
            List<Supermarket> supermarketList = supermarketService.getSupermarketByAddress(getUserId());
            return new ResponseData(supermarketList);
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }


    //根据商品id和用户uid查询其他超市价格
    @RequestMapping("/supermarketPriceList.do")
    @ResponseBody
    public Object showOtherSupermarketPrice(@RequestBody Map<String, Object> param) {
        try {
            Map<String, Object> map = supermarketGoodsService.getPriceSupermarketByGoodsSkuId(super.getUserId(), (Integer) param.get("goodsSkuId"));
            return new ResponseData(map);
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }


    //根据品牌id分类id和商品名获取商品列表(已测试)
    @RequestMapping("/searchgoodsList.do")
    public ResponseData showGoodsSkuList(@RequestBody GoodsSku goodsSku) {
        try {
            goodsSku.setUserId(getUserId());
            if (goodsSku.getPageSize() == null) {
                goodsSku.setPageSize(8);
            }

            List<GoodsSku> goodsList =
                    goodsSkuService.getGoodsListBybrandIdcategoryIdGoodsName(goodsSku);
            Integer totalPage = (int) Math.ceil(Double.valueOf(goodsSku.getTotal()) / Double.valueOf(goodsSku.getPageSize()));
            if (totalPage <= 0) {
                totalPage++;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("data", goodsList);
            map.put("totalPage", totalPage);
            map.put("currentPage", goodsSku.getCurrentPage());
            return new ResponseData(map);
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }


    //原差价排行修改为特价商品排行
    @RequestMapping("/differenceRanking.do")
    public Object showDifferenceRanking(@RequestBody GoodsSku goodsSku) {
        try {
            goodsSku.setUserId(getUserId());
            if (goodsSku.getPageSize() == null) {
                goodsSku.setPageSize(8);
            }
            List<GoodsSku> differenceRankingList = goodsSkuService.getSpecialPrice(goodsSku);
            Integer totalPage = (int) Math.ceil(Double.valueOf(goodsSku.getTotal()) / Double.valueOf(goodsSku.getPageSize()));
            if (totalPage <= 0) {
                totalPage++;
            }

            Map<String, Object> map = new HashMap<>();
            map.put("data", differenceRankingList);
            map.put("totalPage", totalPage);
            map.put("currentPage", goodsSku.getCurrentPage());
            return new ResponseData(map);
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }

    /**
     * 根据商品名查询所有商品
     *
     * @param param
     * @return
     */
    @RequestMapping("/getGoodsByName.do")
    @ResponseBody
    public Object getGoodsByName(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        ParamValidUtil<GoodsSku> pvu = new ParamValidUtil<>(param);
        Integer currentPage = (Integer) param.get("pages");
        String name = (String) param.get("name");
        try {
            GoodsSku goodsSku = new GoodsSku();
            goodsSku.setCurrentPage(currentPage);
            goodsSku.setPageSize(8);
            goodsSku.setName(name);
            //pvu.Exist("accessToken","name");
            List<GoodsSku> goodsSkusList = goodsSkuService.getGoodsByName(goodsSku, request);
            return new ResponseData(goodsSkusList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("F", ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

    /**
     * 根据商品Id查询商品其他超市价格
     *
     * @param param
     * @return
     */
    @RequestMapping("/getGoodsPrices.do")
    public Object getGoodsPrices(@RequestBody Map<String, Object> param) {
        ParamValidUtil<GoodsSku> pvu = new ParamValidUtil<>(param);
        try {
            //pvu.Exist("accessToken","goodsSkuId");
            GoodsSku goodsSku = goodsSkuService.getGoodsPrices(Integer.parseInt("" + param.get("goodsSkuId")), getUserId());
            return new ResponseData(goodsSku);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return new ResponseData("F", ErrorType.SYSTEM_ERROR.getErrorMsg());
    }


    //根据商品名模糊搜索商品不包含价格分享价格用(已测试)
    @RequestMapping("/vagueGoodsSku.do")
    public Object showVagueGoodsSku(@RequestBody GoodsSku goodsSku) {
        try {
            if (goodsSku.getPageSize() == null) {
                goodsSku.setPageSize(8);
            }
//    		goodsSku.setName(goodsName);
            goodsSku.setStatus("NORMAL");
            List<GoodsSku> vagueGoodsSkuList = goodsSkuService.getGoodsSkuByVagueGoodsName(goodsSku);
            Integer totalPage = (int) Math.ceil(Double.valueOf(goodsSku.getTotal()) / Double.valueOf(goodsSku.getPageSize()));
            if (totalPage <= 0) {
                totalPage++;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("data", vagueGoodsSkuList);
            map.put("totalPage", totalPage);
            map.put("currentPage", goodsSku.getCurrentPage());
            return new ResponseData(map);
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);

        }
    }


    //根据用户id和商品id获取超市和价格信息(已测试)
    @RequestMapping("/priceSupermarket.do")
    @ResponseBody
    public Object showPriceSupermarket(@RequestBody Map<String, Object> param) {
        try {
            List<Supermarket> priceSupermarketList = supermarketGoodsService.getPriceSupermarketBycommunityIdAndgoodsSkuId(getUserId(), (Integer) param.get("goodsSkuId"));
            return new ResponseData(priceSupermarketList);
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }

    }


    //自定义搜索
    @RequestMapping("/customSearch.do")
    public Object customSearch(@RequestBody GoodsSku goodsSku) {

        try {

            if (goodsSku.getBrandName() == null && goodsSku.getFullName() == null && goodsSku.getValueName() == null) {
                return null;
            }
            goodsSku.setUserId(getUserId());
            if (goodsSku.getPageSize() == null) {
                goodsSku.setPageSize(8);
            }
            List<GoodsSku> goodsSkuList = goodsSkuService.customSearch(goodsSku);
            Integer totalPage = (int) Math.ceil(Double.valueOf(goodsSku.getTotal()) / Double.valueOf(goodsSku.getPageSize()));
            if (totalPage <= 0) {
                totalPage++;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("data", goodsSkuList);
            map.put("totalPage", totalPage);
            map.put("currentPage", goodsSku.getCurrentPage());
            return new ResponseData(map);

        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);

        }
    }

    /**
     * 通过ES关键字索引检索商品信息
     *
     * @throws Exception
     */
    @ApiOperation(value = "商品查找")
    @RequestMapping(value = "searchByKey.do", method = RequestMethod.POST)
    @ResponseBody
    public Object searchByKey(@RequestBody PromoterVo.EsConfig esConfig) {
        try {
            esConfig.setUserId(getUserId());
            String barcode = esConfig.getBarcode();
            if (barcode != null) {
                GoodsSku goodsSku = goodsSkuService.searchByBarcode(esConfig);
                return new ResponseData(goodsSku);
            }
            List<GoodsSku> goodsSkus = goodsSkuService.searchByKey(esConfig);
            PageResult<GoodsSku> goodsSkuPageResult = new PageResult<>(goodsSkus, esConfig);
            return new ResponseData(goodsSkuPageResult);
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(e);
        }

    }

    //首页推荐商品
    @RequestMapping("/recommend.do")
    public Object getRecommendGoodsSku(@RequestBody GoodsSku goodsSku) {


        try {
            goodsSku.setUserId(getUserId());
            if (goodsSku.getPageSize() == null) {
                goodsSku.setPageSize(4);
            }
            List<GoodsSku> goodsSkuList = goodsSkuService.getRecommendGoodsSku(goodsSku);
            Integer totalPage = (int) Math.ceil(Double.valueOf(goodsSku.getTotal()) / Double.valueOf(goodsSku.getPageSize()));
            if (totalPage <= 0) {
                totalPage++;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("data", goodsSkuList);
            map.put("totalPage", totalPage);
            map.put("currentPage", goodsSku.getCurrentPage());
            return new ResponseData(map);
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR);

        }


    }

}
