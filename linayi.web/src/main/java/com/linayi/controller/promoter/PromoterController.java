package com.linayi.controller.promoter;

import com.linayi.entity.promoter.OpenOrderManInfo;
import com.linayi.service.promoter.PromoterOrderManService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PromoterController {
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
