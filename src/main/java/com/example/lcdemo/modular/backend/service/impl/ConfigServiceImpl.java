package com.example.lcdemo.modular.backend.service.impl;

import com.example.lcdemo.modular.backend.dao.ConfigMapper;
import com.example.lcdemo.modular.backend.model.Config;
import com.example.lcdemo.modular.backend.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    ConfigMapper configMapper;

    @Override
    public String getMinXP() {
        Config c = new Config();
        c.setKey("min_xp");
        return configMapper.selectOne(c).getValue(); //获取最小经验值
    }

    @Override
    public String getMaxXP() {
        Config c = new Config();
        c.setKey("max_xp");
        return configMapper.selectOne(c).getValue(); //获取最大经验值
    }

    @Override
    public String getMinGold() {
        Config c = new Config();
        c.setKey("min_gold");
        return configMapper.selectOne(c).getValue(); //获取最小金币
    }

    @Override
    public String getMaxGold() {
        Config c = new Config();
        c.setKey("max_gold");
        return configMapper.selectOne(c).getValue(); //获取最大金币
    }
}
