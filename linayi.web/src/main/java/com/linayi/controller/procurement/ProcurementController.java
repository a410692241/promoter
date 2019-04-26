package com.linayi.controller.procurement;


import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.exception.ErrorType;
import com.linayi.service.procurement.ProcurementService;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(ProcurementTask procurementTask){
        List<ProcurementTask> procurementTasks = procurementService.getProcurementsByRECEIVEDFLOW(procurementTask);
        PageResult page = new PageResult(procurementTasks,procurementTask.getTotal());
        return page;
    }

    @RequestMapping("/batchDelivery.do")
    @ResponseBody
    public Object batchDelivery(@RequestParam("procurementTaskIdList[]") List<Long> procurementTaskIdList){
        try {
            procurementService.batchDelivery(procurementTaskIdList);
            return new ResponseData("success");
        }catch (Exception e){
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }
    @RequestMapping("/exportData.do")
    @ResponseBody
    public void exportData(ProcurementTask procurementTask, HttpServletRequest request, HttpServletResponse response) throws Exception {
        procurementService.exportData(procurementTask,request,response);
    }

}
