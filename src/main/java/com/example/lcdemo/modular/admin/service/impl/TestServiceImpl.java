package com.example.lcdemo.modular.admin.service.impl;


import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.modular.admin.dao.SubjectMapper;
import com.example.lcdemo.modular.admin.dao.TestMapper;
import com.example.lcdemo.modular.admin.model.Subject;
import com.example.lcdemo.modular.admin.model.Test;
import com.example.lcdemo.modular.admin.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    TestMapper testMapper;
    @Autowired
    SubjectMapper subjectMapper;
    /**
     * 得到所有测试信息
     */
    public void getAllTest(){}


    /**
     * 根据测试id获取测试信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getTestById(int id){
        Test test = testMapper.selectById(id); //根据id找到测试信息
        if(test==null){
            throw new LcException(LcExceptionEnum.TEST_IS_NOT_EXIST);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        if(test.getImg()==null||"".equals(test.getImg())){
            map.put("img","");
        }else{
            map.put("img",test.getImg());
        }
        map.put("testName",test.getTestName());
        map.put("testContent",test.getTestContent());
        map.put("testTime",test.getTestTime());
        map.put("testFraction",test.getTestFraction());
        map.put("testDifficulty",test.getTestDifficulty());
        String subjects = test.getTestSubject();
        if(subjects!=null||"".equals(subjects)){
            String allSubject[] = subjects.split(","); //拆分题目id
            List< Map<String,Object>> list = new ArrayList<>();
            for (String subjectId :allSubject) {
                Subject subject = subjectMapper.selectById(subjectId); //根据题目id得到题目信息
                Map<String,Object> mapSubject = subject.getMap();
                list.add(mapSubject);
            }
            map.put("testSubject",list);
        }else {
            map.put("testSubject","");
        }
        return map;
    }

}
