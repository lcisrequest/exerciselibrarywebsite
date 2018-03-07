package com.example.lcdemo.modular.backend.controller;


import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.backend.model.Admin;
import com.example.lcdemo.modular.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Autowired
    AdminService adminService;

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    @RequestMapping("/addAdmin")
    public ResponseEntity addAdmin(@RequestBody Admin admin) {
        adminService.addNewAdmin(admin);
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }
}
