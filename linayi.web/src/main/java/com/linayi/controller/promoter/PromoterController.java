package com.linayi.controller.promoter;

import com.linayi.dao.promoter.OpenOrderManInfoMapper;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.promoter.OpenOrderManInfo;
import com.linayi.entity.promoter.Promoter;
import com.linayi.exception.ErrorType;
import com.linayi.service.promoter.PromoterOrderManService;
import com.linayi.service.promoter.PromoterService;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/promoter/promoter")
public class PromoterController {
    @Autowired
    private PromoterOrderManService promoterOrderManService;
    
    @Autowired
    private PromoterService promoterService;

    @RequestMapping("/add.do")
    @ResponseBody
    public ResponseData add(OpenOrderManInfo openOrderManInfo){
        try {
            promoterOrderManService.openOrderManByPromoter(openOrderManInfo);
            return new ResponseData("success");
        }catch (Exception e){
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }
    
    @RequestMapping("/getPromoterListByType.do")
    @ResponseBody
    public ResponseData getPromoterListByType(Promoter promoter){
        try {
        	List<Promoter> list = promoterService.getPromoterListByType(promoter);
            return new ResponseData(list);
        }catch (Exception e){
        	e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }
    
    public static void main(String[] args) {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
                    "classpath*:config/spring-context.xml"});
            PromoterOrderManService job = (PromoterOrderManService) context.getBean("promoterOrderManServiceImpl");
            OpenOrderManInfo openOrderManInfo = new OpenOrderManInfo();
            openOrderManInfo.setPromoterId(Integer.valueOf(args[0]));
            openOrderManInfo.setOrderManId(Integer.valueOf(args[1]));
            openOrderManInfo.setIdentity(args[2]);
            openOrderManInfo.setOrderManLevel(args[3]);
            job.openOrderManByPromoter(openOrderManInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    //后台下单员列表
    @RequestMapping("/ordermanList.do")
    @ResponseBody
    public Object ordermanList(OpenOrderManInfo openOrderManInfo) {
//        try {
            List<OpenOrderManInfo> orderManList = promoterOrderManService.getOrderManListForWeb(openOrderManInfo);
            PageResult<OpenOrderManInfo> pageResult = new PageResult<>(orderManList,openOrderManInfo.getTotal());
            return pageResult;
//        }catch (Exception e){
//            return new ResponseData(ErrorType.SYSTEM_ERROR);
//        }
    }


}
