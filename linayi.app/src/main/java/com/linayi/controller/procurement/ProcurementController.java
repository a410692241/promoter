package com.linayi.controller.procurement;



import java.util.Date;
import java.util.List;
import java.util.Map;
import com.linayi.entity.community.Community;
import com.linayi.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 * 用户端获取社区合并采买任务列表
	 * @return
	 */
	@RequestMapping("/getCommunityPros.do")
	public Object getCommunityProcurementList(@RequestBody Map<String, Object> param){
		try {
			ParamValidUtil<ProcurementTask> pvu = new ParamValidUtil<>(param);
			pvu.Exist("procureStatus");
			Integer userId = getUserId();
			ProcurementTask procurementTask = pvu.transObj(ProcurementTask.class);
			procurementTask.setUserId(userId);
			List<ProcurementTask> procurementTaskList = procurementService.getCommunityProcurement(procurementTask);
			PageResult<ProcurementTask> pageResult = new PageResult<>(procurementTaskList, procurementTask);
			return new ResponseData(pageResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
	}

	/**
	 * 用户端改变合并采买任务状态
	 * @return
	 */
	@RequestMapping("/updateProcurStatus.do")
	public Object updateProcurmentStatus(@RequestBody Map<String, Object> param){
		try {
			ParamValidUtil<ProcurementTask> pvu = new ParamValidUtil<>(param);
			pvu.Exist("counts","goodsSkuId");
			//实际采买数量
			ProcurementTask procurementTask = pvu.transObj(ProcurementTask.class);
			Integer userId = getUserId();
			procurementTask.setUserId(userId);
			procurementService.updateProcurmentStatus(procurementTask);
			return new ResponseData("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseData("F", ErrorType.SYSTEM_ERROR.getErrorMsg());
	}

	/**
	 * 用户端买合并采买任务详情页
	 * @param param
	 * @return
	 */
	@RequestMapping("/getProcureDetails.do")
	public Object getProcureDetails(@RequestBody Map<String, Object> param) {
		try {
			ParamValidUtil<ProcurementTask> pvu = new ParamValidUtil<>(param);
			ProcurementTask procurementTask = pvu.transObj(ProcurementTask.class);
			ProcurementTask procurementTasks = procurementService.getprocurementDeatil(procurementTask);
			return new  ResponseData(procurementTasks);
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR);
		}
	}


	@RequestMapping("/updateProcurReceiveStatus.do")
    public Object updateProcurReceiveStatus(@RequestBody Map<String, Object> param){
	    try {
            ParamValidUtil<ProcurementTask> pvu = new ParamValidUtil<>(param);
            ProcurementTask procurementTask = pvu.transObj(ProcurementTask.class);
            procurementService.updateProcurReceiveStatus(procurementTask);
            return new ResponseData("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("F", ErrorType.SYSTEM_ERROR.getErrorMsg());
    }


	//社区端根据社区id和收货状态查询合并采买任务列表
	@RequestMapping("/getProcurementTask.do")
	public Object getProcurementTask(@RequestBody ProcurementTask procurementTask) {
		try {
			procurementTask.setCommunityId(getCommunityId());
			List<ProcurementTask> procurementTaskList = procurementService.getCommunityProcurement(procurementTask);
			PageResult<ProcurementTask> pageResult = new PageResult<>(procurementTaskList, procurementTask);
			return new ResponseData(pageResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseData(ErrorType.SYSTEM_ERROR);
	}


	/**
	 * 社区端买手收货合并采买任务详情页
	 * @param procurementTask 采买时间
	 * @return
	 */
	@RequestMapping("/receivingDetails.do")
	public Object receivingDetails(@RequestBody ProcurementTask procurementTask) {
		try {
			Integer userId = getUserId();
			procurementTask.setCommunityId(userId);
			ProcurementTask procurementTask1 = procurementService.getprocurementDeatil(procurementTask);
			return new  ResponseData(procurementTask1);
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR);
		}

	}

	//社区端买手收货详情点击验货完毕
	@RequestMapping("/inspectionComplete.do")
	public Object inspectionComplete(@RequestBody ProcurementTask procurementTask) {
		try {
			procurementTask.setCommunityId(getUserId());
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

	/**
	 * 获取未收货的商品列表
 	 * @param param
	 * @return
	 */
	@RequestMapping("/getNotReceivingGoods.do")
	public Object getNotReceivingGoods(@RequestBody Map<String, Object> param){
        try {
		    ParamValidUtil<ProcurementTask> pvu = new ParamValidUtil<>(param);
		    ProcurementTask procurementTask = pvu.transObj(ProcurementTask.class);

		    List<ProcurementTask> list = procurementService.getNotReceivingGoods(procurementTask);
		    PageResult<ProcurementTask> pr = new PageResult<>(list,procurementTask);
		    return new ResponseData(pr);
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
	}
	/**
	 * 对商品进行确认收货操作
	 * @param param
	 * @return
	 */
	@RequestMapping("/confirmGoods.do")
	public Object confirmGoods(@RequestBody Map<String, Object> param){
        try {
            ParamValidUtil<ProcurementTask> pvu = new ParamValidUtil<>(param);
            ProcurementTask procurementTask = pvu.transObj(ProcurementTask.class);

            Date date = procurementTask.getAccessTime();
            Integer goodsSkuId = procurementTask.getGoodsSkuId();
            procurementService.confirmGoods(date,goodsSkuId);
		    return new ResponseData("success");
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
	}
    /**
     * 获取未发货的商品列表
     * @param param
     * @return
     */
    @RequestMapping("/getNotDeliverGoods.do")
    public Object getNotDeliverGoods(@RequestBody Map<String, Object> param){
        try {
            ParamValidUtil<ProcurementTask> pvu = new ParamValidUtil<>(param);
            ProcurementTask procurementTask = pvu.transObj(ProcurementTask.class);

            List<ProcurementTask> list = procurementService.getNotDeliverGoods(procurementTask);
            PageResult<ProcurementTask> pr = new PageResult<>(list,procurementTask);
            return new ResponseData(pr);
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }
    /**
     * 对商品进行确认发货操作
     * @param param
     * @return
     */
    @RequestMapping("/confirmDeliverGoods.do")
    public Object confirmDeliverGoods(@RequestBody Map<String, Object> param){
        try {
            ParamValidUtil<ProcurementTask> pvu = new ParamValidUtil<>(param);
            ProcurementTask procurementTask = pvu.transObj(ProcurementTask.class);

            Integer goodsSkuId = procurementTask.getGoodsSkuId();
            Integer communityId = procurementTask.getCommunityId();
            procurementService.confirmDeliverGoods(communityId,goodsSkuId);
            return new ResponseData("success");
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }
}
