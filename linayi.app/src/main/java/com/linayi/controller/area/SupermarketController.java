package com.linayi.controller.area;

import com.linayi.entity.supermarket.Supermarket;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.area.SupermarketService;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("area/Supermarket")
public class SupermarketController {

    @Autowired
    private SupermarketService supermarketService;
    
    @RequestMapping("getSupermarket.do")
    @ResponseBody
    public Object getSupermarketAreaCode(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<Supermarket> pa = new ParamValidUtil<>(param);
            pa.Exist( "areaCode");
            Supermarket supermarket = pa.transObj(Supermarket.class);
            return new ResponseData(supermarketService.getSupermarketByAreaCode(supermarket.getAreaCode())).toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }
    
}
