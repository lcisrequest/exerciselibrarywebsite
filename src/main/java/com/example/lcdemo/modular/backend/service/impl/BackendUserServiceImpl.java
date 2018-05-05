package com.example.lcdemo.modular.backend.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.modular.admin.dao.ClockMapper;
import com.example.lcdemo.modular.admin.dao.ForumsMapper;
import com.example.lcdemo.modular.admin.dao.UserInfoMapper;
import com.example.lcdemo.modular.admin.dao.UserSubjectnumMapper;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.backend.service.BackendUserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BackendUserServiceImpl implements BackendUserService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserSubjectnumMapper userSubjectnumMapper;
    @Autowired
    ClockMapper clockMapper;
    @Autowired
    ForumsMapper forumsMapper;


    /**
     * 后台创建用户
     *
     * @param userInfo
     */
    @Override
    public void insertUser(UserInfo userInfo) {
        String telphone = userInfo.getTelphone();
        String username = userInfo.getUsername();
        if (telphone == null || "".equals(telphone) || username == null || "".equals(username)) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        UserInfo selecttel = new UserInfo();
        selecttel.setTelphone(telphone);
        UserInfo user = userInfoMapper.selectOne(selecttel);
        if (user != null) {                 //判断该手机号是否注册过
            throw new LcException(LcExceptionEnum.TEL_USER_IS_EXIST);
        }
        user = new UserInfo();
        user.setUsername(username);
        user = userInfoMapper.selectOne(user);
        if (user != null) {                     //判断该用户名是否注册过
            throw new LcException(LcExceptionEnum.HAS_REGISTER);
        }
        userInfo.setCreateTime(DateUtil.getTime()); //设置创建时间
        userInfoMapper.insert(userInfo);    //新增用户
    }

    /**
     * 后台禁用用户或取消禁用
     *
     * @param userId
     */
    @Override
    public void banUser(int userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if (userInfo == null) {
            throw new LcException(LcExceptionEnum.USER_NOT_EXIST);
        }
        if (userInfo.getBan() == 0) {
            userInfo.setBan(1);    // ban为1时，是禁用用户
        } else {
            userInfo.setBan(0);    //ban为0时，取消禁用
        }
        userInfoMapper.updateById(userInfo);
    }

    /**
     * 后台更新用户信息
     *
     * @param userInfo
     */
    @Override
    public void updateUser(UserInfo userInfo) {
        Integer userId = userInfo.getId();
        if (userId == null) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        UserInfo user = userInfoMapper.selectById(userId);
        if (user == null) {
            throw new LcException(LcExceptionEnum.USER_NOT_EXIST);
        }
        userInfoMapper.updateById(userInfo);
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfo getUserById(int userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if (userInfo == null) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        return userInfo;
    }


    /**
     * 分页获取所有用户信息
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<UserInfo> getAllUser(int page, int limit) {
        Wrapper<UserInfo> wrapper = new EntityWrapper<>();
        wrapper.orderBy("last_login_time", false);
        RowBounds rowBounds = new RowBounds((page - 1) * limit, limit);
        List<UserInfo> list = userInfoMapper.selectPage(rowBounds, wrapper);
        return list;
    }

    /**
     * 获取所有用人数
     * @return
     */
    @Override
    public Integer getAllUserCount(){
        Wrapper<UserInfo> wrapper = new EntityWrapper<>();
        return userInfoMapper.selectCount(wrapper);
    }

    /**
     * 获取三十天前到今天的所有日期
     */
    @Override
    public List<String> getDays() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String endDate = sdf.format(today);//当前日期
        //获取三十天前日期
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(today);
        List<String> listStr = new ArrayList<>();
        int num = -29;
        for (int x = 0; x < 30; x++) {
            theCa.setTime(today);
            theCa.add(theCa.DATE, num + x);//最后一个数字30可改，30天的意思
            Date start = theCa.getTime();
            String startDate = sdf.format(start);//三十天之前日期
            listStr.add(startDate);
        }
        return listStr;
    }

    /**
     * 获取指定日期注册的用户数量
     *
     * @param date
     * @return
     */
    @Override
    public int getTodayRegisterNum(String date) {
        String start = date + " 00:00:00";
        String end = date + " 23:59:59";
        int count = userInfoMapper.selectRegisterNum(start, end);
        return count;
    }

    /**
     * 获取指定日期用户练习数量
     *
     * @param date
     * @return
     */
    @Override
    public int getTodayTestNum(String date) {
        String start = date + " 00:00:00";
        String end = date + " 23:59:59";
        int count = userSubjectnumMapper.selectTodaySubjectNum(start, end);
        return count;
    }

    /**
     * 获取指定日期用户打卡数量
     *
     * @param date
     * @return
     */
    @Override
    public int getTodayClockNum(String date) {
        String start = date + " 00:00:00";
        String end = date + " 23:59:59";
        int count = clockMapper.selectTodayClockNum(start, end);
        return count;
    }

    /**
     * 获取指定日期用户讨论数量
     *
     * @param date
     * @return
     */
    @Override
    public int getTodayForumsNum(String date) {
        String start = date + " 00:00:00";
        String end = date + " 23:59:59";
        int count = forumsMapper.selectTodayForumsNum(start, end);
        return count;
    }


    /**
     * 获取三十天内 用户注册的数量走势图
     *
     * @return
     */
    @Override
    public JSONArray getAllMonthRegisterNum() {
        List<String> listDate = this.getDays();     //获取全部日期
        JSONArray jsonArray = new JSONArray();
        for (String theDate : listDate) {
            int count = this.getTodayRegisterNum(theDate);   //获取每日的数量
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", theDate);
            jsonObject.put("count", count);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * 获取三十天内 用户练习的数量走势图
     *
     * @return
     */
    @Override
    public JSONArray getAllMonthTestNum() {
        List<String> listDate = this.getDays();     //获取全部日期
        JSONArray jsonArray = new JSONArray();
        for (String theDate : listDate) {
            int count = this.getTodayTestNum(theDate);   //获取每日的数量
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", theDate);
            jsonObject.put("count", count);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * 获取三十天内 用户打卡的数量走势图
     *
     * @return
     */
    @Override
    public JSONArray getAllMonthClockNum() {
        List<String> listDate = this.getDays(); //获取全部日期
        JSONArray jsonArray = new JSONArray();
        for (String theDate : listDate) {
            int count = this.getTodayClockNum(theDate);      //获取每日的数量
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", theDate);
            jsonObject.put("count", count);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * 获取三十天内 用户讨论的数量走势图
     *
     * @return
     */
    @Override
    public JSONArray getAllMonthForumsNum() {
        List<String> listDate = this.getDays(); //获取全部日期
        JSONArray jsonArray = new JSONArray();
        for (String theDate : listDate) {
            int count = this.getTodayForumsNum(theDate);    //获取每日的数量
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", theDate);        //日期
            jsonObject.put("count", count);         //数量
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
