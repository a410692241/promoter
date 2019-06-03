package com.linayi.controller.correct;


import com.linayi.controller.BaseController;
import com.linayi.entity.correct.Correct;
import com.linayi.entity.correct.SupermarketGoodsVersion;
import com.linayi.enums.OperatorType;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.correct.CorrectService;
import com.linayi.service.correct.SupermarketGoodsVersionService;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/correct/correct")
public class CorrectController extends BaseController {

	@Autowired
	private CorrectService correctService;

	@Resource
	private SupermarketGoodsVersionService supermarketGoodsVersionService;

	//显示纠错申请页面
	@RequestMapping("/showcorrect.do")
	public String showCorrect(){

		return "correct";
	}

	//显分享申请页面
	@RequestMapping("/showshare.do")
	public String showShare(){

		return "share";
	}



	@RequestMapping("/correct.do")
	@ResponseBody
	public Object correct(Correct correct,MultipartFile file){
		try {
			if(	(correct.getPrice() == null || correct.getPrice() <= 0) ||
//				(file == null) ||
				(correct.getPriceType() == null || "".equals(correct.getPriceType())) ||
				(correct.getStartTime() == null) ||
				(correct.getEndTime() == null) ||
				(correct.getParentId() == null || correct.getParentId() <= 0) ||
				(correct.getGoodsSkuId() == null || correct.getGoodsSkuId() <= 0) ||
				(correct.getSupermarketId() == null || correct.getSupermarketId() <= 0)
				){
				throw new BusinessException(ErrorType.INCOMPLETE_INFO);
			}
			if(correct.getEndTime().before(correct.getStartTime())){
				throw new BusinessException(ErrorType.TIME_SEQUENCE_ERROR);
			}

			Integer userId = getUserId();
			correct.setUserId(userId);
			String userType=OperatorType.USER.toString();
			correctService.correct(correct, file, userType);

			return new ResponseData("您的纠错申请提交成功!").toString();
		} catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
	}


	@RequestMapping("/share.do")
	@ResponseBody
	public Object share(Correct correct,MultipartFile file){
		try {
			if(
				(correct.getPrice() == null || correct.getPrice() <= 0) ||
//				(file == null) ||
				(correct.getPriceType() == null || "".equals(correct.getPriceType())) ||
				(correct.getStartTime() == null) ||
				(correct.getEndTime() == null) ||
				(correct.getGoodsSkuId() == null || correct.getGoodsSkuId() <= 0) ||
				(correct.getSupermarketId() == null || correct.getSupermarketId() <= 0)
			){
				throw new BusinessException(ErrorType.INCOMPLETE_INFO);
			}
			if(correct.getEndTime().before(correct.getStartTime())){
				throw new BusinessException(ErrorType.TIME_SEQUENCE_ERROR);
			}

			// 线程安全并发处理
			initVersion(correct);

  			Integer userId = getUserId();
			correct.setUserId(userId);
			String userType=OperatorType.USER.toString();
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
		} catch (Exception e) {}
	}


	@RequestMapping("/view.do")
	@ResponseBody
	public Object view(@RequestBody Map<String, Object> param){
		ParamValidUtil<Correct> pvu = new ParamValidUtil<>(param);
		Correct correct = pvu.transObj(Correct.class);
		try {
			if((correct.getCorrectId() == null || correct.getCorrectId()  <= 0)){
				throw new BusinessException(ErrorType.INCOMPLETE_INFO);
			}
			Correct correct1=correctService.showView(correct.getCorrectId());

			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			correct1.setStrStartTime(sdf.format(correct1.getStartTime()));
			correct1.setStrEndTime(sdf.format(correct1.getEndTime()));

			return new ResponseData(correct1);
		} catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
	}

	@RequestMapping("/recall.do")
	@ResponseBody
	public Object recall(@RequestBody Map<String, Object> param){
		ParamValidUtil<Correct> pvu = new ParamValidUtil<>(param);
		Correct correct = pvu.transObj(Correct.class);
		try {
			Integer userId = getUserId();
			correct.setUserId(userId);

			String userType=OperatorType.USER.toString();
			correctService.recall(correct,userType);
			return new ResponseData("撤回成功!");
		} catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
	}

	@RequestMapping("/selecthistoryrecord.do")
	@ResponseBody
	public Object selecthistoryrecord(){
		try{
			Integer userId = getUserId();
			List<Correct> correctList = correctService.selectCorrectListByUserId(userId);

			return new ResponseData(correctList);
		}catch(Exception e){
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}

	}


	@RequestMapping("/historyView.do")
	@ResponseBody
	public Object historyView(@RequestBody Map<String,Object> param){
		ParamValidUtil<Correct> pvu = new ParamValidUtil<>(param);
		Correct correct = pvu.transObj(Correct.class);
		try {
			if(param.get("correctId") == null || ((Integer)param.get("correctId") <= 0)){
				throw new BusinessException(ErrorType.INCOMPLETE_INFO);
			}

			Correct correct1=correctService.historyView(correct);

			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			correct1.setStrStartTime(sdf.format(correct1.getStartTime()));
			correct1.setStrEndTime(sdf.format(correct1.getEndTime()));
			return new ResponseData(correct1);
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
	}


	@RequestMapping("/searchgoodsname.do")
	@ResponseBody
	public Object searchHistory(@RequestBody Correct correct){
		System.out.println("FullName:"+correct.getFullName());
		try {
			if(correct.getPageSize() == null){
				correct.setPageSize(8);
    		}

			Integer userId = getUserId();
			correct.setUserId(userId);
			List<Correct> correctList = correctService.selectCorrectListByGoodsName(correct);

			 Integer totalPage = (int) Math.ceil(Double.valueOf(correct.getTotal())/Double.valueOf(correct.getPageSize()));
				if(totalPage <= 0){
					totalPage++;
				}
				Map<String , Object> map = new HashMap<>();
				map.put("data", correctList);
				map.put("totalPage", totalPage);
				map.put("currentPage",correct.getCurrentPage() );

			return new ResponseData(map);
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}

	}


	@RequestMapping("/waitauditcorrect.do")
	@ResponseBody
	public Object WaitAuditCorrect(@RequestBody Correct correct){
		try {
			if(correct.getPageSize() == null){
				correct.setPageSize(8);
			}

			Integer userId = getUserId();
			correct.setUserId(userId);
			List<Correct> correctList = correctService.getWaitAuditCorrect(correct);
			Integer totalPage = (int) Math.ceil(Double.valueOf(correct.getTotal())/Double.valueOf(correct.getPageSize()));
			if(totalPage <= 0){
				totalPage++;
			}
			Map<String , Object> map = new HashMap<>();
			map.put("data", correctList);
			map.put("totalPage", totalPage);
			map.put("currentPage",correct.getCurrentPage() );

			return new ResponseData(map);
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
	}

	@RequestMapping("/auditprice.do")
	@ResponseBody
	public Object auditPrice(@RequestBody Correct correct){
		try {
			correct.setUserId(getUserId());
			correct.setAuditType(OperatorType.USER.toString());
			correctService.audit(correct);

			return new ResponseData("审核成功！").toString();
		}catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}

	}

	@RequestMapping("/audithistory.do")
	@ResponseBody
	public Object auditHistory(@RequestBody Correct correct){
//		try {
			if(correct.getPageSize() == null){
				correct.setPageSize(8);
			}

			Integer userId = getUserId();
			correct.setUserId(userId);
			List<Correct> correctList = correctService.getCorrectByAuditerId(correct);
			Integer totalPage = (int) Math.ceil(Double.valueOf(correct.getTotal())/Double.valueOf(correct.getPageSize()));
			if(totalPage <= 0){
				totalPage++;
			}
			Map<String , Object> map = new HashMap<>();
			map.put("data", correctList);
			map.put("totalPage", totalPage);
			map.put("currentPage",correct.getCurrentPage() );

			return new ResponseData(map);
//		} catch (Exception e) {
//			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
//		}
	}


}
