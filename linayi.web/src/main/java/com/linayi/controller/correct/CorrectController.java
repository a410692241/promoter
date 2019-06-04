package com.linayi.controller.correct;

import com.linayi.controller.BaseController;
import com.linayi.entity.account.AdminAccount;
import com.linayi.entity.correct.Correct;
import com.linayi.entity.correct.SupermarketGoodsVersion;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.enums.CorrectStatus;
import com.linayi.enums.OperatorType;
import com.linayi.enums.PriceType;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.correct.CorrectService;
import com.linayi.service.correct.SupermarketGoodsVersionService;
import com.linayi.service.goods.SupermarketGoodsService;
import com.linayi.util.DateUtil;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
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
    @Autowired
    private SupermarketGoodsService supermarketGoodsService;


    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(Correct correct) {
        List<Correct> correctResp = correctService.page(correct);

        PageResult<Correct> page = new PageResult<>(correctResp, correct.getTotal());

        return page;
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
            correct.setAuditType(OperatorType.ADMIN.toString());
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

    @RequestMapping("/correct.do")
    @ResponseBody
    public Object correct(Correct correct, MultipartFile file, HttpServletRequest httpRequest) {
        try {
            if ((correct.getPrice() == null || correct.getPrice() <= 0) ||
                    (correct.getPriceType() == null || "".equals(correct.getPriceType())) ||
                    (correct.getStartTime() == null) ||
                    (correct.getEndTime() == null) ||
                    (correct.getParentId() == null || correct.getParentId() <= 0) ||
                    (correct.getGoodsSkuId() == null || correct.getGoodsSkuId() <= 0) ||
                    (correct.getSupermarketId() == null || correct.getSupermarketId() <= 0)
            ) {
                throw new BusinessException(ErrorType.INCOMPLETE_INFO);
            }
            //获取当前时间和一年后的时间
            Date nowTime = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(nowTime);//设置起时间
            cal.add(Calendar.YEAR, 1);//增加一年
            Date afterOneYearTime = cal.getTime();

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

            correctService.correct(correct, file, userType);

            return new ResponseData("您的纠错申请提交成功!").toString();
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

    /**
     * excel 导出
     */
    @RequestMapping(value = "/exportShareRecord.do", method = RequestMethod.GET)
    public void exportExcel(Correct correct , HttpServletRequest request, HttpServletResponse response) {
        List<Correct> list = correctService.page(correct);

        try {
            // 只是让浏览器知道要保存为什么文件而已，真正的文件还是在流里面的数据，你设定一个下载类型并不会去改变流里的内容。
            //而实际上只要你的内容正确，文件后缀名之类可以随便改，就算你指定是下载excel文件，下载时我也可以把他改成pdf等。
            response.setContentType("application/vnd.ms-excel");
            // 传递中文参数编码
            String codedFileName = java.net.URLEncoder.encode("分享纠错信息","UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
            // 定义一个工作薄
            Workbook workbook = new HSSFWorkbook();
            // 创建一个sheet页
            Sheet sheet = workbook.createSheet("分享纠错信息");
            // 创建一行
            Row row = sheet.createRow(0);
            // 在本行赋值 以0开始

            row.createCell(0).setCellValue("分享纠错编号");
            row.createCell(1).setCellValue("分享人");
            row.createCell(2).setCellValue("手机号");
            row.createCell(3).setCellValue("状态");
            row.createCell(4).setCellValue("商品名");
            row.createCell(5).setCellValue("超市");
            row.createCell(6).setCellValue("分享类型");
            row.createCell(7).setCellValue("价格（元）");
            row.createCell(8).setCellValue("价格类型");
            row.createCell(9).setCellValue("有效开始时间");
            row.createCell(10).setCellValue("有效结束时间");
            row.createCell(11).setCellValue("创建时间");
            // 定义样式
            CellStyle cellStyle = workbook.createCellStyle();
            // 格式化日期
            //cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd"));
            String pattern = "yyyy-MM-dd HH:mm:ss";
            // 遍历输出
            for (int i = 1; i <= list.size(); i++) {
                Correct correct1 = list.get(i - 1);
                if("WAIT_AUDIT".equals(correct1.getStatus())){
                    correct1.setStatus("待审核");
                }else if("AUDIT_SUCCESS".equals(correct1.getStatus())){
                    correct1.setStatus("审核通过");
                }else if("AUDIT_SUCCESS".equals(correct1.getStatus())){
                    correct1.setStatus("审核通过");
                }else if("RECALL".equals(correct1.getStatus())){
                    correct1.setStatus("撤回");
                }else if("AUDIT_FAIL".equals(correct1.getStatus())){
                    correct1.setStatus("审核不通过");
                }else if("AFFECTED".equals(correct1.getStatus())){
                    correct1.setStatus("已生效");
                }else if("EXPIRED".equals(correct1.getStatus())){
                    correct1.setStatus("已过期");
                }

                if("SHARE".equals(correct1.getType())){
                    correct1.setType("分享");
                }else if("CORRECT".equals(correct1.getType())){
                    correct1.setType("纠错");
                }

                if("NORMAL".equals(correct1.getPriceType())){
                    correct1.setPriceType("正常价");
                }else if("PROMOTION".equals(correct1.getPriceType())){
                    correct1.setPriceType("促销价");
                }else if("DEAL".equals(correct1.getPriceType())){
                    correct1.setPriceType("处理价");
                }else if("MEMBER".equals(correct1.getPriceType())){
                    correct1.setPriceType("会员价");
                }

                row = sheet.createRow(i);
                row.createCell(0).setCellValue(correct1.getCorrectId());
                row.createCell(1).setCellValue(correct1.getRealName());
                row.createCell(2).setCellValue(correct1.getMobile());
                row.createCell(3).setCellValue(correct1.getStatus());
                row.createCell(4).setCellValue(correct1.getFullName());
                row.createCell(5).setCellValue(correct1.getName());
                row.createCell(6).setCellValue(correct1.getType());
                row.createCell(7).setCellValue(correct1.getPrice()/100.00);
                row.createCell(8).setCellValue(correct1.getPriceType());
                row.createCell(9).setCellValue(DateUtil.date2String(correct1.getStartTime(),pattern));
                row.createCell(10).setCellValue(DateUtil.date2String(correct1.getEndTime(),pattern));
                row.createCell(11).setCellValue(DateUtil.date2String(correct1.getCreateTime(),pattern));
            }
            OutputStream fOut = response.getOutputStream();
            workbook.write(fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/updatePriceForAdmin.do")
    @ResponseBody
    public Object updatePriceForAdmin(Correct correct, MultipartFile file, HttpServletRequest httpRequest) {
        //获取当前时间和一年后的时间
        Date nowTime = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowTime);//设置起时间
        cal.add(Calendar.YEAR, 1);//增加一年
        Date afterOneYearTime = cal.getTime();
        try{
        if (
                (correct.getPrice() == null || correct.getPrice() <= 0) ||
                        (correct.getGoodsSkuId() == null || correct.getGoodsSkuId() <= 0) ||
                        (correct.getSupermarketId() == null || correct.getSupermarketId() <= 0)
        ) {
            throw new BusinessException(ErrorType.INCOMPLETE_INFO);
        }
            if(correct.getPrice()>=210000000){
            System.out.println("分享价格参数溢出,:");
            throw new Exception();
        }

        //后台分享部分数据处理,设置默认开始时间为当前时间，结束时间为一年后
        if (correct.getStartTime() == null) {
            correct.setStartTime(nowTime);
        }
        if (correct.getEndTime() == null) {
            correct.setEndTime(afterOneYearTime);
        }
        if (correct.getPriceType() == null || "".equals(correct.getPriceType())) {
            correct.setPriceType(PriceType.NORMAL.toString());
        }
        String userType = OperatorType.ADMIN.toString();
        AdminAccount adminAccount = (AdminAccount) httpRequest.getSession().getAttribute("loginAccount");
                    Integer creatorId = adminAccount.getAccountId();
        correct.setUserId(creatorId);

        if ("SHARE".equals(correct.getCorrectType())) {
            if(correct.getCorrectId() != null ){
                correct.setCorrectId(null);
            }
            // 线程安全并发处理
            initVersion(correct);
            correctService.share(correct, file, OperatorType.ADMIN.getOperatorTypeName());
        }
        if ("CORRECT".equals(correct.getCorrectType())) {
            if(correct.getCorrectId() != null ){
                correct.setParentId(correct.getCorrectId());
                correct.setCorrectId(null);
            }
            correctService.correct(correct, file, OperatorType.ADMIN.getOperatorTypeName());
        }

        if ("VIEW".equals(correct.getCorrectType())) {
            //
            //调用撤回方法
            Correct currentCorrect = correctService.recall(correct, userType);

            //撤回后,重新通过超市id和商品id查询该商品的状态(可纠错,可分享,可查看)
            Supermarket supermarket = supermarketGoodsService.getCorrectTypeBySupermarketIdAndgoodsSkuId(correct.getGoodsSkuId(),correct.getSupermarketId());
            if("CORRECT".equals(supermarket.getCorrectType())){
                if(correct.getCorrectId() != null ){
                    correct.setCorrectId(null);
                }
                correct.setParentId(currentCorrect.getParentId());
                correctService.correct(correct, file, OperatorType.ADMIN.getOperatorTypeName());
            }else if("SHARE".equals(supermarket.getCorrectType())){
                if(correct.getCorrectId() != null ){
                    correct.setCorrectId(null);
                }

                // 线程安全并发处理
                initVersion(correct);
                correctService.share(correct, file, OperatorType.ADMIN.getOperatorTypeName());
            }else{
                throw new BusinessException(ErrorType.SYSTEM_ERROR);
            }
        }
        return new ResponseData("修改价格成功！");
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }


    //根据用户id和商品id获取超市和价格信息(user_id设为null，查询出所有超市价格)
    @RequestMapping("/priceSupermarket.do")
    @ResponseBody
    public Object showPriceSupermarket(Integer goodsSkuId) {

        try {

            List<Supermarket> priceSupermarketList = supermarketGoodsService.getPriceSupermarketBycommunityIdAndgoodsSkuId(null, goodsSkuId);
            return new ResponseData(priceSupermarketList);

        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);

        }

    }

    //获取纠错表其它超市价格
    @RequestMapping("/getOtherPrice.do")
    public String getOtherPrice(Integer goodsSkuId, ModelMap modelMap){


        List<Correct> otherPrice = correctService.getOtherPrice(goodsSkuId);


        modelMap.addAttribute("otherPrice",otherPrice);

        return "/jsp/goods/otherPrice";

    }

    //获取商品生效最低价列表
    @RequestMapping("/getaffectedminprice.do")
    @ResponseBody
    public Object getAffectedMinPrice(Correct correct){
        try {
            List<Correct> correctList = correctService.getAffectedMinPrice(correct);

            PageResult<Correct> page = new PageResult<>(correctList, correct.getTotal());

            return page;
        }catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);

        }
    }

    //获取商品所有超级价格
    @RequestMapping("/getSupermarketPrice.do")
    public String getSupermarketPrice(SupermarketGoods supermarketGoods, ModelMap modelMap){

        List<SupermarketGoods> supermarketGoodsList = supermarketGoodsService.getOtherPrice(supermarketGoods);

        modelMap.addAttribute("supermarketPrice",supermarketGoodsList);

        return "/jsp/goods/supermarketPrice";

    }



}
