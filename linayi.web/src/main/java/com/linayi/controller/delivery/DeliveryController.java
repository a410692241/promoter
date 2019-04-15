package com.linayi.controller.delivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linayi.entity.delivery.DeliveryTask;
import com.linayi.service.delivery.DeliveryTaskService;

@Controller
@RequestMapping("/task/delivery")
public class DeliveryController {
	
	
	@Autowired
	private DeliveryTaskService deliveryTaskService;
	
	
	@RequestMapping("/selectAll.do")
	@ResponseBody
	public Object selectAll(DeliveryTask deliveryTask){
		
		System.out.println(deliveryTask.getStatus());
		return deliveryTaskService.selectAll(deliveryTask);

	}
	@RequestMapping("/show.do")
	public Object show(DeliveryTask deliveryTask){
		ModelAndView mv = new ModelAndView("jsp/task/DeliveryTaskShow");
		Long ordersId = deliveryTask.getOrdersId();
		Long deliveryTaskId = deliveryTask.getDeliveryTaskId();
		deliveryTask = deliveryTaskService.getDeliveryTaskView(ordersId, deliveryTaskId);
		mv.addObject("list", deliveryTask);
		return mv;
	}

}
