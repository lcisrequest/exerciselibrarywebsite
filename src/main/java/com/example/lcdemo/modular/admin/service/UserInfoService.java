package com.example.lcdemo.modular.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.example.lcdemo.modular.admin.model.UserInfo;

public interface UserInfoService {
    boolean register(UserInfo userInfo);

    void updateUserImg(String img,int userId);

    void updateNickName(String nickName, int userId);

    JSONObject getAllMyNumInfo(int userId);
}
