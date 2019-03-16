package com.linayi.controller.correct;

import com.linayi.controller.BaseController;
import com.linayi.entity.account.AdminAccount;
import com.linayi.entity.correct.Correct;
import com.linayi.entity.correct.SupermarketGoodsVersion;
import com.linayi.enums.CorrectStatus;
import com.linayi.enums.OperatorType;
import com.linayi.enums.PriceType;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.correct.CorrectService;
import com.linayi.service.correct.SupermarketGoodsVersionService;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("correct/correct")
public class CorrectController extends BaseController {

    @Autowired
    private CorrectService correctService;
    @Resource
    private SupermarketGoodsVersionService supermarketGoodsVersionService;

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(Correct correct) {
        return correctService.page(correct);
    }

    @RequestMapping("/get.do")
    public Object show(Long correctId) {
        ModelAndView mv = new ModelAndView("/jsp/goods/CorrectShow");
        Correct correct = correctService.selectByCorrectId(correctId);

        mv.addObject("correct", correct);
        return mv;
    }

    @RequestMapping("/getInfo.do")
    @ResponseBody
    public Object edit(Long correctId) {
        Correct correct = correctService.selectByCorrectId(correctId);
        ModelAndView mv = new ModelAndView("/jsp/goods/CorrectEdit");
        mv.addObject("correct", correct);
        return mv;
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Object save(Correct correct) {
        try {
            correctService.updateCorrect(correct);
            return new ResponseData("success").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.UPDATE_ERROR).toString();
        }
    }

    @RequestMapping("/audit.do")
    @ResponseBody
    public Object audit(Correct correct, HttpSession session) {
        try {
            AdminAccount adminaccount = (AdminAccount) session.getAttribute("loginAccount");
            correct.setUserId(adminaccount.getAccountId());
            correctService.audit(correct);
            return new ResponseData("success").toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }

    }

    /*批量审核*/
    @RequestMapping("/batchAudit.do")
    @Transactional
    @ResponseBody
    public Object batchAudit(@RequestParam("correctIdList[]") List<Integer> correctIdList) {
        try {
            for (Integer correctId : correctIdList) {
                Correct correct = new Correct();
                correct.setCorrectId(Long.valueOf(correctId));
                correct.setStatus(CorrectStatus.AUDIT_SUCCESS.toString());
                correctService.audit(correct);
            }
            return new ResponseData("success").toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    @RequestMapping("/recall.do")
    @ResponseBody
    public Object recall(Correct correct, HttpSession session) {
        try {
            AdminAccount adminaccount = (AdminAccount) session.getAttribute("loginAccount");
            correct.setUserId(adminaccount.getAccountId());
            String userType = OperatorType.ADMIN.toString();
            correctService.recall(correct, userType);
            return new ResponseData("撤回成功!");
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    @RequestMapping("/share.do")
    @ResponseBody
    public Object share(Correct correct, MultipartFile file, HttpServletRequest httpRequest) {
        //获取当前时间和一年后的时间
        Date nowTime = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowTime);//设置起时间
        cal.add(Calendar.YEAR, 1);//增加一年
        Date afterOneYearTime = cal.getTime();
        try {
            if (
                    (correct.getPrice() == null || correct.getPrice() <= 0) ||
                            (correct.getGoodsSkuId() == null || correct.getGoodsSkuId() <= 0) ||
                            (correct.getSupermarketId() == null || correct.getSupermarketId() <= 0)
                    ) {
                throw new BusinessException(ErrorType.INCOMPLETE_INFO);
            }

            // 线程安全并发处理
            initVersion(correct);

            //后台分享部分数据处理,设置默认开始时间为当前时间，结束时间为一年后
            if (correct.getStartTime() == null) {
                correct.setStartTime(nowTime);
            }
            if (correct.getEndTime() == null) {
                correct.setEndTime(afterOneYearTime);
            }
            if (correct.getPriceType() == null) {
                correct.setPriceType(PriceType.NORMAL.toString());
            }
            String userType = OperatorType.ADMIN.toString();
            correct.setPrice(correct.getPrice() * 100);
            AdminAccount adminAccount = (AdminAccount) httpRequest.getSession().getAttribute("loginAccount");
            Integer creatorId = adminAccount.getAccountId();
            correct.setUserId(creatorId);

            correctService.share(correct, file, userType);
            return new ResponseData("您的分享申请提交成功!").toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    public void initVersion(Correct correct) {
        try {
            SupermarketGoodsVersion version = new SupermarketGoodsVersion();
            version.setSupermarketId(correct.getSupermarketId());
            version.setGoodsSkuId(Integer.parseInt(correct.getGoodsSkuId() + ""));
            supermarketGoodsVersionService.insert(version);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/exportShareRecord.do",method = RequestMethod.GET)
    public void exportExcel() {
        Correct correct = new Correct();
        correct.setPageSize(20000);
        correct.setCurrentPage(1);
        PageResult<Correct> correctPageResult = correctService.page(correct);
        try {
            getRequest().setCharacterEncoding("UTF-8");//设置request的编码方式，防止中文乱码
            String fileName = null;
            fileName = "分享纠错";//设置导出的文件名称
            String contentType = "application/vnd.ms-excel";//定义导出文件的格式的字符串
            String recommendedName = new String(fileName.getBytes(), "iso_8859_1");//设置文件名称的编码格式
            getResponse().setContentType(contentType);//设置导出文件格式
            getResponse().setHeader("Content-Disposition", "attachment; filename=" + recommendedName + ".xls");
            getResponse().setCharacterEncoding("utf-8");
            getResponse().resetBuffer();
            //利用输出输入流导出文件
            ServletOutputStream sos = getResponse().getOutputStream();
            String tempsb = "";
            if (null != correctPageResult) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                StringBuilder tableHtml = new StringBuilder("<table><tr><td>真实姓名</td><td>手机号</td><td>状态</td><td>商品</td><td>超市</td>" +
                        "<td>类型</td><td>价格（元）</td><td>价格类型</td><td>有效开始时间</td><td>有效结束时间</td><td>创建时间</td></tr>");
                for (Correct cre : correctPageResult.getRows()) {
                    //用户相关信息
                    if (cre.getUser() != null) {
                        tableHtml = tableHtml.append("<tr><td>" + cre.getUser().getRealName() + "</td>");
                        tableHtml = tableHtml.append("<td>" + cre.getUser().getMobile() + "</td>");
                    } else {
                        tableHtml = tableHtml.append("<tr><td>" + " " + "</td>");
                        tableHtml = tableHtml.append("<td>" + " " + "</td>");
                    }

                    // 商品信息
                    if (cre.getGoodsSku() != null) {
                        // 商品状态
                        if ("WAIT_AUDIT".equals(cre.getGoodsSku().getStatus())) {
                            tableHtml = tableHtml.append("<td>" + "待审核" + "</td>");
                        } else if ("AUDIT_SUCCESS".equals(cre.getGoodsSku().getStatus())) {
                            tableHtml = tableHtml.append("<td>" + "审核通过" + "</td>");
                        } else if ("RECALL".equals(cre.getGoodsSku().getStatus())) {
                            tableHtml = tableHtml.append("<td>" + "撤回" + "</td>");
                        } else if ("AUDIT_FAIL".equals(cre.getGoodsSku().getStatus())) {
                            tableHtml = tableHtml.append("<td>" + "审核不通过" + "</td>");
                        } else if ("AFFECTED".equals(cre.getGoodsSku().getStatus())) {
                            tableHtml = tableHtml.append("<td>" + "已生效" + "</td>");
                        } else if ("EXPIRED".equals(cre.getGoodsSku().getStatus())) {
                            tableHtml = tableHtml.append("<td>" + "已过期" + "</td>");
                        }
                        // 商品名
                        tableHtml = tableHtml.append("<td>" + cre.getGoodsSku().getName() + "</td>");
                    } else {
                        tableHtml = tableHtml.append("<td>" + " " + "</td>");
                        tableHtml = tableHtml.append("<td>" + " " + "</td>");
                    }

                    //商戶信息
//                    if (cre.getSupermarket() != null) {
                    // 超市
                    tableHtml = tableHtml.append("<td>" + cre.getSupermarket().getName() + "</td>");
//                    }

                    // 纠错类型
                    if ("SHARE".equals(cre.getType())) {
                        tableHtml = tableHtml.append("<td>" + "纠错" + "</td>");
                    } else if ("CORRECT".equals(cre.getType())) {
                        tableHtml = tableHtml.append("<td>" + "分享" + "</td>");
                    } else {
                        tableHtml = tableHtml.append("<td>" + " " + "</td>");
                    }

                    // 价格
                    tableHtml = tableHtml.append("<td>" + cre.getPrice() / 100 + "</td>");
                    // 价格类型
                    if ("NORMAL".equals(cre.getPriceType())) {
                        tableHtml = tableHtml.append("<td>" + "正常价" + "</td>");
                    } else if ("PROMOTION".equals(cre.getPriceType())) {
                        tableHtml = tableHtml.append("<td>" + "促销价" + "</td>");
                    } else if ("DEAL".equals(cre.getPriceType())) {
                        tableHtml = tableHtml.append("<td>" + "处理价" + "</td>");
                    } else if ("MEMBER".equals(cre.getPriceType())) {
                        tableHtml = tableHtml.append("<td>" + "会员价" + "</td>");
                    } else {
                        tableHtml = tableHtml.append("<td>" + " " + "</td>");
                    }

                    // 有效开始时间
                    tableHtml = tableHtml.append("<td>" + simpleDateFormat.format(cre.getStartTime()) + "</td>");
                    // 有效结束时间
                    tableHtml = tableHtml.append("<td>" + simpleDateFormat.format(cre.getEndTime()) + "</td>");
                    // 创建时间
                    if (cre.getSupermarket() != null) {
                        tableHtml = tableHtml.append("<td>" + simpleDateFormat.format(cre.getSupermarket().getCreateTime()) + "</td></tr>");
                    } else {
                        tableHtml = tableHtml.append("<td>" + " " + "</td></tr>");
                    }
                }
                if (!StringUtils.isEmpty(tableHtml)) {
                    tempsb = "<meta http-equiv=\"content-type\" content=\"application/ms-excel; charset=UTF-8\"/>" + tableHtml + "</table>";
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
