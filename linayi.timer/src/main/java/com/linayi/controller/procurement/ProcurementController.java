package com.linayi.controller.procurement;

import com.alibaba.fastjson.JSON;
import com.linayi.dao.order.OrdersGoodsMapper;
import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.order.OrdersGoods;
import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.service.procurement.ProcurementService;
import com.linayi.util.ParamValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/procurement/procurement")
public class ProcurementController {
    @Autowired
    private ProcurementService procurementService;
    @Autowired
    SupermarketMapper supermarketMapper;

    @RequestMapping("/procurementList.do")
    @ResponseBody
    public List<ProcurementTask> procurementList(ProcurementTask procurementTask){
        List<ProcurementTask> procurementTasks = procurementService.showProcurementTaskAndOrderGoods(procurementTask);
        return procurementTasks;
    }

}
