package com.example.lcdemo.modular.controller;

import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.admin.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userTest")
public class UserTestController extends BaseController {
    @Autowired
    UserTestService userTestService;

    /**
     * 生成一套指定类型的顺序练习
     * @param testNum
     * @param testType
     * @return
     */
    @RequestMapping("/getOrderTest")
    public ResponseEntity getAOrderTest(int testNum,String testType){
        List<Map<String,Object>> list = userTestService.getAOrderTest(getUserId(),testNum,testType);
        return ResponseEntity.ok(SuccessTip.create(list,"请求成功"));
    }
}
