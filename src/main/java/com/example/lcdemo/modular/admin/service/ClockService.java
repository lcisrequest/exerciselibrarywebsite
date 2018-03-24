package com.example.lcdemo.modular.admin.service;

import com.example.lcdemo.modular.admin.model.Clock;

import java.util.List;

public interface ClockService {
    String clockUser(String content, int userId);

    boolean todayIsClock(int userId);

    List<Clock> getMyClockRecord(int userId);

    void clockBalance(int userId, int xp, int gold);

    Integer getMyClockNum(int userId);
}
