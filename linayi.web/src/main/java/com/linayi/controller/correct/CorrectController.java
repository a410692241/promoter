package com.linayi.controller.correct;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
import com.linayi.util.ResponseData;

@Controller
@RequestMapping("correct/correct")
public class CorrectController extends BaseController{

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
    public Object show(Long correctId){
        ModelAndView mv = new ModelAndView("/jsp/goods/CorrectShow");
        Correct correct = correctService.selectByCorrectId(correctId);

        mv.addObject("correct", correct);
        return mv;
    }

    @RequestMapping("/getInfo.do")
    @ResponseBody
    public Object edit(Long correctId){
        Correct correct = correctService.selectByCorrectId(correctId);
        ModelAndView mv = new ModelAndView("/jsp/goods/CorrectEdit");
        mv.addObject("correct", correct);
        return mv;
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Object save(Correct correct){
        try {
            correctService.updateCorrect(correct);
            return new ResponseData("success").toString();
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseData(ErrorType.UPDATE_ERROR).toString();
        }
    }

    @RequestMapping("/audit.do")
    @ResponseBody
    public Object audit(Correct correct,HttpSession session){
        try {
        	AdminAccount adminaccount = (AdminAccount)session.getAttribute("loginAccount");
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
    public Object batchAudit(@RequestParam("correctIdList[]") List<Integer> correctIdList){
    	try {
    		for(Integer correctId:correctIdList){
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
   	public Object recall(Correct correct,HttpSession session){
   		try {
   			AdminAccount adminaccount = (AdminAccount)session.getAttribute("loginAccount");
   			correct.setUserId(adminaccount.getAccountId());
   			String userType = OperatorType.ADMIN.toString();
   			correctService.recall(correct,userType);
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
   	public Object share(Correct correct,MultipartFile file,HttpServletRequest httpRequest){
       	//获取当前时间和一年后的时间
       	Date nowTime = new Date();
       	Calendar cal = Calendar.getInstance();
       	cal.setTime(nowTime);//设置起时间
       	cal.add(Calendar.YEAR, 1);//增加一年
       	Date afterOneYearTime = cal.getTime();
   		try {
   			if(	
   				(correct.getPrice() == null || correct.getPrice() <= 0) ||
   				(correct.getGoodsSkuId() == null || correct.getGoodsSkuId() <= 0) ||
   				(correct.getSupermarketId() == null || correct.getSupermarketId() <= 0)
   			){
   				throw new BusinessException(ErrorType.INCOMPLETE_INFO);
   			}
   			
   			// 线程安全并发处理
   			initVersion(correct);

   			//后台分享部分数据处理,设置默认开始时间为当前时间，结束时间为一年后
   			if(correct.getStartTime() == null){
   				correct.setStartTime(nowTime);
   			}
   			if(correct.getEndTime() == null){
   				correct.setEndTime(afterOneYearTime);
   			}
   			if(correct.getPriceType() == null){
   				correct.setPriceType(PriceType.NORMAL.toString());
   			}
   			String userType=OperatorType.ADMIN.toString();
   			correct.setPrice(correct.getPrice()*100);
			AdminAccount adminAccount=(AdminAccount)httpRequest.getSession().getAttribute("loginAccount");
			Integer creatorId=adminAccount.getAccountId();
			correct.setUserId(creatorId);
   			
   			correctService.share(correct, file, userType);
   			return new ResponseData("您的分享申请提交成功!").toString();
   		} catch (BusinessException e) {
   			return new ResponseData(e.getErrorType()).toString();
   		} catch (Exception e) {
   			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
   		}
   	}
    
    public void initVersion(Correct correct){
		try {
			SupermarketGoodsVersion version = new SupermarketGoodsVersion();
			version.setSupermarketId(correct.getSupermarketId());
			version.setGoodsSkuId(Integer.parseInt(correct.getGoodsSkuId()+""));
			supermarketGoodsVersionService.insert(version);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    @RequestMapping("/exportShareRecord.do")
    public void exportShareRecord() {
		try {
			getRequest().setCharacterEncoding("UTF-8");//设置request的编码方式，防止中文乱码
			
			String fileName =null;
			
			fileName ="666666";//设置导出的文件名称
			
			String contentType = "application/vnd.ms-excel";//定义导出文件的格式的字符串
			String recommendedName = new String(fileName.getBytes(),"iso_8859_1");//设置文件名称的编码格式
			getResponse().setContentType(contentType);//设置导出文件格式
			getResponse().setHeader("Content-Disposition", "attachment; filename=" + recommendedName + ".xls");
			getResponse().setCharacterEncoding("utf-8");
			getResponse().resetBuffer();
			//利用输出输入流导出文件
			ServletOutputStream sos = getResponse().getOutputStream();
			String tempsb="";
			String tableHtml = "<table><tr><td>sdgsdhdsh888888888888</td></tr><tr><td>99999999999</td></tr></table>";
			if(!StringUtils.isEmpty(tableHtml)){
//				tempsb="<meta http-equiv=\"content-type\" content=\"application/ms-excel; charset=UTF-8\"/>"+tableHtml;
				tempsb="<meta http-equiv=\"content-type\" content=\"application/ms-excel; charset=UTF-8\"/>"+tableHtml;
			}
			sos.write(tempsb.getBytes("utf-8"));
			sos.flush();
			sos.close();
		} catch (Exception e) {
			
		}
	}

   
}
