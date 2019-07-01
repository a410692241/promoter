package com.linayi.controller.area;

import com.linayi.controller.BaseController;
import com.linayi.entity.BaseEntity;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.community.SmallCommunityReq;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.area.SmallCommunityService;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("area/SmallCommunity")
public class SmallCommunityController extends BaseController {


    @Autowired
    private SmallCommunityService smallCommunityService;

    @RequestMapping("getSmallCommunity.do")
    @ResponseBody
    public Object getSmallCommunityByAreaCode(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<SmallCommunity> pa = new ParamValidUtil<>(param);
            pa.Exist( "areaCode");
            SmallCommunity smallCommunity = pa.transObj(SmallCommunity.class);
            return new ResponseData(smallCommunityService.getSmallCommunityByAreaCode(smallCommunity.getAreaCode())).toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(e).toString();
        }
    }

    @RequestMapping("addSmallCommunity.do")
    @ResponseBody
    public Object addSmallCommunity(@RequestBody Map<String, Object> param) {
        try {

            ParamValidUtil<SmallCommunity> pa = new ParamValidUtil<>(param);
            pa.Exist( "areaCode");
            pa.Exist("name");
            SmallCommunity smallCommunity = pa.transObj(SmallCommunity.class);
            smallCommunity.setCreatorId(getUserId());
            return new ResponseData(smallCommunityService.addSmallCommunity(smallCommunity));
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(e).toString();
        }
    }
}
