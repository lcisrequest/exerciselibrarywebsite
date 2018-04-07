package com.example.lcdemo.modular.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.example.lcdemo.modular.admin.model.Clock;
import com.example.lcdemo.modular.admin.model.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserInfoService {
    boolean register(UserInfo userInfo);

    void updateUserImg(String img,int userId);

    void updateNickName(String nickName, int userId);

    JSONObject getAllMyNumInfo(int userId);

    void updatePassword(String newPassword,int userId);

    void followUser(int followUser,int userId);

    JSONObject getFollowNum(int userId);

    List<Map<String, Object>> getIFollowUser(int userId);

    List<Map<String, Object>> getFollowMeUser(int userId);

    List<Clock> getTheUserClockRecord(int userId);

    JSONObject getOtherUserInfo(int userId, int myId);

    boolean IFollowTheUser(int userId, int myId);

    int getTheUserSubjectNum(int userId);
}
