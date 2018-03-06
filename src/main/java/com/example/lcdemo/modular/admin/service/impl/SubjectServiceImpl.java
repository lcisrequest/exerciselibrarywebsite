package com.example.lcdemo.modular.admin.service.impl;


import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.modular.admin.dao.SubjectMapper;
import com.example.lcdemo.modular.admin.model.Subject;
import com.example.lcdemo.modular.admin.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectMapper subjectMapper;

    /**
     * 判断该习题答案是否正确
     */
    @Override
    public boolean subjectIsRight(int subjectId, int answerId) {
        Subject subject = subjectMapper.selectById(subjectId);
        if (subject == null) {
            throw new LcException(LcExceptionEnum.SUBJECT_IS_NOT_EXIST);
        }
        int answer = subject.getRightKey();
        if (answer == answerId) {
            return true;
        } else {
            return false;
        }
    }
}
