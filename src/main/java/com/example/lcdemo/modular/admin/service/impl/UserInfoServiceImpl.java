package com.example.lcdemo.modular.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.modular.admin.dao.UserInfoMapper;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.admin.service.ClockService;
import com.example.lcdemo.modular.admin.service.CollectService;
import com.example.lcdemo.modular.admin.service.UserInfoService;
import com.example.lcdemo.modular.admin.service.UserTestService;
import com.example.lcdemo.modular.backend.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserTestService userTestService;
    @Autowired
    CollectService collectService;
    @Autowired
    KnowledgeService knowledgeService;
    @Autowired
    ClockService clockService;

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
        user.setNickname(DateUtil.getTime() + UUID.randomUUID());
        user.setCreateTime(DateUtil.getTime());
        user.setLastLoginTime(DateUtil.getTime());
        return userInfoMapper.insert(user) > 0;
    }


    /**
     * 更改用户头像
     *
     * @param img
     * @param userId
     */
    @Override
    public void updateUserImg(String img, int userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        userInfo.setUserimg(img);
        userInfoMapper.updateById(userInfo);
    }

    /**
     * 修改昵称
     *
     * @param nickName
     * @param userId
     */
    @Override
    public void updateNickName(String nickName, int userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo = userInfoMapper.selectById(userId);
        if (userInfo == null) {
            throw new LcException(LcExceptionEnum.HAS_NOT_USER);
        }
        userInfo.setNickname(nickName);
        userInfoMapper.updateById(userInfo);
    }


    /**
     * 得到我的首页个人信息
     *
     * @param userId
     * @return
     */
    @Override
    public JSONObject getAllMyNumInfo(int userId) {
        int collectSubjectNum = collectService.getAllMyCollectNum("all", userId); //获取我的所有收藏的题目的数量
        int errorSubjectNum = userTestService.getAllMyErrorSubjectNum("all", userId); //获取我的所有的错题数量
        int testNum = userTestService.selectMyUserTestCount("all", userId);  //获取我的所有练习数量
        int collectKnowledgeNum = knowledgeService.getAllMyCollectKnowledgeCount(0, userId); //获取我的收藏的课程的数量
        boolean isClock = clockService.todayIsClock(userId);//获取我今天是否已经打卡
        UserInfo userInfo = userInfoMapper.selectById(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userInfo", userInfo);
        jsonObject.put("collectSubjectNum", collectSubjectNum);
        jsonObject.put("errorSubjectNum", errorSubjectNum);
        jsonObject.put("testNum", testNum);
        jsonObject.put("collectKnowledgeNum", collectKnowledgeNum);
        jsonObject.put("isClock", isClock);
        return jsonObject;
    }


}
