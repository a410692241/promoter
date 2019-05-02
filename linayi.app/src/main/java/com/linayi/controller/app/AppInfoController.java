package com.linayi.controller.app;

import com.linayi.entity.app.AppInfo;
import com.linayi.exception.ErrorType;
import com.linayi.util.PropertiesUtil;
import com.linayi.util.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AppInfoController {
    @RequestMapping("/getAPPUpdateInfomation")
    public ResponseData getAPPUpdateInfomation(){
        try {
            Double version = Double.valueOf(PropertiesUtil.getValueByKey("version"));
            String description = PropertiesUtil.getValueByKey("description");
            String force = PropertiesUtil.getValueByKey("force");
            String androidDownloadLink = PropertiesUtil.getValueByKey("androidDownloadLink");
            String iosDownloadLink = PropertiesUtil.getValueByKey("iosDownloadLink");
            AppInfo appInfo = new AppInfo();
            appInfo.setVersion(version);
            appInfo.setDescription(description);
            appInfo.setForce(force);
            appInfo.setAndroidDownloadLink(androidDownloadLink);
            appInfo.setIosDownloadLink(iosDownloadLink);
            return new ResponseData(appInfo);
        }catch (Exception e){
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }
}
