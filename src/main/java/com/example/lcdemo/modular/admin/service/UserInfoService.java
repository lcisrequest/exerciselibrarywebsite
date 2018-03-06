package com.example.lcdemo.modular.admin.service;

import com.example.lcdemo.modular.admin.model.UserInfo;

public interface UserInfoService {
    boolean register(UserInfo userInfo);

    void updateUserImg(String img,int userId);
}
