package com.example.lcdemo.modular.admin.service.impl;

import com.example.lcdemo.base.util.MD5Util;
import com.example.lcdemo.modular.admin.dao.UserInfoMapper;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.admin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;

    public void login(UserInfo userInfo){
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        UserInfo user = new UserInfo();
        user.setUsername(username);
        user.setPassword(password);
        user = userInfoMapper.selectOne(user);
        if(user!=null){
            System.out.println("登陆成功");
        }
    }
}
