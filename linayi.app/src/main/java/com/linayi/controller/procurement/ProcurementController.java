package com.linayi.controller.procurement;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linayi.entity.community.Community;
import com.linayi.entity.supermarket.Supermarket;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.linayi.controller.BaseController;
import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.exception.ErrorType;
import com.linayi.service.procurement.ProcurementService;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;



@RestController
@RequestMapping("/procurement/procurement")
public class ProcurementController extends BaseController {

    @Autowired
    private ProcurementService procurementService;

    @RequestMapping("/getProcurementList.do")
    public Object getProcurementList(@RequestBody Map<String, Object> param){
        ParamValidUtil<ProcurementTask> pvu = new ParamValidUtil<>(param);
        try {
            pvu.Exist("procureStatus");
            ProcurementTask procurementTask = pvu.transObj(ProcurementTask.class);
            procurementTask.setUserId(getUserId());
            Object procurementTaskList = procurementService.getProcurementList(procurementTask);
            return new ResponseData(procurementTaskList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("F", ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

    @RequestMapping("/getProcurement.do")
    public Object getProcurement(@RequestBody Map<String, Object> param){
        ParamValidUtil<ProcurementTask> pvu = new ParamValidUtil<>(param);
        try {
            pvu.Exist("procurementTaskId");
            ProcurementTask procurementTask = pvu.transObj(ProcurementTask.class);
            procurementTask =procurementService.getProcurementTask(procurementTask);
            return new ResponseData(procurementTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("F", ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

    @RequestMapping("/updateProcureStatus.do")
    public Object updateProcurementStatus(@RequestBody Map<String, Object> param){
        ParamValidUtil<ProcurementTask> pvu = new ParamValidUtil<>(param);
        try {
            pvu.Exist("procureStatus","procurementTaskId");
            ProcurementTask procurementTask = pvu.transObj(ProcurementTask.class);
            procurementService.updateProcurement(procurementTask);
            return new ResponseData("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("F", ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

	/**
	 * 获取采买任务社区列表
	 * @return
	 */
	@RequestMapping("/getCommunityList.do")
	public Object getCommunityProcurement(){
		try {
			Integer userId = getUserId();
			List<Community> communityList = procurementService.getProcurementCommunity(userId);
			return new ResponseData(communityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
	}


	/**
	 * 获取社区采买任务列表
	 * @return
	 */
	@RequestMapping("/getCommunityPros.do")
	public Object getCommunityProcurementList(Integer communityId,String procureStatus){
		try {
			List<ProcurementTask> procurementTaskList = procurementService.getCommunityProcurement(communityId, procureStatus);
			return new ResponseData(procurementTaskList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
	}

	/**
	 * 改变采买任务状态
	 * @param goodsSkuId
	 * @param quantity
	 * @param communityId
	 * @return
	 */
	@RequestMapping("/updateProcurStatus.do")
	public Object updateProcurmentStatus(Integer goodsSkuId, Integer quantity, Integer communityId){
		try {
			procurementService.updateProcurmentStatus(goodsSkuId, quantity,communityId);
			return new ResponseData("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseData("F", ErrorType.SYSTEM_ERROR.getErrorMsg());
	}

	//根据社区id和收货状态查询采买任务列表
	@RequestMapping("/getProcurementTask.do")
	public Object getProcurementTask(@RequestBody ProcurementTask procurementTask) {

		try {
			//社区id
			procurementTask.setCommunityId(getCommunityId());

    		if(procurementTask.getPageSize() == null){
    			procurementTask.setPageSize(15);
    		}

    		List<ProcurementTask> procurementTaskList =
    				procurementService.getProcurementTaskStatus(procurementTask);
    		Integer totalPage = (int) Math.ceil(Double.valueOf(procurementTask.getTotal())/Double.valueOf(procurementTask.getPageSize()));
    		if(totalPage <= 0){
    			totalPage++;
    		}

    		Map<String , Object> map = new HashMap<>();
    		map.put("data", procurementTaskList);
    		map.put("totalPage", totalPage);
    		map.put("currentPage",procurementTask.getCurrentPage() );
    		return new ResponseData(map);

		} catch (Exception e) {
			String message = e.getMessage();
			ResponseData responseData = new ResponseData(ErrorType.SYSTEM_ERROR);
			if (message != null){
				responseData.setErrorType(message);
			}
			return responseData;
		}

	}


	//买手收货详情页
	@RequestMapping("/receivingDetails.do")
	public Object receivingDetails(@RequestBody ProcurementTask procurementTask) {

		try {
			ProcurementTask newprocurementTask = procurementService.getProcurementById(procurementTask.getProcurementTaskId());
			return new  ResponseData(newprocurementTask);
		} catch (Exception e) {

			return new ResponseData(ErrorType.SYSTEM_ERROR);
		}

	}

	//买手收货详情点击验货完毕
	@RequestMapping("/inspectionComplete.do")
	public Object inspectionComplete(@RequestBody ProcurementTask procurementTask) {

		try {
			Integer newprocurementTask = procurementService.updateOrderStatus(procurementTask);
			return new  ResponseData(newprocurementTask);
		} catch (Exception e) {

			return new ResponseData(ErrorType.SYSTEM_ERROR);
		}

	}

	 @RequestMapping("/boxingDetails.do")
	    public Object boxingDetails(@RequestBody Map<String, Object> param){//前端参数 ordersId
	    	try {
	    		ParamValidUtil<ProcurementTask> pvu = new ParamValidUtil<>(param);
	       	 	ProcurementTask procurementTask = pvu.transObj(ProcurementTask.class);

	       	 	List<ProcurementTask> ProcurementTaskList = procurementService.getProcurementTaskByOrderIdAndActualQuantity(procurementTask);
	       	 	return new ResponseData(ProcurementTaskList);
			} catch (Exception e) {
				return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
			}

	    }


}
