package com.example.lcdemo.modular.backend.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.modular.admin.dao.SubjectMapper;
import com.example.lcdemo.modular.admin.dao.TestMapper;
import com.example.lcdemo.modular.admin.model.Subject;
import com.example.lcdemo.modular.admin.model.Test;
import com.example.lcdemo.modular.backend.service.BackendTestService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BackendTestServiceImpl implements BackendTestService {
    @Autowired
    SubjectMapper subjectMapper;
    @Autowired
    TestMapper testMapper;

    /**
     * 新增题目
     *
     * @param subject
     * @return
     */
    @Override
    public boolean addSubject(Subject subject) {
        int num = subjectMapper.insert(subject);
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改题目
     *
     * @param subject
     * @return
     */
    @Override
    public boolean updateSubject(Subject subject) {
        int num = subjectMapper.updateById(subject);
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除题目
     *
     * @param subjectId
     * @return
     */
    @Override
    public boolean deleteSubject(int subjectId) {
        int num = subjectMapper.deleteById(subjectId);
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据题目id获取题目详情
     *
     * @param subjectId
     * @return
     */
    @Override
    public Map<String, Object> getSubjectById(int subjectId) {
        Subject subject = subjectMapper.selectById(subjectId);
        if (subject == null) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        Map<String, Object> map = subject.getMap();
        return map;
    }

    /**
     * 分页获取指定类型的题目
     *
     * @param courseType
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> getSubject(String courseType, int page, int limit) {
        Wrapper<Subject> wrapper = new EntityWrapper<>();
        if (!courseType.equals("all")) {                      //当类型为all时，为不指定类型
            wrapper.eq("problem_type", courseType);                       //指定题目类型
        }
        RowBounds rowBounds = new RowBounds((page - 1) * limit, limit);//分页
        List<Subject> list = subjectMapper.selectPage(rowBounds, wrapper);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (Subject s : list) {
            Map<String, Object> map = s.getMap();
            listMap.add(map);
        }
        return listMap;
    }

    /**
     * 获取指定类型的题目的总数
     *
     * @param courseType
     * @return
     */
    @Override
    public int getSubjectNum(String courseType) {
        Wrapper<Subject> wrapper = new EntityWrapper<>();
        if (!courseType.equals("all")) {                      //当类型为all时，为不指定类型
            wrapper.eq("problem_type", courseType);                       //指定题目类型
        }
        int count = subjectMapper.selectCount(wrapper);
        return count;
    }

    /**
     * 新增模拟练习
     *
     * @param test
     * @return
     */
    @Override
    public boolean addTest(Test test) {
        int num = testMapper.insert(test);
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改模拟练习
     *
     * @param test
     * @return
     */
    @Override
    public boolean updateTest(Test test) {
        int num = testMapper.updateById(test);
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除模拟练习
     *
     * @param testId
     * @return
     */
    @Override
    public boolean deleteTest(int testId) {
        int num = testMapper.deleteById(testId);
        if (num > 0) {
            return true;
        } else {
            return false;
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
    @Override
    public List<Map<String, Object>> getTest(String courseType, int page, int limit) {
        Wrapper<Test> wrapper = new EntityWrapper<>();
        if (!courseType.equals("all")) {                      //当类型为all时，为不指定类型
            wrapper.eq("problem_type", courseType);                       //指定题目类型
        }
        RowBounds rowBounds = new RowBounds((page - 1) * limit, limit);//分页
        List<Test> list = testMapper.selectPage(rowBounds, wrapper);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (Test t : list) {
            Map<String, Object> map = t.getMap();
            String sId = t.getTestSubject();
            String[] subjectIds = sId.split(",");//获取到该练习的所有题目id
            List<Map<String, Object>> listSubject = new ArrayList<>();
            for (String id : subjectIds) {
                Map<String, Object> subjectMap = this.getSubjectById(Integer.valueOf(id));//根据题目id取得题目信息
                listSubject.add(subjectMap);
            }
            map.put("subjectList", listSubject);
            listMap.add(map);
        }
        return listMap;
    }

    /**
     * 获取指定类型的模拟练习数量
     *
     * @param courseType
     * @return
     */
    @Override
    public int getTestNum(String courseType) {
        Wrapper<Test> wrapper = new EntityWrapper<>();
        if (!courseType.equals("all")) {                      //当类型为all时，为不指定类型
            wrapper.eq("problem_type", courseType);                       //指定题目类型
        }
        int count = testMapper.selectCount(wrapper);
        return count;
    }
}
