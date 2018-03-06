package com.example.lcdemo.modular.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.modular.admin.dao.SubjectMapper;
import com.example.lcdemo.modular.admin.dao.TestMapper;
import com.example.lcdemo.modular.admin.dao.UserTestMapper;
import com.example.lcdemo.modular.admin.dto.UserTestDTO;
import com.example.lcdemo.modular.admin.model.Subject;
import com.example.lcdemo.modular.admin.model.Test;
import com.example.lcdemo.modular.admin.model.UserTest;
import com.example.lcdemo.modular.admin.service.SubjectService;
import com.example.lcdemo.modular.admin.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class UserTestServiceImpl implements UserTestService {
    @Autowired
    UserTestMapper userTestMapper;
    @Autowired
    SubjectMapper subjectMapper;
    @Autowired
    SubjectService subjectService;
    @Autowired
    TestMapper testMapper;

    /**
     * 获取一套顺序测试
     */
    @Override
    public List<Map<String, Object>> getAOrderTest(int userId, int testNum, String testType) {
        if (testNum == 0 || testType == null || "".equals(testType)) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        Wrapper<Subject> wrapper = new EntityWrapper<>();
        wrapper.eq("problem_type", testType);
        List<Subject> list = subjectMapper.selectList(wrapper);
        List<Integer> orderSubIds = this.getAllMyOrderTest(userId);
        List<Map<String, Object>> returnList = new ArrayList<>();
        int num = 0;
        if (list.size() == 0) {
            throw new LcException(LcExceptionEnum.SUBJECCT_IS_NULL);
        }
        for (Subject sub : list) {    //遍历所有习题
            if (num < testNum) {
                int subId = sub.getId(); //得到当前习题的id
                boolean isDone = false;
                for (int id : orderSubIds) {
                    if (id == subId) {        //判断该习题我是否做过了
                        isDone = true;
                    }
                }
                if (!isDone) {                //若该习题没做过
                    returnList.add(sub.getMap());    //则加入返回
                    num++;                  //习题数量+1
                }
            }
        }
        while (num < testNum) {  //若返回习题数量不够
            int notEnoughNum = testNum - num;
            Subject subject = this.getARandomSubject(testType); //随机获得一道该类型的题目
            returnList.add(subject.getMap());
            num++;
        }
        return returnList;
    }


    /**
     * 生成一套指定类型的随机练习
     *
     * @param testNum
     * @param testType
     * @return
     */
    @Override
    public List<Map<String, Object>> getARandomTest(int testNum, String testType) {
        if (testNum == 0 || testType == null || "".equals(testType)) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        List<Map<String, Object>> list = new ArrayList<>();
        int num = 0;
        while (num < testNum) {
            num++;
            Subject subject = this.getARandomSubject(testType);
            list.add(subject.getMap());
        }
        return list;
    }

    /**
     * 获取指定类型的一个随机题目
     *
     * @param testType
     * @return
     */
    @Override
    public Subject getARandomSubject(String testType) {
        Wrapper<Subject> wrapper = new EntityWrapper<>();
        wrapper.eq("problem_type", testType);
        List<Subject> list = subjectMapper.selectList(wrapper); //得到该类型的所有题目
        if (list.size() == 0) {
            return new Subject();
        }
        int listnum = list.size();      //得到该类型的的题目的数量
        Random rand = new Random();
        int randNum = rand.nextInt(listnum); //生成一个0-题目数量的随机数
        return list.get(randNum);
    }

    /**
     * 获取我做过的所有顺序练习的习题的id
     *
     * @param userId
     * @return
     */
    @Override
    public List<Integer> getAllMyOrderTest(int userId) {
        Wrapper<UserTest> wrapper = new EntityWrapper<>();
        wrapper.eq("test_type", "order"); //规定练习类型为顺序练习
        wrapper.eq("user_id", userId);
        List<UserTest> list = userTestMapper.selectList(wrapper);
        List<Integer> listInt = new ArrayList<>();
        if (list.size() == 0) {
            return listInt;
        }
        for (UserTest ut : list) {
            String subjectStr = ut.getSubjectId();
            String subjectIds[] = subjectStr.split(",");
            for (String subjectId : subjectIds) {
                listInt.add(Integer.valueOf(subjectId));
            }
        }
        return listInt;
    }


    /**
     * 提交练习
     *
     * @param userTestDTO
     * @param userId
     */
    @Override
    public List<Boolean> submitTest(UserTestDTO userTestDTO, int userId) {
        UserTest userTest = new UserTest();
        String subjectIds = userTestDTO.getSubjectIds();
        String ids[] = subjectIds.split(","); //获取题目数组
        int idNum = ids.length;
        String answers = userTestDTO.getAnswers();
        String answersNum[] = answers.split(",");//获取答案数组
        int aNum = answersNum.length;
        if (aNum != idNum) {
            throw new LcException(LcExceptionEnum.ANSWER_NUM_IS_WRONG);//若答案数与习题数不同，则抛出异常
        }
        userTest.setUserId(userId);
        userTest.setCourseType(userTestDTO.getCourseType());
        userTest.setTestType(userTestDTO.getTestType());
        userTest.setSubjectId(userTestDTO.getSubjectIds());
        userTest.setTestResult(userTestDTO.getAnswers());
        userTest.setSubjectNum(idNum);
        userTest.setStartTime(DateUtil.getTime());
        userTestMapper.insert(userTest);            //新增练习记录
        List<Boolean> listbool = new ArrayList<>();
        for (int x = 0; x < idNum; x++) {
            int subjectid = Integer.valueOf(ids[x]); //获取习题id
            int answer = Integer.valueOf(answersNum[x]);//获取答案
            listbool.add(subjectService.subjectIsRight(subjectid, answer));//判断答案是否正确
        }
        return listbool;
    }

    /**
     * 提交练习（顺序练习和随机练习）
     *
     * @param testId
     * @param answers
     * @param userId
     * @return
     */
    @Override
    public List<Boolean> submitMock(int testId, String answers, int userId) {
        Test test = testMapper.selectById(testId);
        String subjectIds = test.getTestSubject();
        String ids[] = subjectIds.split(","); //获取题目数组
        int idNum = ids.length;
        String answersNum[] = answers.split(",");//获取答案数组
        int aNum = answersNum.length;
        if (aNum != idNum) {
            throw new LcException(LcExceptionEnum.ANSWER_NUM_IS_WRONG);//若答案数与习题数不同，则抛出异常
        }
        UserTest userTest = new UserTest();
        userTest.setUserId(userId);
        userTest.setCourseType(test.getTestResult());
        userTest.setTestType("mock");
        userTest.setSubjectId(subjectIds);
        userTest.setTestResult(answers);
        userTest.setSubjectNum(idNum);
        userTest.setStartTime(DateUtil.getTime());
        userTest.setTestId(testId);
        userTestMapper.insert(userTest);            //新增练习记录
        List<Boolean> listbool = new ArrayList<>();
        for (int x = 0; x < idNum; x++) {
            int subjectid = Integer.valueOf(ids[x]); //获取习题id
            int answer = Integer.valueOf(answersNum[x]);//获取答案
            listbool.add(subjectService.subjectIsRight(subjectid, answer));//判断答案是否正确
        }
        return listbool;
    }
}
