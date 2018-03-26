package com.example.lcdemo.modular.backend.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.backend.service.BackendForumsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class BackendForumsController extends BaseController {
    @Autowired
    BackendForumsService backendForumsService;

    /**
     * 后台分页获取所有讨论
     *
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getAllForums")
    public ResponseEntity getAllForums(int page, int limit) {
        List<Map<String, Object>> list = backendForumsService.getAllForums(page, limit);
        int count = backendForumsService.getAllForumsNum();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("count", count);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "获取成功"));
    }

    /**
     * 置顶讨论
     *
     * @param forumsId
     * @return
     */
    @RequestMapping("/topForums")
    public ResponseEntity topForums(int forumsId) {
        backendForumsService.topForums(forumsId);
        return ResponseEntity.ok(SuccessTip.create("置顶成功"));
    }

    /**
     * 取消置顶讨论
     *
     * @param forumsId
     * @return
     */
    @RequestMapping("/notTopForums")
    public ResponseEntity notTopForums(int forumsId) {
        backendForumsService.notTopForums(forumsId);
        return ResponseEntity.ok(SuccessTip.create("取消置顶成功"));
    }
}
