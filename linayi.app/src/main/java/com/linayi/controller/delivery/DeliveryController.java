package com.linayi.controller.delivery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linayi.dao.user.AuthenticationApplyMapper;
import com.linayi.entity.user.AuthenticationApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.linayi.controller.BaseController;
import com.linayi.entity.order.Orders;
import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.delivery.DeliveryBoxService;
import com.linayi.service.delivery.DeliveryTaskService;
import com.linayi.util.PageResult;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/delivery")
public class DeliveryController extends BaseController {
    @Autowired
    private DeliveryTaskService deliveryTaskService;
    @Autowired
    private DeliveryBoxService deliveryBoxService;
    @Autowired
    private AuthenticationApplyMapper authenticationApplyMapper;


    //显示订单信息
    @RequestMapping("/deliveryList.do")
    @ResponseBody
    public Object getDeliveryTaskByUserIdAndStatus(@RequestBody Orders orders) {
        try {
            if (orders.getPageSize() == null) {
                orders.setPageSize(8);
            }
            AuthenticationApply authenticationApply = new AuthenticationApply();
            authenticationApply.setAuthenticationType("DELIVERER");
            authenticationApply.setUserId(getUserId());
            authenticationApply = authenticationApplyMapper.getAuthenticationApplyByUserIdAndType(authenticationApply);
            if (authenticationApply != null) {
                orders.setDelivererId(getUserId());
            }
            List<Orders> ordersList = deliveryTaskService.getOrdersBydelivererIdAndStatus(orders);
            Integer totalPage = (int) Math.ceil(Double.valueOf(orders.getTotal()) / Double.valueOf(orders.getPageSize()));
            if (totalPage <= 0) {
                totalPage++;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("data", ordersList);
            map.put("totalPage", totalPage);
            map.put("currentPage", orders.getCurrentPage());
            return new ResponseData(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    //修改配送中订单状态
    @RequestMapping("/updateFinishedStatus.do")
    @ResponseBody
    public Object updateFinishedStatus(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<Orders> pa = new ParamValidUtil<>(param);
            Orders orders = pa.transObj(Orders.class);
            int num = deliveryTaskService.updateFinishedStatus(orders);
            return new ResponseData(num);
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }

    }

    @RequestMapping("/deliveryTask/getAllDeliveryTask.do")
    @ResponseBody
    public Object getAllDeliveryTask(@RequestBody Map<String, Object> param) {
        try {
        	Integer communityId = getCommunityId();
            ParamValidUtil<Orders> pvu = new ParamValidUtil<>(param);
            Orders orders = pvu.transObj(Orders.class);
            orders.setCommunityId(communityId);
            List<Orders> list = deliveryTaskService.getListDeliveryTask(orders);
            PageResult<Orders> pr = new PageResult<>(list,orders);
            return new ResponseData(pr);
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }

    @RequestMapping("/deliveryTask/toViewDeliveryTask.do")
    @ResponseBody
    public ResponseData viewDeliveryTask(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<Orders> pvu = new ParamValidUtil<>(param);
            Orders orders = pvu.transObj(Orders.class);
            Long ordersId = orders.getOrdersId();
            String boxNo = orders.getBoxNo();
            Orders deliveryTask = deliveryTaskService.toViewDeliveryTask(boxNo, ordersId);
            return new ResponseData(deliveryTask);
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }

    @RequestMapping("/deliveryTask/sealBox.do")
    @ResponseBody
    public ResponseData sealBox(String boxNo, Long ordersId, MultipartFile file) {
        try {
            deliveryTaskService.sealBox(boxNo, ordersId, file);
            return new ResponseData("success");
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }

    //配送订单详情页面
    @RequestMapping("/getDeliveryTaskDetails.do")
    @ResponseBody
    public Object getDeliveryTaskDetails(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<Orders> pvu = new ParamValidUtil<>(param);
            Orders orders = pvu.transObj(Orders.class);
            orders = deliveryTaskService.getDeliveryTaskDetails(orders);
            return new ResponseData(orders);
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }

    /*分拣装箱详情-“装箱”按钮*/
	@RequestMapping("/deliveryBox/boxing.do")
	@ResponseBody
	public Object boxing(@RequestBody Map<String,Object> map){
		try {
			String obj = map.get("procurementTaskList") + "";
			String replace = obj.replace("=", ":");
			/*replace=replace.replace("}","\"}");
			replace=replace.replace("boxNo:", "boxNo:\"");*/
			List<ProcurementTask> ts = (List<ProcurementTask>) JSONArray.parseArray(replace, ProcurementTask.class);
			deliveryBoxService.boxing(ts);
			return new ResponseData("装箱成功");
		} catch (BusinessException e) {
			return new ResponseData(e.getErrorType()).toString();
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
	}
}

