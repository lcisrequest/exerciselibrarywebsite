package com.example.lcdemo.modular.backend.service;

import com.alibaba.fastjson.JSONArray;
import com.example.lcdemo.modular.admin.model.UserInfo;

import java.util.List;

public interface BackendUserService {

    void insertUser(UserInfo userInfo);

    void banUser(int userId);

    void updateUser(UserInfo userInfo);

    UserInfo getUserById(int userId);

    List<UserInfo> getAllUser(int page, int limit);

    List<String> getDays();

    int getTodayRegisterNum(String date);

    int getTodayClockNum(String date);

    int getTodayTestNum(String date);

    int getTodayForumsNum(String date);

    JSONArray getAllMonthRegisterNum();

    JSONArray getAllMonthTestNum();

    JSONArray getAllMonthClockNum();

    JSONArray getAllMonthForumsNum();

    Integer getAllUserCount();
}
