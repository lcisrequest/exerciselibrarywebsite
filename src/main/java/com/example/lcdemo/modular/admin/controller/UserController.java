package com.example.lcdemo.modular.admin.controller;


import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.config.properties.HiguProperties;
import com.example.lcdemo.modular.admin.dao.ImgMapper;
import com.example.lcdemo.modular.admin.dto.Base64DTO;
import com.example.lcdemo.modular.admin.model.Img;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.admin.service.UserInfoService;
import com.itspeed.higu.base.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    private HiguProperties higuProperties;
    @Autowired
    ImgMapper imgMapper;

    /**
     * 用户注册
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/register")
    public ResponseEntity Register(@RequestBody UserInfo userInfo){
        if(userInfoService.register(userInfo)){
            return ResponseEntity.ok(SuccessTip.create("注册成功"));
        }else{
            return ResponseEntity.ok(SuccessTip.create("注册失败"));
        }
    }

    /**
     * 更改用户头像
     * @param img
     * @return
     */
    @RequestMapping("/updateUserImg")
    public ResponseEntity updateUserImg(String img){
        userInfoService.updateUserImg(img,getUserId());
        return ResponseEntity.ok(SuccessTip.create("修改成功"));
    }

    /**
     * 上传图片
     * @param base64DTO
     * @return
     */
    @RequestMapping(value = "/img64", method = RequestMethod.POST)
    public ResponseEntity upload(@RequestBody Base64DTO base64DTO) {
        String pictureName = UUID.randomUUID().toString() + ".jpg";
        String base64 = base64DTO.getBase64();
        File file = new File( higuProperties.getFileUploadPath());
        if(!file.exists()){
            file.mkdirs();
            System.out.println("路径无，创建成功");
        }
        try {
            Base64Util.decodeBase64ToImage(base64.substring(base64.indexOf(",") + 1), higuProperties.getFileUploadPath(), pictureName);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(base64.substring(base64.indexOf(",") + 1));
            return ResponseEntity.ok(com.itspeed.higu.base.tips.SuccessTip.create(e.toString(), "上传出错"));
        }
        String pictureUrl = higuProperties.getImgUrlPath() + "/" + pictureName;
        Img img = new Img();
        img.setPath(pictureUrl);
        img.setTime(DateUtil.getTime());
        imgMapper.insert(img);
        return ResponseEntity.ok(com.itspeed.higu.base.tips.SuccessTip.create(pictureUrl, "上传成功"));
    }

}
