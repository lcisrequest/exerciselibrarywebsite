package com.example.lcdemo.modular.admin.service;

import com.example.lcdemo.modular.admin.model.Subject;

import java.util.List;
import java.util.Map;

public interface UserTestService {
    List<Map<String, Object>> getAOrderTest(int userId, int testNum, String testType);

    Subject getARandomSubject(String testType);

    List<Integer> getAllMyOrderTest(int userId);
}
