package com.atguigu.gmall.manage.util;

import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public class PmsUploadUtil {

    public static String uploadImage(MultipartFile multipartFile) {
        String imgUrl = "";
        String tracker = PmsUploadUtil.class.getResource("/tracker.conf").getPath();
        try {
            ClientGlobal.init(tracker);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerClient.getTrackerServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StorageClient storageClient = new StorageClient(trackerServer,null);

        try {
            byte[] bytes = multipartFile.getBytes();//获得上传的二进制对象
            String originalFilename =  multipartFile.getOriginalFilename();
            String extName = StringUtils.substringAfterLast(originalFilename, ".");
            String[] uploadInfos = storageClient.upload_file(bytes, extName, null);
            imgUrl = "http://192.168.242.130";
            for (String uploadInfo : uploadInfos){
                imgUrl += "/" + uploadInfo;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return imgUrl;
    }
}
