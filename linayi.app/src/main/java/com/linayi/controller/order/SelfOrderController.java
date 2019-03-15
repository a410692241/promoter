package com.linayi.controller.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linayi.entity.goods.GoodsSku;
import org.quartz.impl.calendar.BaseCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.linayi.controller.BaseController;
import com.linayi.entity.order.SelfOrder;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.exception.ErrorType;
import com.linayi.service.order.SelfOrderService;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;

import sun.net.www.content.audio.basic;

@RestController
@RequestMapping("/self/order")
public class SelfOrderController extends BaseController {

    @Autowired
    private SelfOrderService selforderService;

    @RequestMapping("/selfOrder.do")
    public Object selfOrder(@RequestBody GoodsSku goodsSku) {

    	try {
    	goodsSku.setUserId(getUserId());
		
		if(goodsSku.getPageSize() == null){
			goodsSku.setPageSize(8);
		}
    	
    	
    	 List<SelfOrder> selfOrderList = selforderService.getSelfOrderByUserId(goodsSku);
            
    		Integer totalPage = (int) Math.ceil(Double.valueOf(goodsSku.getTotal())/Double.valueOf(goodsSku.getPageSize()));
    		if(totalPage <= 0){
    			totalPage++;
    		}
    		
    		Map<String , Object> map = new HashMap<>();
    		map.put("data", selfOrderList);
    		map.put("totalPage", totalPage);
    		map.put("currentPage",goodsSku.getCurrentPage() );
    		return new ResponseData(map);

    	
    	} catch (Exception e) {
    		return new ResponseData(ErrorType.SYSTEM_ERROR);
    		
		}
    }
}
