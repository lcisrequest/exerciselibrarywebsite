package com.example.lcdemo.modular.admin.service;

import com.example.lcdemo.modular.admin.model.Course;

import java.util.List;

public interface CourseService {
    List<Course> getAllCourse();

    void addCourse(Course course);

    void deleteCourse(int courseId);
}
