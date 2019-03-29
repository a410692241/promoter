package com.linayi.controller.settlement;

import com.linayi.controller.BaseController;
import com.linayi.dto.PromoterSettleDTO;
import com.linayi.entity.order.Orders;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.promoter.PromoterSettleService;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/finace")
public class PromoterSettleController extends BaseController {
    @Autowired
    private PromoterSettleService promoterSettleService;

    @PostMapping("/getAllPromoter.do")
    @ResponseBody
    public ResponseData getAllPromoter(Orders orders) {
        try {
            // 推广商列表[推广商信息，订单信息，当前推广商的收益]
            List<PromoterSettleDTO> promoterSettleDTOList = promoterSettleService.getAllPromoterDto(orders);
            return new ResponseData(promoterSettleDTOList);
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }
    @RequestMapping("/exportData.do")
    public void exportData(Orders orders){

    }

}
