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
     * 发送找回密码验证码
     *
     * @param
     */
    @Override
    public void sendMessage(String telphone) {
        this.userCanSendMessage(telphone);        //判断用户是否能发送验证码
        UserInfo userInfo = new UserInfo();
        userInfo.setTelphone(telphone);
        userInfo = userInfoMapper.selectOne(userInfo);  //根据手机号查找用户信息
        if (userInfo == null) {                           //判断该手机号码是否注册过用户，若没有注册过，则不能找回
            throw new LcException(LcExceptionEnum.TEL_USER_NOT_EXIST);
        }
        String varCodeStr = MessageUtil.sendMessage(telphone);  //发送验证码
        if (varCodeStr == null) {
            throw new LcException(LcExceptionEnum.VARCODE_ERROR);
        }
        VarCode code = new VarCode();
        code.setTelphone(telphone);
        code.setVarCode(varCodeStr);
        code.setCreateTime(DateUtil.getTime());
        varCodeMapper.insert(code);     //新增验证码记录
    }

    /**
     * 发送注册验证码
     *
     * @param telphone
     */
    @Override
    public void sendRegisterMessage(String telphone) {
        this.userCanSendMessage(telphone);        //判断用户是否能发送验证码
        UserInfo userInfo = new UserInfo();
        userInfo.setTelphone(telphone);
        userInfo = userInfoMapper.selectOne(userInfo);  //根据手机号查找用户信息
        if (userInfo != null) {                           //判断该手机号码是否注册过用户，若注册过，则不能再注册
            throw new LcException(LcExceptionEnum.TEL_USER_IS_EXIST);
        }
        String varCodeStr = MessageUtil.sendMessage(telphone);  //发送验证码
        if (varCodeStr == null) {
            throw new LcException(LcExceptionEnum.VARCODE_ERROR);
        }
        VarCode code = new VarCode();
        code.setTelphone(telphone);
        code.setVarCode(varCodeStr);
        code.setCreateTime(DateUtil.getTime());
        varCodeMapper.insert(code);     //新增验证码记录
    }

    /**
     * 判断用户是否符合发送条件
     *
     * @param telphone
     */
    public void userCanSendMessage(String telphone) {
        boolean inTime = this.sendInTime(telphone);
        if (inTime) {
            throw new LcException(LcExceptionEnum.TIME_NOT_ENOUGH);//1分钟之内不可重复发送验证码
        }
        boolean inCount = this.inTimes(telphone);
        if (inCount) {
            throw new LcException(LcExceptionEnum.COUNT_IS_OVER);//每天只能发送五次验证码
        }
    }

    /**
     * 判断过去一分钟内用户是否发送过验证码
     *
     * @param
     * @return
     */
    @Override
    public boolean sendInTime(String telphone) {
        Wrapper<VarCode> wrapper = new EntityWrapper<>();
        wrapper.eq("telphone", telphone);
        wrapper.orderBy("create_time", false);
        List<VarCode> list = varCodeMapper.selectList(wrapper);
        if (list.size() == 0) {
            return false;               //该手机还没有发送过验证码
        }
        VarCode code = list.get(0);
        String time = code.getCreateTime(); //获取上一次发送验证码的时间
        Date timeDate = DateUtil.parseTime(time);
        long timeLong = timeDate.getTime();
        long endLong = timeLong + 60000;   //获取发送验证码1分钟之后的时间
        Date endDate = new Date(endLong);
        String end = DateUtil.getTime(endDate);
        String now = DateUtil.getTime();    //获取现在时间
        boolean inTime = DateUtil.isInTheTimeRange(time, end, now); //判断是否在有效时间内
        if (inTime) {
            return true;
        }
        return false;
    }

    /**
     * 判断用户今天是否发送了5次验证码
     *
     * @param telphone
     * @return
     */
    public boolean inTimes(String telphone) {
        Wrapper<VarCode> wrapper = new EntityWrapper<>();
        wrapper.eq("telphone", telphone);
        int count = varCodeMapper.selectCount(wrapper);
        if (count >= 5) {   //次数大于5不能发送
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断验证码是否正确，是否有效
     *
     * @param telphone
     * @param varCode
     * @return
     */
    @Override
    public boolean VarCodeIsTrue(String telphone, String varCode) {
        VarCode code = new VarCode();
        code.setTelphone(telphone);
        code.setVarCode(varCode);
        code = varCodeMapper.selectOne(code);
        if (code == null) {     //判断验证码是否正确
            throw new LcException(LcExceptionEnum.VAR_CODE_IS_WRONG);
        }
        String time = code.getCreateTime(); //获取发送验证码的时间
        Date timeDate = DateUtil.parseTime(time);
        long timeLong = timeDate.getTime();
        long endLong = timeLong + 300000;   //获取发送验证码5分钟之后的时间
        Date endDate = new Date(endLong);
        String end = DateUtil.getTime(endDate);
        String now = DateUtil.getTime();    //获取现在时间
        boolean inTime = DateUtil.isInTheTimeRange(time, end, now); //是否在有效时间内
        if (inTime) {
            return true;
        }
        return false;
    }

}
