package com.example.lcdemo.modular.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.modular.admin.dao.SubjectMapper;
import com.example.lcdemo.modular.admin.dao.UserTestMapper;
import com.example.lcdemo.modular.admin.model.Subject;
import com.example.lcdemo.modular.admin.model.UserTest;
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

    /**
     * 获取一套顺序测试
     */
    @Override
    public List<Map<String, Object>> getAOrderTest(int userId, int testNum, String testType) {
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

}
