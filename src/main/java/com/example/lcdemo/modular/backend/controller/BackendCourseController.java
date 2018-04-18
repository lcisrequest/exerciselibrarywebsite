package com.example.lcdemo.modular.backend.controller;


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
@RequestMapping("/admin")
public class BackendCourseController extends BaseController {
    @Autowired
    CourseService courseService;


    /**
     * 添加新课程
     *
     * @param courseName
     * @return
     */
    @RequestMapping("/addCourse")
    public ResponseEntity addCourse(String courseName) {
        Course course = new Course();
        course.setName(courseName);
        courseService.addCourse(course);
        return ResponseEntity.ok(SuccessTip.create("添加成功"));
    }


    /**
     * 删除课程 （慎用）
     *
     * @param courseId
     * @return
     */
    @RequestMapping("/deleteCourse")
    public ResponseEntity deleteCourse(int courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok(SuccessTip.create("删除成功"));
    }


    /**
     * 后台获取所有课程
     *
     * @return
     */
    @RequestMapping("/getAllCourse")
    public ResponseEntity getAllCourse() {
        List<Course> list = courseService.getAllCourse();
        return ResponseEntity.ok(SuccessTip.create(list, "请求成功"));
    }

}
