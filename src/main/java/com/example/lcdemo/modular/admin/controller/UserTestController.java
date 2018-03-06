package com.example.lcdemo.modular.admin.controller;

import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.admin.dto.UserTestDTO;
import com.example.lcdemo.modular.admin.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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
     * @param courseType
     * @return
     */
    @RequestMapping("/getOrderTest")
    public ResponseEntity getAOrderTest(int testNum,String courseType){
        List<Map<String,Object>> list = userTestService.getAOrderTest(getUserId(),testNum,courseType);
        return ResponseEntity.ok(SuccessTip.create(list,"请求成功"));
    }

    /**
     * 生成一套指定类型的随机练习
     * @param testNum
     * @param courseType
     * @return
     */
    @RequestMapping("/getRandomTest")
    public ResponseEntity getARandomTest(int testNum, String courseType){
        List<Map<String,Object>> list = userTestService.getARandomTest(testNum,courseType);
        return ResponseEntity.ok(SuccessTip.create(list,"请求成功"));
    }

    /**
     * 提交练习(顺序练习和随机练习)
     * @param userTestDTO
     * @return
     */
    @RequestMapping("/submitTest")
    public ResponseEntity submitTest(@RequestBody UserTestDTO userTestDTO){
        List<Boolean> list = userTestService.submitTest(userTestDTO,getUserId());
        return ResponseEntity.ok(SuccessTip.create(list,"请求成功"));
    }

    /**
     * 提交练习(模拟练习)
     * @param testId
     * @param answers
     * @return
     */
    @RequestMapping("/submitMock")
    public ResponseEntity submitMock(int testId,String answers){
        List<Boolean> list = userTestService.submitMock(testId,answers,getUserId());
        return ResponseEntity.ok(SuccessTip.create(list,"请求成功"));
    }
}
