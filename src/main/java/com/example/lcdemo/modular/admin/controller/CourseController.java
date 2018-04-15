package com.example.lcdemo.modular.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.admin.model.Course;
import com.example.lcdemo.modular.admin.service.CourseService;
import com.example.lcdemo.modular.backend.model.Knowledge;
import com.example.lcdemo.modular.backend.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {
    @Autowired
    CourseService courseService;
    @Autowired
    KnowledgeService knowledgeService;

    /**
     * 获取所有课程
     *
     * @return
     */
    @RequestMapping("/getAllCourse")
    public ResponseEntity getAllCourse() {
        List<Course> list = courseService.getAllCourse();
        return ResponseEntity.ok(SuccessTip.create(list, "请求成功"));
    }

    /**
     * 分页获取指定类型的考试大纲或课本知识
     *
     * @param type
     * @param problemType
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getKnowledge")
    public ResponseEntity getKnowledge(@RequestParam(value = "type", required = false, defaultValue = "0") int type,
                                       String problemType,
                                       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                       @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<Knowledge> list = knowledgeService.getKnowledge(type, problemType, page, limit);
        int count = knowledgeService.getKnowledgeNum(type, problemType);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("count", count);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }

    /**
     * 根据知识点id获取知识点
     *
     * @param knowledgeId
     * @return
     */
    @RequestMapping("/getKnowledgeById")
    public ResponseEntity getKnowledgeById(@RequestParam(value = "knowledgeId", required = false, defaultValue = "0") int knowledgeId) {
        Knowledge knowledge = knowledgeService.getKnowledgeById(knowledgeId);
        return ResponseEntity.ok(SuccessTip.create(knowledge, "请求成功"));
    }
}
