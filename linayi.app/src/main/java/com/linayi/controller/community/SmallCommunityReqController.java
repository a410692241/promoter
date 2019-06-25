package com.linayi.controller.community;

import com.linayi.entity.community.SmallCommunityReq;
import com.linayi.exception.BusinessException;
import com.linayi.service.community.SmallCommunityReqService;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("community/smallCommunityReq")
public class SmallCommunityReqController {


    @Autowired
    private SmallCommunityReqService smallCommunityReqService;

    @RequestMapping("addSmallCommunityReq.do")
    @ResponseBody
    public Object addSmallCommunityReq(@RequestBody SmallCommunityReq smallCommunityReq) {
        try {
            smallCommunityReqService.addSmallCommunityReq(smallCommunityReq);
            return new ResponseData("请求成功!").toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(e).toString();
        }
    }
}
