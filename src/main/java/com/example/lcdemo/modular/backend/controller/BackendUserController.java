package com.example.lcdemo.modular.backend.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.backend.service.BackendUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class BackendUserController extends BaseController {
    @Autowired
    BackendUserService backendUserService;


    /**
     * 后台新增用户
     *
     * @param userInfo
     * @return
     */
    @RequestMapping("/insertUser")
    public ResponseEntity insertUser(@RequestBody UserInfo userInfo) {
        backendUserService.insertUser(userInfo);
        return ResponseEntity.ok(SuccessTip.create("添加成功"));
    }

    /**
     * 后台禁用用户或者取消禁用
     *
     * @param userId
     * @return
     */
    @RequestMapping("/banUser")
    public ResponseEntity banUser(int userId) {
        backendUserService.banUser(userId);
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }

    /**
     * 后台更改用户信息
     *
     * @param userInfo
     * @return
     */
    @RequestMapping("/updateUser")
    public ResponseEntity updateUser(@RequestBody UserInfo userInfo) {
        backendUserService.updateUser(userInfo);
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }


    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/getUserById")
    public ResponseEntity getUserById(int userId) {
        UserInfo userInfo = backendUserService.getUserById(userId);
        return ResponseEntity.ok(SuccessTip.create(userInfo, "请求成功"));
    }

    /**
     * 分页获取所有用户信息
     *
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getAllUser")
    public ResponseEntity getAllUser(int page, int limit) {
        List<UserInfo> list = backendUserService.getAllUser(page, limit);
        return ResponseEntity.ok(SuccessTip.create(list, "请求成功"));
    }


    /**
     * 获取所有图表
     *
     * @return
     */
    @RequestMapping("/getAllTable")
    public ResponseEntity getAllTable() {
        JSONArray register = backendUserService.getAllMonthRegisterNum();
        JSONArray test = backendUserService.getAllMonthTestNum();
        JSONArray clock = backendUserService.getAllMonthClockNum();
        JSONArray forums = backendUserService.getAllMonthForumsNum();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("register", register);
        jsonObject.put("test", test);
        jsonObject.put("clock", clock);
        jsonObject.put("forums", forums);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }
}
