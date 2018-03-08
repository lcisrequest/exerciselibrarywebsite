package com.example.lcdemo.modular.admin.controller;

import com.alibaba.fastjson.JSONObject;
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
     *
     * @param testNum
     * @param problemType
     * @return
     */
    @RequestMapping("/getOrderTest")
    public ResponseEntity getAOrderTest(int testNum, String problemType) {
        List<Map<String, Object>> list = userTestService.getAOrderTest(getUserId(), testNum, problemType);
        return ResponseEntity.ok(SuccessTip.create(list, "请求成功"));
    }

    /**
     * 生成一套指定类型的随机练习
     *
     * @param testNum
     * @param problemType
     * @return
     */
    @RequestMapping("/getRandomTest")
    public ResponseEntity getARandomTest(int testNum, String problemType) {
        List<Map<String, Object>> list = userTestService.getARandomTest(testNum, problemType);
        return ResponseEntity.ok(SuccessTip.create(list, "请求成功"));
    }

    /**
     * 提交练习(顺序练习和随机练习)
     *
     * @param userTestDTO
     * @return
     */
    @RequestMapping("/submitTest")
    public ResponseEntity submitTest(@RequestBody UserTestDTO userTestDTO) {
        List<Boolean> list = userTestService.submitTest(userTestDTO, getUserId());
        return ResponseEntity.ok(SuccessTip.create(list, "请求成功"));
    }

    /**
     * 提交练习(模拟练习)
     *
     * @param testId
     * @param answers
     * @return
     */
    @RequestMapping("/submitMock")
    public ResponseEntity submitMock(int testId, String answers) {
        JSONObject jsonObject = userTestService.submitMock(testId, answers, getUserId());
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }

    /**
     * 分页获取我的指定类型的错题
     *
     * @param problemType
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getError")
    public ResponseEntity getMyError(String problemType, int page, int limit) {
        List<Map<String, Object>> list = userTestService.getAllMyErrorSubject(problemType, getUserId(), page, limit);
        return ResponseEntity.ok(SuccessTip.create(list, "请求成功"));
    }

    /**
     * 删除指定的错题
     *
     * @param subjectId
     * @return
     */
    @RequestMapping("/deleteError")
    public ResponseEntity deleteError(int subjectId) {
        userTestService.deleteError(subjectId, getUserId());
        return ResponseEntity.ok(SuccessTip.create("删除成功"));
    }

    /**
     * 提交错题练习
     *
     * @param userTestDTO
     * @return
     */
    @RequestMapping("/submitError")
    public ResponseEntity submitError(@RequestBody UserTestDTO userTestDTO) {
        List<Boolean> list = userTestService.submitErrorSubject(userTestDTO, getUserId());
        return ResponseEntity.ok(SuccessTip.create(list, "提交成功"));
    }

    /**
     * 分页获取指定类型的模拟练习
     *
     * @param problemType
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/selectTest")
    public ResponseEntity selectTest(String problemType, int page, int limit) {
        List<Map<String, Object>> list = userTestService.getAllUserTest(problemType, page, limit);
        int count = userTestService.getTestNum(problemType);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("count", count);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }
}
