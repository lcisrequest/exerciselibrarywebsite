package com.example.lcdemo.modular.admin.service.impl;

import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.config.properties.HiguProperties;
import com.example.lcdemo.modular.admin.dao.ImgMapper;
import com.example.lcdemo.modular.admin.dao.UserInfoMapper;
import com.example.lcdemo.modular.admin.dto.Base64DTO;
import com.example.lcdemo.modular.admin.model.Img;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.admin.service.UserInfoService;
import com.itspeed.higu.base.tips.SuccessTip;
import com.itspeed.higu.base.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 用户注册
     *
     * @param userInfo
     * @return
     */
    @Override
    public boolean register(UserInfo userInfo) {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        UserInfo user = new UserInfo();
        user.setUsername(username);
        user = userInfoMapper.selectOne(user);
        if (user != null) {
            throw new LcException(LcExceptionEnum.HAS_REGISTER);
        }
        user = new UserInfo();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreateTime(DateUtil.getTime());
        user.setLastLoginTime(DateUtil.getTime());
        return userInfoMapper.insert(user) > 0;
    }


    /**
     * 更改用户头像
     * @param img
     * @param userId
     */
    @Override
    public void updateUserImg(String img,int userId){
        UserInfo userInfo = userInfoMapper.selectById(userId);
        userInfo.setUserimg(img);
        userInfoMapper.updateById(userInfo);
    }

}
