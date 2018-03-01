package com.example.lcdemo.modular.admin.service.impl;

import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.modular.admin.dao.UserInfoMapper;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.admin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 用户注册
     * @param userInfo
     * @return
     */
    @Override
    public boolean register(UserInfo userInfo){
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        UserInfo user = new UserInfo();
        user.setUsername(username);
        user = userInfoMapper.selectOne(user);
        if(user!=null){
            throw new LcException(LcExceptionEnum.HAS_REGISTER);
        }
        user = new UserInfo();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreateTime(DateUtil.getTime());
        user.setLastLoginTime(DateUtil.getTime());
        return userInfoMapper.insert(user)>0;
    }
}
