package com.example.lcdemo.modular.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.lcdemo.modular.admin.dto.UserTestDTO;
import com.example.lcdemo.modular.admin.model.Subject;
import com.example.lcdemo.modular.admin.model.UserTest;

import java.util.List;
import java.util.Map;

public interface UserTestService {
    List<Map<String, Object>> getAOrderTest(int userId, int testNum, String testType);

    Subject getARandomSubject(String testType);

    List<Integer> getAllMyOrderTest(int userId);

    JSONObject submitTest(UserTestDTO userTestDTO, int userId);

    JSONObject submitMock(int testId, String answers, int userId);

    List<Map<String, Object>> getARandomTest(int testNum, String testType);

    void addToErrorSubject(int subjectId, int userId);

    List<Map<String, Object>> getAllMyErrorSubject(String courseType, int userId, int page, int limit);

    void deleteError(int subjectId, int userId);

    JSONObject submitErrorSubject(UserTestDTO userTestDTO, int userId);

    List<Map<String, Object>> getAllUserTest(String problemType, int page, int limit);

    int getTestNum(String problemType);

    List<UserTest> selectMyUserTest(String problemType, int page, int limit, int userId);

    Integer selectMyUserTestCount(String problemType, int userId);

    List<Map<String, Object>> getRankForTest(int testId);

    List<Map<String, Object>> getTodayRankForTest(int testId);

    Integer getAllMyErrorSubjectNum(String courseType, int userId);

    JSONObject getMyTestNum(String problemType,String testType, int userId);

    UserTest getUserTestById(int userTestId);

    int getRightNumByTestId(int userTestId);

    Integer peek(int userId, int subjectId);

    void ICanPeek(int userId);

    JSONArray getAllRank();

    JSONArray getTodayAllRank();
}
