package com.example.lcdemo.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by tsy
 */
@Component
@ConfigurationProperties(prefix = HiguProperties.PREFIX)
public class HiguProperties {

    public static final String PREFIX = "higu";

    private String fileUploadPath;

    public static final String avatarPath = "avatar/";
    public static final String bannerPath = "banner/";
    public static final String bankPath = "bank/";
    public static final String qrPath = "qr/";

    private String imgUrlPath;


    public String getFileUploadPath() {
        if (!fileUploadPath.endsWith(File.separator)) {
            fileUploadPath = fileUploadPath + File.separator;
        }
        //判断目录存不存在,不存在得加上
        File file = new File(fileUploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return fileUploadPath;
//        //如果没有写文件上传路径,保存到临时目录
//        if (isEmpty(fileUploadPath)) {
//            return getTempPath();
//        } else {
//            //判断有没有结尾符,没有得加上
//            if (!fileUploadPath.endsWith(File.separator)) {
//                fileUploadPath = fileUploadPath + File.separator;
//            }
//            if (haveCreatePath == false) {
//                File file = new File(fileUploadPath);
//                file.mkdirs();
//                haveCreatePath = true;
//            }
//            return fileUploadPath;
//        }
    }


    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    public String getImgUrlPath() {
        return imgUrlPath;
    }

    public void setImgUrlPath(String imgUrlPath) {
        this.imgUrlPath = imgUrlPath;
    }

    private String  agreementPath;

    public String getAgreementPath() {
        return agreementPath;
    }

    public void setAgreementPath(String agreementPath) {
        this.agreementPath = agreementPath;
    }
}


