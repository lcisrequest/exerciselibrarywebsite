package com.example.lcdemo.modular.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.modular.admin.dao.CourseMapper;
import com.example.lcdemo.modular.admin.dao.InterestMapper;
import com.example.lcdemo.modular.admin.dao.TestMapper;
import com.example.lcdemo.modular.admin.model.Course;
import com.example.lcdemo.modular.admin.model.Interest;
import com.example.lcdemo.modular.admin.model.Test;
import com.example.lcdemo.modular.admin.service.InterestService;
import com.example.lcdemo.modular.admin.service.UserTestService;
import com.example.lcdemo.modular.backend.dao.KnowledgeMapper;
import com.example.lcdemo.modular.backend.model.Knowledge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InterestServiceImpl implements InterestService {
    @Autowired
    InterestMapper interestMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    KnowledgeMapper knowledgeMapper;
    @Autowired
    TestMapper testMapper;
    @Autowired
    UserTestService userTestService;

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
        return interestMapper.selectCount(wrapper) > 0; //若大于0则拥有兴趣
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


    /**
     * 根据兴趣随机获得指定数量的知识点
     *
     * @param userId
     * @param num
     * @return
     */
    @Override
    public List<Knowledge> getHomePageKnowledge(int userId, int num) {
        List<Interest> list = this.getAllMyInterest(userId);
        List<Knowledge> listKnowledge = new ArrayList<>();
        if (list.size() == 0) { //若没有设置兴趣
            for (int x = 0; x < num; x++) {
                Knowledge k = this.getAKnowledgeByInterest("all"); //获取所有课程类型
                listKnowledge.add(k);
            }
        } else {
            int listNum = list.size();
            Random rand = new Random();
            for (int x = 0; x < num; x++) {
                int randNum = rand.nextInt(listNum); //生成一个0-num的随机数
                Interest interest = list.get(randNum);
                Knowledge knowledge = this.getAKnowledgeByInterest(interest.getInterestType());//根据兴趣随机获取
                listKnowledge.add(knowledge);
            }
        }
        return listKnowledge;
    }

    /**
     * 根据兴趣随机获得指定数量的模拟练习
     *
     * @param userId
     * @param num
     * @return
     */
    @Override
    public List<Map<String, Object>> getHomePageTest(int userId, int num) {
        if (num == 0) {
            throw new LcException(LcExceptionEnum.PARAM_NULL);
        }
        List<Interest> list = this.getAllMyInterest(userId);
        List<Test> listKnowledge = new ArrayList<>();
        if (list.size() == 0) { //若没有设置兴趣
            for (int x = 0; x < num; x++) {
                Test t = this.getATestByInterest("all");
                listKnowledge.add(t);
            }
        } else {
            int listNum = list.size();
            Random rand = new Random();
            for (int x = 0; x < num; x++) {
                int randNum = rand.nextInt(listNum); //生成一个0-num的随机数
                Interest interest = list.get(randNum);
                Test test = this.getATestByInterest(interest.getInterestType());//根据兴趣随机获取
                listKnowledge.add(test);
            }
        }
        List<Map<String, Object>> listTest = new ArrayList<>();
        for (Test t : listKnowledge) {
            Map<String, Object> mapTest = t.makeMap();
            String testSubject = t.getTestSubject();
            String[] testSubjects = testSubject.split(",");     //获取练习中的所有题目id
            List<Map<String, Object>> listMap = new ArrayList<>();
            for (String subjectIdStr : testSubjects) {
                int subjectId = Integer.valueOf(subjectIdStr);
                Map<String, Object> mapSubject = userTestService.getSubjectById(subjectId, userId);  //根据题目id获取题目详情
                listMap.add(mapSubject);
            }
            mapTest.put("subjectList", listMap);
            listTest.add(mapTest);
        }
        return listTest;
    }


    /**
     * 生成一个指定课程的知识点
     *
     * @param problemType
     * @return
     */
    @Override
    public Knowledge getAKnowledgeByInterest(String problemType) {
        Wrapper<Knowledge> wrapper = new EntityWrapper<>();
        if (!problemType.equals("all")) {
            wrapper.eq("problem_type", problemType); //指定课程类型
        }
        List<Knowledge> list = knowledgeMapper.selectList(wrapper); //获取该类型的所有知识点
        int num = list.size();
        if (num == 0) {
            return null;
        }
        Random rand = new Random();
        int randNum = rand.nextInt(num); //生成一个0-num的随机数
        return list.get(randNum);
    }

    /**
     * 生成一个指定课程的模拟练习
     *
     * @param problemType
     * @return
     */
    @Override
    public Test getATestByInterest(String problemType) {
        Wrapper<Test> wrapper = new EntityWrapper<>();
        if (!problemType.equals("all")) {
            wrapper.eq("problem_type", problemType);
        }
        List<Test> list = testMapper.selectList(wrapper); //获取该类型的所有模拟测试
        int num = list.size();
        Random rand = new Random();
        int randNum = rand.nextInt(num); //生成一个0-num的随机数
        return list.get(randNum);
    }
}
