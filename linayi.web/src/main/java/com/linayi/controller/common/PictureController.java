package com.linayi.controller.common;

import com.alibaba.fastjson.JSON;
import com.linayi.util.ConstantUtil;
import com.linayi.util.DateUtil;
import com.linayi.util.PropertiesUtil;
import com.linayi.util.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/common/picture")
public class PictureController {

    @RequestMapping("/showPicture.do")
    public void showPicture(String imagePath, HttpServletResponse response){
            File file = null;
            FileInputStream fis = null;
            ServletOutputStream outStream = null;
            try {
                if(imagePath != null && !imagePath.equals("")){
                    String path = imagePath.replaceAll("\\*", "/");
                    file = new File(path);
                }
                outStream = response.getOutputStream();// 得到向客户端输出二进制数据的对象
                fis = new FileInputStream(file); // 以byte流的方式打开文件
                // 读数据
                byte data[] = new byte[1000];
                while (fis.read(data) > 0) {
                    outStream.write(data);
                }
                fis.close();
                response.setContentType("image/*"); // 设置返回的文件类型
                outStream.write(data); // 输出数据
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (outStream != null){
                    try {
                        outStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    @RequestMapping("/upload.do")
    @ResponseBody
    public Object savePicture(HttpServletRequest request){
        InputStream inputStream = null;
        String type = request.getHeader("fileType");
        String datePath = DateUtil.date2String(new Date(), "yyyy/MM/dd/HH");
        String fileName = UUID.randomUUID().toString();
        String result = datePath + "/" + fileName + "." + type;
        ResponseData responseData = new ResponseData();
        File file;
        try {
            inputStream = request.getInputStream();
            File imageFile = new File(PropertiesUtil.getValueByKey(ConstantUtil.IMAGE_PATH));
            File srcfile = new File(imageFile + "/" + datePath);
            if (!srcfile.exists()){
                srcfile.mkdirs();
            }
            file = new File(srcfile + "/" + fileName + "." + type);
            FileOutputStream outputStream = new FileOutputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = inputStream.read(bytes)) > 0){
                bos.write(bytes,0,read);
            }
            byte[] byteArray = bos.toByteArray();
            outputStream.write(byteArray);
            bos.flush();
            outputStream.flush();
            bos.close();
            outputStream.close();
            if(!file.exists()){
                throw new IOException("File upload failed!");
            }
            responseData.setData(result);
            responseData.setRespCode("S");
        } catch (IOException e) {
            e.printStackTrace();
            responseData.setRespCode("F");
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return JSON.toJSONString(responseData);
    }
}
