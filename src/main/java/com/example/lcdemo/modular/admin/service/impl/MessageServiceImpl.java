package com.example.lcdemo.modular.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.base.util.MessageUtil;
import com.example.lcdemo.modular.admin.dao.UserInfoMapper;
import com.example.lcdemo.modular.admin.dao.VarCodeMapper;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.admin.model.VarCode;
import com.example.lcdemo.modular.admin.service.MessageService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    VarCodeMapper varCodeMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 发送验证码
     *
     * @param tel
     */
    @Override
    public void sendMessage(String tel, int userId) {
        this.userCanSendMessage(userId);        //判断用户是否能发送验证码
        String varCodeStr = MessageUtil.sendMessage(tel);  //发送验证码
        if (varCodeStr == null) {
            throw new LcException(LcExceptionEnum.VARCODE_ERROR);
        }
        VarCode code = new VarCode();
        code.setUserId(userId);
        code.setVarCode(varCodeStr);
        code.setCreateTime(DateUtil.getTime());
        varCodeMapper.insert(code);     //新增验证码记录
    }

    /**
     * 判断用户是否符合发送条件
     *
     * @param userId
     */
    public void userCanSendMessage(int userId) {
        boolean inTime = this.sendInTime(userId);
        if (inTime) {
            throw new LcException(LcExceptionEnum.TIME_NOT_ENOUGH);
        }
        boolean inCount = this.inTimes(userId);
        if (inCount) {
            throw new LcException(LcExceptionEnum.COUNT_IS_OVER);
        }
    }

    /**
     * 判断过去一分钟内用户是否发送过验证码
     *
     * @param userId
     * @return
     */
    @Override
    public boolean sendInTime(int userId) {
        Wrapper<VarCode> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderBy("create_time", false);
        List<VarCode> list = varCodeMapper.selectList(wrapper);
        if (list.size() == 0) {
            return false;               //用户还没有发送过验证码
        }
        VarCode code = list.get(0);
        String time = code.getCreateTime(); //获取上一次发送验证码的时间
        Date timeDate = DateUtil.parseTime(time);
        long timeLong = timeDate.getTime();
        long endLong = timeLong + 60000;   //获取发送验证码1分钟之后的时间
        Date endDate = new Date(endLong);
        String end = DateUtil.getTime(endDate);
        String now = DateUtil.getTime();    //获取现在时间
        boolean inTime = DateUtil.isInTheTimeRange(time, end, now);
        if (inTime) {
            return true;
        }
        return false;
    }

    /**
     * 判断用户今天是否发送了5次验证码
     *
     * @param userId
     * @return
     */
    public boolean inTimes(int userId) {
        Wrapper<VarCode> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        int count = varCodeMapper.selectCount(wrapper);
        if (count >= 5) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断验证码是否正确，是否有效
     *
     * @param userId
     * @param varCode
     * @return
     */
    @Override
    public boolean VarCodeIsTrue(int userId, String varCode) {
        VarCode code = new VarCode();
        code.setUserId(userId);
        code.setVarCode(varCode);
        code = varCodeMapper.selectOne(code);
        if (code == null) {
            throw new LcException(LcExceptionEnum.VAR_CODE_IS_WRONG);
        }
        String time = code.getCreateTime(); //获取发送验证码的时间
        Date timeDate = DateUtil.parseTime(time);
        long timeLong = timeDate.getTime();
        long endLong = timeLong + 300000;   //获取发送验证码5分钟之后的时间
        Date endDate = new Date(endLong);
        String end = DateUtil.getTime(endDate);
        String now = DateUtil.getTime();    //获取现在时间
        boolean inTime = DateUtil.isInTheTimeRange(time, end, now);
        if (inTime) {
            return true;
        }
        return false;
    }

    /**
     * 忘记密码通过验证码修改密码
     * @param userId
     * @param varcode
     * @param newPassword
     */
    @Override
    public void forgetPassword(int userId, String varcode, String newPassword) {
        boolean isRight = this.VarCodeIsTrue(userId, varcode);
        if (!isRight) {
            throw new LcException(LcExceptionEnum.VAR_CODE_OVERTIME); //验证码超时
        }
        UserInfo user = userInfoMapper.selectById(userId);
        user.setPassword(newPassword);
        userInfoMapper.updateById(user);    //更改密码
    }
}
