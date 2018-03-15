package com.example.lcdemo.modular.admin.controller;


import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.admin.model.Course;
import com.example.lcdemo.modular.admin.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {
    @Autowired
    CourseService courseService;


    /**
     * 获取所有课程
     * @return
     */
    @RequestMapping("/getAllCourse")
    public ResponseEntity getAllCourse() {
        List<Course> list = courseService.getAllCourse();
        return ResponseEntity.ok(SuccessTip.create(list, "请求成功"));
    }
}
