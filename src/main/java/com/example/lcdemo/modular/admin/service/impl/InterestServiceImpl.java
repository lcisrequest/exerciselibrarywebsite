package com.example.lcdemo.modular.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.modular.admin.dao.CourseMapper;
import com.example.lcdemo.modular.admin.dao.InterestMapper;
import com.example.lcdemo.modular.admin.model.Course;
import com.example.lcdemo.modular.admin.model.Interest;
import com.example.lcdemo.modular.admin.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestServiceImpl implements InterestService {
    @Autowired
    InterestMapper interestMapper;
    @Autowired
    CourseMapper courseMapper;

    /**
     * 修改我的兴趣
     *
     * @param userId
     * @param interestStr
     */
    @Override
    public void changeInterest(int userId, String interestStr) {
        Wrapper<Interest> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        List<Interest> list = interestMapper.selectList(wrapper);
        if (list.size() > 0) {
            for (Interest i : list) {                         //删除过去的所有兴趣
                interestMapper.deleteById(i.getId());
                String problemType = i.getInterestType();
                Course course = new Course();
                course.setName(problemType);
                course = courseMapper.selectOne(course);        //得到该课程
                if (course == null) {
                    throw new LcException(LcExceptionEnum.INTEREST_NOT_EXIST);
                }
                course.setPeopleNum(course.getPeopleNum() - 1); //用户兴趣数量-1
                courseMapper.updateById(course);                //更新课程信息
            }
        }
        if (interestStr != null && !"".equals(interestStr)) {
            String[] interests = interestStr.split(",");    //得到所有兴趣
            for (String interesting : interests) {                //添加所有兴趣
                Interest in = new Interest();
                in.setInterestType(interesting);
                in.setUserId(userId);
                interestMapper.insert(in);
                Course course = new Course();
                course.setName(interesting);
                course = courseMapper.selectOne(course);        //得到该课程
                if (course == null) {
                    throw new LcException(LcExceptionEnum.INTEREST_NOT_EXIST);
                }
                course.setPeopleNum(course.getPeopleNum() + 1); //用户兴趣数量+1
                courseMapper.updateById(course);                //更新课程信息
            }
        }
    }

    /**
     * 是否拥有兴趣
     *
     * @param userId
     * @return
     */
    @Override
    public boolean havaInterest(int userId) {
        Wrapper<Interest> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return interestMapper.selectCount(wrapper) > 0;
    }

    /**
     * 获取我的所有兴趣
     *
     * @param userId
     * @return
     */
    @Override
    public List<Interest> getAllMyInterest(int userId) {
        Wrapper<Interest> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        List<Interest> list = interestMapper.selectList(wrapper);
        return list;
    }
}
