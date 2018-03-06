package com.example.lcdemo.modular.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.admin.model.Subject;
import com.example.lcdemo.modular.admin.model.Test;
import com.example.lcdemo.modular.backend.service.BackendTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin")
public class BackendTestController extends BaseController {
    @Autowired
    BackendTestService backendTestService;

    /**
     * 新增题目
     *
     * @param subject
     * @return
     */
    @RequestMapping("/addSubject")
    public ResponseEntity addSubject(@RequestBody Subject subject) {
        if (backendTestService.addSubject(subject)) {
            return ResponseEntity.ok(SuccessTip.create("添加成功"));
        } else {
            return ResponseEntity.ok(SuccessTip.create("添加失败"));
        }
    }

    /**
     * 修改题目
     *
     * @param subject
     * @return
     */
    @RequestMapping("/updateSubject")
    public ResponseEntity updateSubject(@RequestBody Subject subject) {
        if (backendTestService.updateSubject(subject)) {
            return ResponseEntity.ok(SuccessTip.create("修改成功"));
        } else {
            return ResponseEntity.ok(SuccessTip.create("修改失败"));
        }
    }

    /**
     * 删除题目
     *
     * @param subjectId
     * @return
     */
    @RequestMapping("/deleteSubject")
    public ResponseEntity deleteSubject(int subjectId) {
        if (backendTestService.deleteSubject(subjectId)) {
            return ResponseEntity.ok(SuccessTip.create("删除成功"));
        } else {
            return ResponseEntity.ok(SuccessTip.create("删除失败"));
        }
    }

    /**
     * 根据题目id获取题目详情
     *
     * @param subjectId
     * @return
     */
    @RequestMapping("/getSubjectById")
    public ResponseEntity getSubjectById(int subjectId) {
        Map<String, Object> map = backendTestService.getSubjectById(subjectId);
        return ResponseEntity.ok(SuccessTip.create(map, "获取成功"));
    }

    /**
     * 分页获取指定类型的题目
     *
     * @param courseType
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getSubject")
    public ResponseEntity getSubject(String courseType, int page, int limit) {
        List<Map<String, Object>> list = backendTestService.getSubject(courseType, page, limit);
        int count = backendTestService.getSubjectNum(courseType);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("count", count);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "获取成功"));
    }


    /**
     * 新增模拟练习
     *
     * @param test
     * @return
     */
    @RequestMapping("/addTest")
    public ResponseEntity addTest(@RequestBody Test test) {
        if (backendTestService.addTest(test)) {
            return ResponseEntity.ok(SuccessTip.create("添加成功"));
        } else {
            return ResponseEntity.ok(SuccessTip.create("添加失败"));
        }
    }


    /**
     * 修改模拟练习
     *
     * @param test
     * @return
     */
    @RequestMapping("/updateTest")
    public ResponseEntity updateTest(@RequestBody Test test) {
        if (backendTestService.updateTest(test)) {
            return ResponseEntity.ok(SuccessTip.create("修改成功"));
        } else {
            return ResponseEntity.ok(SuccessTip.create("修改失败"));
        }
    }

    /**
     * 删除模拟练习
     *
     * @param testId
     * @return
     */
    @RequestMapping("/deleteTest")
    public ResponseEntity deleteTest(int testId) {
        if (backendTestService.deleteTest(testId)) {
            return ResponseEntity.ok(SuccessTip.create("删除成功"));
        } else {
            return ResponseEntity.ok(SuccessTip.create("删除失败"));
        }
    }

    /**
     * 分页获取指定类型的模拟练习
     *
     * @param courseType
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getTest")
    public ResponseEntity getTest(String courseType, int page, int limit) {
        List<Map<String, Object>> list = backendTestService.getTest(courseType, page, limit);
        int count = backendTestService.getTestNum(courseType);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("count", count);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "获取成功"));
    }

}
