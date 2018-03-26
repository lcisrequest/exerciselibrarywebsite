package com.example.lcdemo.modular.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.modular.admin.dao.CourseMapper;
import com.example.lcdemo.modular.admin.model.Course;
import com.example.lcdemo.modular.admin.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseMapper courseMapper;

    /**
     * 获取所有的课程类型
     *
     * @return
     */
    @Override
    public List<Course> getAllCourse() {
        Wrapper<Course> wrapper = new EntityWrapper<>();
        List<Course> list = courseMapper.selectList(wrapper);
        return list;
    }

    /**
     * 添加新课程类型
     *
     * @param course
     */
    @Override
    public void addCourse(Course course) {
        String name = course.getName();
        Course c = new Course();
        c.setName(name);
        c = courseMapper.selectOne(c);      //判断该课程是否存在
        if (c != null) {
            throw new LcException(LcExceptionEnum.COURSE_IS_EXIST);
        }
        course.setSubjectNum(0);
        course.setTestNum(0);
        course.setPeopleNum(0);
        course.setKnowledgeNum(0);
        courseMapper.insert(course); //新增课程类型
    }

    /**
     * 删除课程
     *
     * @param courseId
     */
    @Override
    public void deleteCourse(int courseId) {
        Course course = courseMapper.selectById(courseId);
        if (course == null) {                                       //判断该课程是否存在
            throw new LcException(LcExceptionEnum.COURSE_NOT_EXIST);
        }
        courseMapper.deleteById(course); //删除课程
    }
}
