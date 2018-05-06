package com.example.lcdemo.modular.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.modular.admin.dao.FollowMapper;
import com.example.lcdemo.modular.admin.dao.UserInfoMapper;
import com.example.lcdemo.modular.admin.dao.UserSubjectnumMapper;
import com.example.lcdemo.modular.admin.model.*;
import com.example.lcdemo.modular.admin.service.*;
import com.example.lcdemo.modular.backend.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    UserSubjectnumMapper userSubjectnumMapper;
    @Autowired
    FollowMapper followMapper;
    @Autowired
    ForumsService forumsService;
    @Autowired
    CommentService commentService;

    /**
     * 用户注册
     *
     * @param userInfo
     * @return
     */
    @Override
    public boolean register(UserInfo userInfo) {
        if (userInfo.getTelphone() == null) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        UserInfo user = new UserInfo();
        user.setUsername(username);
        user = userInfoMapper.selectOne(user);
        if (user != null) {
            throw new LcException(LcExceptionEnum.HAS_REGISTER);//用户名不可以重复
        }
        user = new UserInfo();
        user.setUsername(username);
        user.setPassword(password);
        //    user.setNickname(DateUtil.getTime() + UUID.randomUUID()); //随机初始昵称
        user.setNickname(username);
        user.setCreateTime(DateUtil.getTime());
        user.setLastLoginTime(DateUtil.getTime());
        user.setGold(0);
        user.setLevel(1);
        user.setXp(0);
        user.setBan(0);
        return userInfoMapper.insert(user) > 0; //新增用户
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
        if (userInfo == null) { //判断用户是否存在
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
        int clockNum = clockService.getMyClockNum(userId);                      //获取该用户的打卡天数
        int collectKnowledgeNum = knowledgeService.getAllMyCollectKnowledgeCount(0, userId); //获取我的收藏的课程的数量
        boolean isClock = clockService.todayIsClock(userId);//获取我今天是否已经打卡
        int todaySubjectNun = userTestService.getTodayMySubjectNum(userId);  //获取今日用户做题数量
        int todayCommentAllNum = commentService.getTodayMyCommentNum(userId);   //获取今日用户评论的总数,其中包括圈子中讨论的数量
        int userSubjectNum = this.getTheUserSubjectNum(userId);     //获取我的所有正确题目数量
        JSONObject followNum = this.getFollowNum(userId);
        int IFollowNum = followNum.getInteger("iFollowNum");    //获取我关注的数量
        int followMeNum = followNum.getInteger("followMeNum");  //获取关注我的数量
        int forumsNum = forumsService.getUserForumsNum(userId);     //获取我发布的讨论数量
        UserInfo userInfo = userInfoMapper.selectById(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userInfo", userInfo);
        jsonObject.put("todaySubjectNum", todaySubjectNun);
        jsonObject.put("todayCommentAllNum", todayCommentAllNum);
        jsonObject.put("collectSubjectNum", collectSubjectNum);
        jsonObject.put("errorSubjectNum", errorSubjectNum);
        jsonObject.put("testNum", testNum);
        jsonObject.put("clockNum", clockNum);
        jsonObject.put("collectKnowledgeNum", collectKnowledgeNum);
        jsonObject.put("isClock", isClock);
        jsonObject.put("rightSubjectNum", userSubjectNum);
        jsonObject.put("iFollowNum", IFollowNum);
        jsonObject.put("followMeNum", followMeNum);
        jsonObject.put("forumsNum", forumsNum);
        return jsonObject;
    }

    /**
     * 获取该用户的正确题目数量
     *
     * @param userId
     * @return
     */
    @Override
    public int getTheUserSubjectNum(int userId) {
        Wrapper<UserSubjectnum> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        List<UserSubjectnum> listSubjectNum = userSubjectnumMapper.selectList(wrapper);
        if (listSubjectNum.size() == 0) {
            return 0;
        }
        int num = 0;
        for (UserSubjectnum usn : listSubjectNum) {
            num = num + usn.getSubjectNum();
        }
        return num;
    }

    /**
     * 得到该用户的首页个人信息
     *
     * @param userId
     * @param myId
     * @return
     */
    @Override
    public JSONObject getOtherUserInfo(int userId, int myId) {
        int collectSubjectNum = collectService.getAllMyCollectNum("all", userId); //获取该用户的所有收藏的题目的数量
        int collectKnowledgeNum = knowledgeService.getAllMyCollectKnowledgeCount(0, userId); //获取该用户的收藏的课程的数量
        int errorSubjectNum = userTestService.getAllMyErrorSubjectNum("all", userId); //获取该用户的所有的错题数量
        int testNum = userTestService.selectMyUserTestCount("all", userId);  //获取该用户的所有练习数量
        int clockNum = clockService.getMyClockNum(userId);                      //获取该用户的打卡天数
        int userSubjectNum = this.getTheUserSubjectNum(userId);     //获取我的所有正确题目数量
        int todaySubjectNun = userTestService.getTodayMySubjectNum(userId);  //获取今日用户做题数量
        int todayCommentAllNum = commentService.getTodayMyCommentNum(userId);   //获取今日用户评论的总数,其中包括圈子中讨论的数量
        JSONObject followNum = this.getFollowNum(userId);
        int IFollowNum = followNum.getInteger("iFollowNum");    //获取该用户关注的数量
        int followMeNum = followNum.getInteger("followMeNum");  //获取关注该用户的数量
        int forumsNum = forumsService.getUserForumsNum(userId);     //获取该用户发布的讨论数量
        boolean isFollowTheUser = this.IFollowTheUser(userId, myId); //获取我是否关注过该用户
        UserInfo userInfo = userInfoMapper.selectById(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userInfo", userInfo);
        jsonObject.put("todaySubjectNum", todaySubjectNun);
        jsonObject.put("todayCommentAllNum", todayCommentAllNum);
        jsonObject.put("collectSubjectNum", collectSubjectNum);
        jsonObject.put("errorSubjectNum", errorSubjectNum);
        jsonObject.put("testNum", testNum);
        jsonObject.put("clockNum", clockNum);
        jsonObject.put("collectKnowledgeNum", collectKnowledgeNum);
        jsonObject.put("rightSubjectNum", userSubjectNum);
        jsonObject.put("iFollowNum", IFollowNum);
        jsonObject.put("followMeNum", followMeNum);
        jsonObject.put("forumsNum", forumsNum);
        jsonObject.put("isFollowTheUser", isFollowTheUser);
        return jsonObject;
    }

    /**
     * 获取该用户的打卡记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<Clock> getTheUserClockRecord(int userId) {
        return clockService.getMyClockRecord(userId);
    }


    /**
     * 修改密码
     *
     * @param newPassword
     * @param telphone
     */
    @Override
    public void updatePassword(String newPassword, String telphone) {
        UserInfo userinfo = new UserInfo();
        userinfo.setTelphone(telphone);
        UserInfo user = userInfoMapper.selectOne(userinfo);
        if (user == null) {
            throw new LcException(LcExceptionEnum.TEL_USER_NOT_EXIST);
        }
        user.setPassword(newPassword);
        userInfoMapper.updateById(user);    //更改密码
    }

    /**
     * 关注用户或取消关注用户
     *
     * @param followUser
     * @param userId
     */
    @Override
    public void followUser(int followUser, int userId) {
        Follow follow = new Follow();
        follow.setUserId(userId);
        follow.setFollowUser(followUser);
        Follow flag = followMapper.selectOne(follow); //判断我是否有关注过他
        if (flag == null) {             //若没有关注过他
            follow.setCreateTime(DateUtil.getTime());
            followMapper.insert(follow); //则关注他
        } else {                      //若关注过了他
            followMapper.deleteById(flag);//则取消关注
        }
    }

    /**
     * 获取我关注的数量和关注我的数量
     *
     * @param userId
     * @return
     */
    @Override
    public JSONObject getFollowNum(int userId) {
        Wrapper<Follow> ifollow = new EntityWrapper<>();
        ifollow.eq("user_id", userId);
        int IToOther = followMapper.selectCount(ifollow); //获取我关注的数量
        Wrapper<Follow> followMe = new EntityWrapper<>();
        followMe.eq("follow_user", userId);
        int otherToMe = followMapper.selectCount(followMe);//获取关注我的数量
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("iFollowNum", IToOther);
        jsonObject.put("followMeNum", otherToMe);
        return jsonObject;
    }

    /**
     * 获取我关注的所有用户
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getIFollowUser(int userId) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("user_id", userId);
        List<Follow> listFollow = followMapper.selectList(wrapper); //得到所有我关注的人
        List<Map<String, Object>> list = new ArrayList<>();
        for (Follow f : listFollow) {                    //遍历获得用户头像昵称
            int theUserId = f.getFollowUser();
            UserInfo userInfo = userInfoMapper.selectById(theUserId);
            Map<String, Object> map = new HashMap<>();
            map.put("userimg", userInfo.getUserimg());
            map.put("nickname", userInfo.getNickname());
            map.put("createTime", f.getCreateTime());
            list.add(map);
        }
        return list;
    }


    /**
     * 获取所有关注我的用户
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getFollowMeUser(int userId) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("follow_user", userId);
        List<Follow> listFollow = followMapper.selectList(wrapper); //得到所有关注我的人
        List<Map<String, Object>> list = new ArrayList<>();
        for (Follow f : listFollow) {                    //遍历获得用户头像昵称
            int theUserId = f.getUserId();
            UserInfo userInfo = userInfoMapper.selectById(theUserId);
            Map<String, Object> map = new HashMap<>();
            map.put("userimg", userInfo.getUserimg());
            map.put("nickname", userInfo.getNickname());
            map.put("createTime", f.getCreateTime());
            list.add(map);
        }
        return list;
    }

    /**
     * 判断我是否有关注过该用户 是true，否false
     *
     * @param userId
     * @param myId
     * @return
     */
    @Override
    public boolean IFollowTheUser(int userId, int myId) {
        Follow follow = new Follow();
        follow.setUserId(myId);
        follow.setFollowUser(userId);
        follow = followMapper.selectOne(follow); //判断我是否有关注过该用户
        if (follow == null) {
            return false;
        } else {
            return true;
        }
    }

}
