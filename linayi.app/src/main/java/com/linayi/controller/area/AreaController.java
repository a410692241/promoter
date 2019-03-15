package com.linayi.controller.area;

import com.linayi.entity.area.Area;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.area.AreaService;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/area/area")
public class AreaController {

    @Autowired
    private AreaService areaService;


    @RequestMapping("/getArea.do")
    public Object getArea(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<Area> pa = new ParamValidUtil<>(param);
            pa.Exist( "parent");
            Area area = pa.transObj(Area.class);
            return new ResponseData(areaService.getArea(area)).toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    @RequestMapping("/getAllArea.do")
    public Object getAllArea(@RequestBody Map<String, Object> param) {
        try {
            return new ResponseData(areaService.getAllArea()).toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    @RequestMapping("/addArea.do")
    @Transactional
    public Object addArea(@RequestBody Map<String, Object> param) {
        try {
            ParamValidUtil<Area> pa = new ParamValidUtil<>(param);
            pa.Exist( "parent","level","name");
            Area area = pa.transObj(Area.class);
            areaService.addArea(area);
            return new ResponseData("添加成功").toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    @RequestMapping("/getAreaMap.do")
    public Object getAreaMap(@RequestBody Map<String, Object> param) {
        try {
            return new ResponseData(areaService.getAreaMap()).toString();
        } catch (BusinessException e) {
            return new ResponseData(e.getErrorType()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }
}
