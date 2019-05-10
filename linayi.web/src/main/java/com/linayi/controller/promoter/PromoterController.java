package com.linayi.controller.promoter;

import com.linayi.entity.promoter.OpenOrderManInfo;
import com.linayi.service.promoter.PromoterOrderManService;
import com.linayi.util.ResponseData;
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

    @RequestMapping("/add.do")
    @ResponseBody
    public ResponseData add(OpenOrderManInfo openOrderManInfo){
        promoterOrderManService.openOrderManByPromoter(openOrderManInfo);
        return new ResponseData("success");
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
}
