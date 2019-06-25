package com.linayi.controller.community;

import com.linayi.entity.community.SmallCommunityReq;
import com.linayi.service.community.SmallCommunityReqService;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/smallReqCommunity")
public class SmallCommunityReqController {
    @Resource
    private SmallCommunityReqService smallCommunityReqService;

    @PostMapping("/list.do")
    @ResponseBody
    public Object getSmallCommunityReqList(SmallCommunityReq smallCommunityReq) {
        List<SmallCommunityReq> smallCommunityReqList = smallCommunityReqService.getSmallCommunityReqList(smallCommunityReq);
        return new PageResult<>(smallCommunityReqList,smallCommunityReq.getTotal());
    }

    @GetMapping("/updateStatus.do")
    @ResponseBody
    public Object updateStatus(SmallCommunityReq smallCommunityReq) {
        smallCommunityReqService.updateStatus(smallCommunityReq);
        return new ResponseData("操作成功!");
    }

    @GetMapping("/batchUpdateStatus.do")
    @ResponseBody
    public Object batchUpdateStatus(@RequestParam("idList[]") List<Integer> idList) {
        smallCommunityReqService.batchUpdateStatus(idList);
        return new ResponseData("操作成功!");
    }

    @GetMapping("/show.do")
    @ResponseBody
    public Object show(SmallCommunityReq smallCommunityReq) {
        ModelAndView mv = new ModelAndView("/jsp/community/SmallCommunityReqShow");
        mv.addObject("smallCommunityReq", smallCommunityReqService.get(smallCommunityReq));
        return mv;
    }
}
