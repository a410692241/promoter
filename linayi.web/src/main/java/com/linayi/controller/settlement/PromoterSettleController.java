package com.linayi.controller.settlement;

import com.linayi.controller.BaseController;
import com.linayi.dto.PromoterSettleDTO;
import com.linayi.entity.order.Orders;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.promoter.PromoterSettleService;
import com.linayi.util.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/finace")
public class PromoterSettleController extends BaseController {
    @Autowired
    private PromoterSettleService promoterSettleService;

    /**
     * 推广商结算列表
     *
     * @param orders
     * @return
     */
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


    /**
     * excel 导出
     */
    @RequestMapping(value = "/exportShareRecord.do", method = RequestMethod.GET)
    public void exportExcel(Orders orders) {
        List<PromoterSettleDTO> promoterSettleDTOList = promoterSettleService.getAllPromoterDto(orders);
        try {
            getRequest().setCharacterEncoding("UTF-8");//设置request的编码方式，防止中文乱码
            String fileName = null;
            fileName = "推广商结算";//设置导出的文件名称
            String contentType = "application/vnd.ms-excel";//定义导出文件的格式的字符串
            String recommendedName = new String(fileName.getBytes(), "iso_8859_1");//设置文件名称的编码格式
            getResponse().setContentType(contentType);//设置导出文件格式
            getResponse().setHeader("Content-Disposition", "attachment; filename=" + recommendedName + ".xls");
            getResponse().setCharacterEncoding("utf-8");
            getResponse().resetBuffer();
            //利用输出输入流导出文件
            ServletOutputStream sos = getResponse().getOutputStream();
            String tempsb = "";
            if (null != promoterSettleDTOList) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                StringBuilder tableHtml = new StringBuilder("<table><tr><td>推广商名称</td><td>推广商等级</td><td>订单数</td><td>订单总额</td><td>推广商收益</td></tr>");
                for (PromoterSettleDTO settleDTO : promoterSettleDTOList) {
                    // 推广商名称
                    if (settleDTO.getName() != null) {
                        tableHtml = tableHtml.append("<tr><td>" + settleDTO.getName() + "</td>");
                    } else {
                        tableHtml = tableHtml.append("<td>" + " " + "</td>");
                    }
                    // 推广商等级
                    if (settleDTO.getPromoterLevel() != null) {
                        tableHtml = tableHtml.append("<tr><td>" + settleDTO.getName() + "</td>");
                        if ("1".equals(settleDTO.getPromoterLevel())) {
                            tableHtml = tableHtml.append("<td>" + "一级" + "</td>");
                        } else if ("2".equals(settleDTO.getPromoterLevel())) {
                            tableHtml = tableHtml.append("<td>" + "二级" + "</td>");
                        } else if ("3".equals(settleDTO.getPromoterLevel())) {
                            tableHtml = tableHtml.append("<td>" + "三级" + "</td>");
                        }
                    } else {
                        tableHtml = tableHtml.append("<td>" + " " + "</td>");
                    }
                    // 订单数
                    if (settleDTO.getSumOrderNo() != null) {
                        tableHtml = tableHtml.append("<tr><td>" + settleDTO.getSumOrderNo() + "</td>");
                    } else {
                        tableHtml = tableHtml.append("<td>" + " " + "</td>");
                    }
                    // 订单合计金额
                    if (settleDTO.getSumOrderAmount() != null) {
                        tableHtml = tableHtml.append("<td>" + settleDTO.getSumOrderAmount() + "</td>");
                    } else {
                        tableHtml = tableHtml.append("<td>" + " " + "</td>");
                    }
                    // 推广商收益
                    if (settleDTO.getProfit() != null) {
                        tableHtml = tableHtml.append("<td>" + settleDTO.getProfit() + "</td></tr>");
                    } else {
                        tableHtml = tableHtml.append("<td>" + " " + "</td></tr>");
                    }
                }
                if (!StringUtils.isEmpty(tableHtml)) {
                    tempsb = "<meta http-equiv=\"content-type\" content=\"application/ms-excel; charset=UTF-8\"/>" + tableHtml.toString().replaceAll("null", "") + "</table>";
                }
            }
            sos.write(tempsb.getBytes("utf-8"));
            sos.flush();
            sos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
