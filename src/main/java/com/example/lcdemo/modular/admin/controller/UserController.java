package com.example.lcdemo.modular.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.base.util.Base64Util;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.config.properties.HiguProperties;
import com.example.lcdemo.modular.admin.dao.ImgMapper;
import com.example.lcdemo.modular.admin.dto.Base64DTO;
import com.example.lcdemo.modular.admin.model.*;
import com.example.lcdemo.modular.admin.service.ClockService;
import com.example.lcdemo.modular.admin.service.HomepageService;
import com.example.lcdemo.modular.admin.service.MessageService;
import com.example.lcdemo.modular.admin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
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
    @Autowired
    HomepageService homepageService;
    @Autowired
    ClockService clockService;
    @Autowired
    MessageService messageService;

    /**
     * 用户注册
     *
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/register")
    public ResponseEntity Register(@RequestBody UserInfo userInfo) {
        if (userInfoService.register(userInfo)) {
            return ResponseEntity.ok(SuccessTip.create("注册成功"));
        } else {
            return ResponseEntity.ok(SuccessTip.create("注册失败"));
        }
    }

    /**
     * 更改用户头像
     *
     * @param img
     * @return
     */
    @RequestMapping("/updateUserImg")
    public ResponseEntity updateUserImg(String img) {
        userInfoService.updateUserImg(img, getUserId());
        return ResponseEntity.ok(SuccessTip.create("修改成功"));
    }

    /**
     * 上传图片
     *
     * @param base64DTO
     * @return
     */
    @RequestMapping(value = "/img64", method = RequestMethod.POST)
    public ResponseEntity upload(@RequestBody Base64DTO base64DTO) {
        String pictureName = UUID.randomUUID().toString() + ".jpg";
        String base64 = base64DTO.getBase64();
        File file = new File(higuProperties.getFileUploadPath());
        if (!file.exists()) {
            file.mkdirs();
            System.out.println("路径无，创建成功");
        }
        try {
            Base64Util.decodeBase64ToImage(base64.substring(base64.indexOf(",") + 1), higuProperties.getFileUploadPath(), pictureName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(base64.substring(base64.indexOf(",") + 1));
            return ResponseEntity.ok(SuccessTip.create(e.toString(), "上传出错"));
        }
        String pictureUrl = higuProperties.getImgUrlPath() + "/" + pictureName;
        Img img = new Img();
        img.setPath(pictureUrl);
        img.setTime(DateUtil.getTime());
        imgMapper.insert(img);
        return ResponseEntity.ok(SuccessTip.create(pictureUrl, "上传成功"));
    }


    /**
     * 获取所有首页信息
     *
     * @return
     */
    @RequestMapping("/getAllHomepage")
    public ResponseEntity getAllHomepage() {
        List<Homepage> list = homepageService.getAllHomepage();
        return ResponseEntity.ok(SuccessTip.create(list, "请求成功"));
    }


    /**
     * 修改用户头像
     *
     * @param nickName
     * @return
     */
    @RequestMapping("/updateNickName")
    public ResponseEntity updateNickName(String nickName) {
        userInfoService.updateNickName(nickName, getUserId());
        return ResponseEntity.ok(SuccessTip.create("修改成功"));
    }


    /**
     * 获取个人中心的信息
     *
     * @return
     */
    @RequestMapping("/getAllMyNum")
    public ResponseEntity getAllMyNumInfo() {
        JSONObject jsonObject = userInfoService.getAllMyNumInfo(getUserId());
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }


    /**
     * 用户打卡
     *
     * @param content
     * @return
     */
    @RequestMapping("/userClock")
    public ResponseEntity UserClock(@RequestParam(value = "content", required = false, defaultValue = "") String content) {
        String str = clockService.clockUser(content, getUserId());
        return ResponseEntity.ok(SuccessTip.create(str));
    }

    /**
     * 获取我的打卡记录
     *
     * @return
     */
    @RequestMapping("/getMyClockRecord")
    public ResponseEntity getMyClockRecord() {
        List<Clock> list = clockService.getMyClockRecord(getUserId());
        return ResponseEntity.ok(SuccessTip.create(list, "请求成功"));
    }

    /**
     * 修改昵称
     *
     * @param nickName
     * @return
     */
    @RequestMapping("/updateNickname")
    public ResponseEntity updateNickname(String nickName) {
        userInfoService.updateNickName(nickName, getUserId());
        return ResponseEntity.ok(SuccessTip.create("修改成功"));
    }

    /**
     * 发送验证码
     *
     * @param tel
     * @return
     */
    @RequestMapping("/sendMessage")
    public ResponseEntity sendMessage(String tel) {
        messageService.sendMessage(tel, getUserId());
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }


    /**
     * 判断验证码
     *
     * @param varCode
     * @param
     * @return
     */
    @RequestMapping("/judgeVarCode")
    public ResponseEntity judgeVarCode(String varCode) {
        boolean isRight = messageService.VarCodeIsTrue(getUserId(), varCode);
        if (!isRight) {
            throw new LcException(LcExceptionEnum.VAR_CODE_OVERTIME); //验证码超时
        }
        return ResponseEntity.ok(SuccessTip.create(isRight, "请求成功"));
    }

    /**
     * 修改密码
     *
     * @param newPassword
     * @return
     */
    @RequestMapping("/updatePassword")
    public ResponseEntity updatePassword(String newPassword) {
        userInfoService.updatePassword(newPassword, getUserId());
        return ResponseEntity.ok(SuccessTip.create("修改成功"));
    }
}
