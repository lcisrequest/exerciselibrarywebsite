package com.example.lcdemo.modular.controller;

import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class testController extends BaseController {

    @RequestMapping("/hello")
    public ResponseEntity say() {
        System.out.println("Hello springboot");
        return ResponseEntity.ok(SuccessTip.create(getUserId(), "获取成功"));
    }
}
