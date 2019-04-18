package com.linayi.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

public  class OSSManageUtil {

    private final static String endpoint;
    private final static String accessKeyId;
    private final static String accessKeySecret ;
    private final static String bucketName;
    private final static String accessUrl;
    private final static String fileFolder;

    static {
        endpoint = PropertiesUtil.getValueByKey("endpoint");
        accessKeyId = PropertiesUtil.getValueByKey("accessKeyId");
        accessKeySecret = PropertiesUtil.getValueByKey("accessKeySecret");
        bucketName = PropertiesUtil.getValueByKey("bucketName");
        accessUrl = PropertiesUtil.getValueByKey("accessUrl");
        fileFolder = PropertiesUtil.getValueByKey("fileFolder");

    }
    /**
     * 上传OSS服务器文件 @Title: uploadFile
     * remotePath @param oss服务器二级目录
     *  @throws Exception 设定文件 @return String
     * 返回类型 @throws
     */
    public static String uploadFile(MultipartFile file) throws Exception {
        long size = file.getSize();
        if (size <= 0){
            return "";
        }
        String fileName = file.getOriginalFilename();
        InputStream fileContent = file.getInputStream();
        //随机名处理
        String remotePath = fileFolder;
        String datePath = DateUtil.date2String(new Date(), "yyyy/MM/dd/HH");
        String name = UUID.randomUUID().toString();
        fileName = datePath + "/" + name + fileName.substring(fileName.lastIndexOf("."));
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId,
                accessKeySecret);
        // 定义二级目录
        String remoteFilePath = remotePath.replaceAll("\\*", "/") + "/";
        // 创建上传Object的Metadata
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileContent.available());
        objectMetadata.setContentEncoding("utf-8");
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(contentType(fileName.substring(fileName.lastIndexOf("."))));
        objectMetadata.setContentDisposition("inline;filename=" + fileName);
        // 上传文件
        ossClient.putObject(bucketName, remoteFilePath + fileName, fileContent, objectMetadata);
        // 关闭OSSClient
        ossClient.shutdown();
        // 关闭io流
        fileContent.close();
        return remoteFilePath + fileName;
    }

    /**
     * 处理展示
     * @param fileUrl
     * @return
     */
    public static String toShow(String fileUrl){
        if(fileUrl == null && "".equals(fileUrl)){
            return "";
        }
        return accessUrl+ "/" + fileUrl;
    }

    // 下载文件
    @SuppressWarnings("unused")
    public static void downloadFile(String source, String filename)
            throws OSSException, ClientException, IOException {
        // 初始化OSSClient
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId,
                accessKeySecret);
        OSSObject object = ossClient.getObject(bucketName, source);
//        // 获取ObjectMeta
        ObjectMetadata meta = object.getObjectMetadata();

        // 获取Object的输入流
        InputStream objectContent = object.getObjectContent();

        ObjectMetadata objectData = ossClient.getObject(new GetObjectRequest(bucketName, source),
                new File(filename));
        // 关闭数据流
        objectContent.close();

    }

    /**
     * 根据key删除OSS服务器上的文件 @Title: deleteFile @Description: @param @param
     * ossConfigure @param @param filePath 设定文件 @return void 返回类型 @throws
     * @throws IOException
     */
    public static void deleteFile( String filePath) throws IOException {
        // 加载配置文件，初始化OSSClient
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId,
                accessKeySecret);
//        filePath=filePath.substring(45);
        ossClient.deleteObject(bucketName, filePath);

    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType @Version1.0
     *
     * @param FilenameExtension
     *            文件后缀
     * @return String
     */
    public static String contentType(String FilenameExtension) {
        if (FilenameExtension.equals(".BMP") || FilenameExtension.equals(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equals(".GIF") || FilenameExtension.equals(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equals(".JPEG") || FilenameExtension.equals(".jpeg") || FilenameExtension.equals(".JPG")
                || FilenameExtension.equals(".jpg") || FilenameExtension.equals(".PNG")
                || FilenameExtension.equals(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equals(".HTML") || FilenameExtension.equals(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equals(".TXT") || FilenameExtension.equals(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equals(".VSD") || FilenameExtension.equals(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equals(".PPTX") || FilenameExtension.equals(".pptx") || FilenameExtension.equals(".PPT")
                || FilenameExtension.equals(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equals(".DOCX") || FilenameExtension.equals(".docx") || FilenameExtension.equals(".DOC")
                || FilenameExtension.equals(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equals(".XML") || FilenameExtension.equals(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equals(".apk") || FilenameExtension.equals(".APK")) {
            return "application/octet-stream";
        }
        return "text/html";
    }
}
