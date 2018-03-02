package com.example.lcdemo.modular.controller;


import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.admin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    UserInfoService userInfoService;

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



}
