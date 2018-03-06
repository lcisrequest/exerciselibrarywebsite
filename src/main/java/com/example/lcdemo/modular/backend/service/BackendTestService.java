package com.example.lcdemo.modular.backend.service;

import com.example.lcdemo.modular.admin.model.Subject;
import com.example.lcdemo.modular.admin.model.Test;

import java.util.List;
import java.util.Map;

public interface BackendTestService {
    boolean addSubject(Subject subject);

    boolean updateSubject(Subject subject);

    boolean deleteSubject(int subjectId);

    boolean addTest(Test test);

    boolean updateTest(Test test);

    boolean deleteTest(int testId);

    Map<String, Object> getSubjectById(int subjectId);

    List<Map<String, Object>> getSubject(String courseType, int page, int limit);

    int getSubjectNum(String courseType);

    List<Map<String, Object>> getTest(String courseType, int page, int limit);

    int getTestNum(String courseType);
}
