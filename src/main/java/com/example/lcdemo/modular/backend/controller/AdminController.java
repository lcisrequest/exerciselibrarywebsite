package com.example.lcdemo.modular.backend.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.backend.model.Admin;
import com.example.lcdemo.modular.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Autowired
    AdminService adminService;

    /**
     * 添加管理员
     *
     * @param admin
     * @return
     */
    @RequestMapping("/addAdmin")
    public ResponseEntity addAdmin(@RequestBody Admin admin) {
        adminService.addNewAdmin(admin, getUserId());
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }

    /**
     * 分页搜索管理员信息
     *
     * @param page
     * @param limit
     * @param username
     * @return
     */
    @RequestMapping("/selectAdmin")
    public ResponseEntity selectAdmin(int page, int limit, @RequestParam(value = "username", required = false, defaultValue = "") String username) {
        List<Map<String, Object>> list = adminService.getAllAdmin(getUserId(), page, limit, username);
        int count = adminService.getAdminCount(username);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("count", count);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }

    /**
     * 删除指定的管理员
     *
     * @param adminId
     * @return
     */
    @RequestMapping("/deleteAdmin")
    public ResponseEntity deleteAdmin(int adminId) {
        adminService.deleteAdmin(adminId, getUserId());
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }

    /**
     * 更新管理员信息
     *
     * @param admin
     * @return
     */
    @RequestMapping("/updateAdmin")
    public ResponseEntity updateAdmin(@RequestBody Admin admin) {
        adminService.updateAdmin(admin, getUserId());
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }
}
