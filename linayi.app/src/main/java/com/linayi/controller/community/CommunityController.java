package com.linayi.controller.community;


import com.linayi.dao.goods.AttributeMapper;
import com.linayi.entity.area.Area;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.community.Community;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.community.CommunityService;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("community/community")
public class CommunityController {


    @Autowired
    private CommunityService communityService;

    @RequestMapping("/getCommunityByAreaCode.do")
    @ResponseBody
    public Object getCommunityByAreaCode(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<Community> pa = new ParamValidUtil<>(param);
            pa.Exist("accessToken", "areaCode");
            Community community = pa.transObj(Community.class);
            return new ResponseData(communityService.getCommunityByAreaCode(community)).toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

}
