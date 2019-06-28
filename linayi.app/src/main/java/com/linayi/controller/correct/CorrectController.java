package com.linayi.controller.correct;


import com.linayi.controller.BaseController;
import com.linayi.dao.correct.PriceAuditTaskMapper;
import com.linayi.entity.correct.Correct;
import com.linayi.entity.correct.PriceAuditTask;
import com.linayi.entity.correct.SupermarketGoodsVersion;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.enums.OperatorType;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.correct.CorrectService;
import com.linayi.service.correct.SupermarketGoodsVersionService;
import com.linayi.service.supermarket.SupermarketService;
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
	private SupermarketService supermarketService;
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

			//校验是否绑定超市
			Supermarket supermarket = supermarketService.getSupermarketByProcurerId(userId);
			if(supermarket == null){
				throw new BusinessException(ErrorType.NOT_PROCURER_NO_AUDIT);
			}
			correct.setSupermarket(supermarket);

			List<Correct> correctList = correctService.getWaitAuditCorrect(correct);
			Integer totalPage = (int) Math.ceil(Double.valueOf(correct.getTotal())/Double.valueOf(correct.getPageSize()));
			if(totalPage <= 0){
				totalPage++;
			}
			Map<String , Object> map = new HashMap<>();
			map.put("data", correctList);
			map.put("supermarketName", supermarket.getName());
			map.put("totalPage", totalPage);
			map.put("currentPage",correct.getCurrentPage() );

			return new ResponseData(map);
		}catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
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

			correct.setResultStr("审核成功！");
			return new ResponseData(correct);
		}catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}

	}

	//审核历史
	@RequestMapping("/audithistory.do")
	@ResponseBody
	public Object auditHistory(@RequestBody Correct correct){
//		try {
			if(correct.getPageSize() == null){
				correct.setPageSize(8);
			}

			Integer userId = getUserId();
			correct.setUserId(userId);

//			//校验是否绑定超市
//			Supermarket supermarket = supermarketService.getSupermarketByProcurerId(userId);
//			if(supermarket == null){
//				throw new BusinessException(ErrorType.NOT_PROCURER_NO_AUDIT);
//			}

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


	/**
	 * 分享纠错查看按钮合并
	 * @param correct
	 * @param file
	 * @return
	 */
	@RequestMapping("/updatePriceByApp.do")
	@ResponseBody
	public Object updatePriceByApp(Correct correct, MultipartFile file){
		if(correct.getEndTime().before(correct.getStartTime())){
			throw new BusinessException(ErrorType.TIME_SEQUENCE_ERROR);
		}
		try {
			correct.setUserId(getUserId());
			correct.setAuditType(OperatorType.USER.toString());
			correctService.updatePriceByApp(correct,file);
			if("CORRECT".equals(correct.getCorrectType())){
				return new ResponseData("价格修改成功");
			}else {
				return new ResponseData("价格分享成功");
			}
		}catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}

	}


	/**
	 * 获取任务总数量和完成数量
	 * @param priceAuditTask
	 * @return
	 */
	@RequestMapping("/getTotalQuantity.do")
	@ResponseBody
	public Object getTotalQuantity(@RequestBody PriceAuditTask priceAuditTask){
		try {
			PriceAuditTask priceAudit = correctService.getTotalQuantity(priceAuditTask);
			return new ResponseData(priceAudit);
		}catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
	}


	/**
	 * 审核2个月前生效最低价
	 * @param correct
	 * @return
	 */
	@RequestMapping("/updatePriceAudit.do")
	@ResponseBody
	public Object updatePriceAudit(@RequestBody Correct correct){
		try {
			correct.setUserId(getUserId());
			correct.setAuditType(OperatorType.USER.toString());
			correctService.updatePriceAudit(correct);
			correct.setResultStr("审核成功！");
			return new ResponseData(correct);
		}catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}

	}

	/**
	 * 根据超市id和任务日期获取待审核商品列表
	 * @param correct
	 * @return
	 */
	@RequestMapping("/taskGoodsSkuList.do")
	@ResponseBody
	public Object getTaskGoodsSkuList(@RequestBody Correct correct){
		try {
		if(correct.getPageSize() == null){
			correct.setPageSize(8);
		}
		List<Correct> correctList = correctService.getTaskGoodsSkuList(correct);
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


	/**
	 * 任务点击审核通过
	 * @param correct
	 * @return
	 */
	@RequestMapping("/taskAuditSuccess.do")
	@ResponseBody
	public Object taskAuditSuccess(@RequestBody Correct correct) {
		try {
			correct.setUserId(getUserId());
			correctService.taskAuditSuccess(correct);
			return new ResponseData("审核成功");
		} catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
	}



	/**
	 * 任务点击价格错误/分享价格
	 * @param correct
	 * @return
	 */
	@RequestMapping("/taskAuditPriceError.do")
	@ResponseBody
	public Object taskAuditPriceError(@RequestBody Correct correct) {
		try {
			correct.setUserId(getUserId());
			Correct correct1 = correctService.taskAuditPriceError(correct);
			correct1.setResultStr("审核成功！");
			return new ResponseData(correct1);
		} catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
	}


	/**
	 * 任务点击价格错误/暂无价格
	 * @param correct
	 * @return
	 */
	@RequestMapping("/noTimePrice.do")
	@ResponseBody
	public Object noTimePrice(@RequestBody Correct correct) {
		try {
			correct.setUserId(getUserId());
			 correctService.noTimePrice(correct);
			return new ResponseData("操作成功");
		} catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
	}


}
