package com.example.lcdemo.modular.admin.service;

import com.example.lcdemo.modular.admin.model.Interest;
import com.example.lcdemo.modular.admin.model.Test;
import com.example.lcdemo.modular.backend.model.Knowledge;

import java.util.List;
import java.util.Map;

public interface InterestService {
    void changeInterest(int userId, String interestStr);

    boolean havaInterest(int userId);

    List<Interest> getAllMyInterest(int userId);

    Knowledge getAKnowledgeByInterest(String problemType);

    Test getATestByInterest(String problemType);

    List<Knowledge> getHomePageKnowledge(int userId, int num);

    List<Map<String, Object>> getHomePageTest(int userId, int num);
}
