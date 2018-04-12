package com.example.lcdemo.modular.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.modular.admin.dao.ClockMapper;
import com.example.lcdemo.modular.admin.dao.UserInfoMapper;
import com.example.lcdemo.modular.admin.model.Clock;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.admin.service.ClockService;
import com.example.lcdemo.modular.backend.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ClockServiceImpl implements ClockService {
    @Autowired
    ClockMapper clockMapper;
    @Autowired
    ConfigService configService;
    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 用户打卡
     *
     * @param content
     * @param userId
     * @return
     */
    @Override
    public String clockUser(String content, int userId) {
        String today = DateUtil.getDay();
        Clock clock = new Clock();
        clock.setCreateTime(today);
        clock.setUserId(userId);
        Clock c = clockMapper.selectOne(clock);
        if (c != null) {    //判断今天是否已经打过卡了
            throw new LcException(LcExceptionEnum.TODAY_IS_HAVE_CLOCK);
        }
        clock.setContent(content);
        //获取金币的最大值和最小值，经验值的最大值和最小值
        int minXP = Integer.valueOf(configService.getMinXP());
        int maxXP = Integer.valueOf(configService.getMaxXP());
        int minGold = Integer.valueOf(configService.getMinGold());
        int maxGold = Integer.valueOf(configService.getMaxGold());
        Random random = new Random();
        int xp = random.nextInt(maxXP - minXP) + minXP; //生成随机经验值
        int gold = random.nextInt(maxGold - minGold) + minGold; //生成随机金币
        clock.setXp(xp);
        clock.setGold(gold);
        clockMapper.insert(clock);//新增打卡记录
        StringBuilder sb = new StringBuilder();
        sb.append("打卡成功！您获得").append(xp).append("点经验值和").append(gold).append("金币！");
        this.clockBalance(userId, xp, gold);    //打卡结算
        return sb.toString();
    }

    /**
     * 判断今天是否打过卡了 true为打过卡
     *
     * @param userId
     * @return
     */
    @Override
    public boolean todayIsClock(int userId) {
        String today = DateUtil.getDay();
        Clock clock = new Clock();
        clock.setCreateTime(today);
        clock.setUserId(userId);
        Clock c = clockMapper.selectOne(clock);
        if (c == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取我的打卡记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<Clock> getMyClockRecord(int userId) {
        Wrapper<Clock> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderBy("create_time", false);
        return clockMapper.selectList(wrapper);
    }

    /**
     * 获取我的打卡天数
     *
     * @param userId
     * @return
     */
    @Override
    public Integer getMyClockNum(int userId) {
        Wrapper<Clock> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return clockMapper.selectCount(wrapper);
    }

    /**
     * 打卡结算
     *
     * @param userId
     * @param xp
     * @param gold
     */
    @Override
    public void clockBalance(int userId, int xp, int gold) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if (userInfo == null) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        int oldXP = userInfo.getXp();           //获取旧经验值
        int oldGold = userInfo.getGold();       //获取旧金币数
        int newXP = oldXP + xp;                 //算出新经验值
        int level = userInfo.getLevel();
        while (newXP >= 100) {                  //若经验值达到100，就升级
            newXP = newXP - 100;
            level++;
        }
        int newGold = oldGold + gold;           //获取新金币数
        userInfo.setXp(newXP);
        userInfo.setGold(newGold);
        userInfo.setLevel(level);
        userInfoMapper.updateById(userInfo);    //更新用户信息
    }
}

