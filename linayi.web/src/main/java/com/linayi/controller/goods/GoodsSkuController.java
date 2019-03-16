package com.linayi.controller.goods;

import com.linayi.entity.account.AdminAccount;
import com.linayi.entity.correct.Correct;
import com.linayi.entity.correct.SupermarketGoodsVersion;
import com.linayi.entity.goods.Attribute;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.enums.OperatorType;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.correct.CorrectService;
import com.linayi.service.correct.SupermarketGoodsVersionService;
import com.linayi.service.goods.GoodsSkuService;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/goods/goods")
public class GoodsSkuController {

    @Resource
    private GoodsSkuService goodsService;
    @Autowired
    private CorrectService correctService;
    @Autowired
    private SupermarketGoodsVersionService supermarketGoodsVersionService;

    /**
     * 添加商品页面的入口
     *
     * @param model
     * @return
     */
    @RequestMapping("/goAddGoods.do")
    public String goAddGoods(ModelMap model) {
        goodsService.getBrandAndVal(model);
        return "jsp/goods/GoodsAdd";
    }


    /**
     * 添加商品
     *
     * @param modelMap
     * @param file
     * @param category
     * @param brand
     * @param httpRequest
     * @return
     */
    @Transactional
    @RequestMapping("/addGoods.do")
    @ResponseBody
    public String addGoods(ModelMap modelMap, MultipartFile file, String category, String brand, HttpServletRequest httpRequest, GoodsSku goods, String[] attribute) {
        String result = "success";
        try {
            goodsService.insertGoods(modelMap, file, category, brand, goods, attribute, httpRequest);

        } catch (Exception e) {
            e.printStackTrace();
            result = "defeat";
        }
        return result;
    }


    @RequestMapping("/addGoodsForAdmin.do")
    @ResponseBody
    @Transactional
    public ResponseData addGoodsForAdmin(
            ModelMap modelMap,
            MultipartFile file,
            String category,
            String brand,
            HttpServletRequest httpRequest,
            GoodsSku goods,
            String[] attribute,
            Integer supermarketId,
            String priceType,
            Double price,
            String startTime,
            String endTime
    ) {
        GoodsSku goodsSku;
        try {
            // 插入商品sku
            goodsSku = goodsService.insertGoods(modelMap, file, category, brand, goods, attribute, httpRequest);
            if (goodsSku == null){
                ResponseData responseData = new ResponseData(ErrorType.SYSTEM_ERROR);
                responseData.setRespCode("T");
                return responseData;
            }
            // 分享价格
            if (supermarketId != null && price != null && priceType != null) {
                Long goodsSkuId = goodsSku.getGoodsSkuId();
                Correct correct = new Correct();
                correct.setGoodsSkuId(goodsSkuId);
                correct.setSupermarketId(supermarketId);
                correct.setPriceType(priceType);
                correct.setPrice((int) (price * 100));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate, endDate;
                if (startTime == null || startTime.equals("")){
                    startDate = new Date();
                }else {
                    startDate = sdf.parse(startTime);
                }
                if (endTime == null || endTime.equals("")){
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);//设置起时间
                    cal.add(Calendar.YEAR, 1);//增加一年
                    endDate = cal.getTime();
                }else {
                    endDate = sdf.parse(endTime);
                }
                correct.setStartTime(startDate);
                correct.setEndTime(endDate);
                // 线程安全并发处理
                AdminAccount adminAccount=(AdminAccount)httpRequest.getSession().getAttribute("loginAccount");
                Integer creatorId=adminAccount.getAccountId();
                correct.setUserId(creatorId);
                initVersion(correct);
                correctService.share(correct, null, OperatorType.ADMIN.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
        return new ResponseData(goodsSku);
    }


    public void initVersion(Correct correct) {
        try {
            SupermarketGoodsVersion version = new SupermarketGoodsVersion();
            version.setSupermarketId(correct.getSupermarketId());
            version.setGoodsSkuId(Integer.parseInt(correct.getGoodsSkuId() + ""));
            supermarketGoodsVersionService.insert(version);
        } catch (Exception e) {
        }
    }

    //显示筛选规格
    @RequestMapping("/specificationsFilter.do")
    @ResponseBody
    public List<Object> specificationsFilter(String categoryName, String brandName) {
        return goodsService.specificationsFilter(categoryName, brandName);
    }

    /**
     * 展示所有属性值和者新增属性值
     *
     * @param modelMap
     * @param attributeId
     * @param value
     * @return
     */
    @Transactional
    @RequestMapping("/showSpecifications.do")
    public String showSpecifications(ModelMap modelMap, Integer attributeId, String value) {
        return goodsService.showSpecifications(modelMap, attributeId, value);
    }

    /**
     * 分类、品牌、属性值绑定
     *
     * @param categoryName
     * @param brandName
     * @param
     */
    @Transactional
    @RequestMapping(value = "/specificationsAdd.do", method = RequestMethod.POST)
    @ResponseBody
    public String specificationsAdd(String categoryName, String brandName, String attrStr) {
        return goodsService.specificationsAdd(categoryName, brandName, attrStr);
    }

    /**
     * 分页查询
     *
     * @param goods
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public Object getGoodsList(GoodsSku goods, String userName) {
        return goodsService.getGoodsLists(goods, userName);
    }


    /**
     * 根据id查询商品
     *
     * @param goodsSkuId
     * @return
     */
    @RequestMapping("/show.do")
    public String view(Long goodsSkuId, Model model) {
        goodsService.view(goodsSkuId, model);
        return "jsp/goods/GoodsShow";
    }

    /**
     * 删除商品
     *
     * @param goodsSkuId
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteGoodsById.do")
    public Object deleteGoodsById(Integer goodsSkuId) {
        String s = goodsService.deleteGoodsById(goodsSkuId);
        return new ResponseData(s);
    }

    @RequestMapping("/getAttributeList.do")
    @ResponseBody
    public List<Attribute> getAttributeList() {
        List<Attribute> attributeList = goodsService.getAttributeList();
        return attributeList;
    }

    @RequestMapping("/Skulist.do")
    @ResponseBody
    public Object Skulist(SupermarketGoods smg) {
        List<SupermarketGoods> selectSupermarketGoodsList = goodsService.selectSupermarketGoodsList(smg);
        PageResult<SupermarketGoods> pageResult = new PageResult<SupermarketGoods>(selectSupermarketGoodsList, smg.getTotal());
        return pageResult;
    }

    @RequestMapping("/shareEdit.do")
    public ModelAndView edit(String goodsSkuId) {
        Correct correct = new Correct();
        correct.setGoodsSkuId(Long.parseLong(goodsSkuId));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsp/goods/goodShare");
        modelAndView.addObject("correct", correct);
        return modelAndView;
    }

    @RequestMapping("/edit.do")
    @ResponseBody
    public Object edit(Long goodsSkuId) {
        GoodsSku goodsSku = goodsService.getGoodsSku(goodsSkuId);
        return new ResponseData(goodsSku);
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Object save(@RequestParam(value = "goodsImage", required = false) CommonsMultipartFile goodsImage, GoodsSku goodsSku) {
        try {
            goodsService.edit(goodsImage, goodsSku);
            return new ResponseData("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("defeat");
    }


    @RequestMapping("/listSupermarket.do")
    @ResponseBody
    public ResponseData listSupermarket() {
        try {
            return new ResponseData(goodsService.listSupermarket(new Supermarket()));
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }
}
