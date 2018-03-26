package com.example.lcdemo.modular.backend.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.modular.backend.dao.ConfigMapper;
import com.example.lcdemo.modular.backend.model.Config;
import com.example.lcdemo.modular.backend.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 修改最小经验值
     *
     * @param num
     */
    @Override
    public void updateMinXP(int num) {
        if (num < 0) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        Config config = new Config();
        config.setKey("min_xp");
        config = configMapper.selectOne(config);
        if (config == null) {
            throw new LcException(LcExceptionEnum.DB_DATA_ERROR);
        }
        config.setValue(num + "");
        configMapper.updateById(config);
    }

    /**
     * 修改最大经验值
     *
     * @param num
     */
    @Override
    public void updateMaxXP(int num) {
        Config min = new Config();
        min.setKey("min_xp");
        min = configMapper.selectOne(min);
        int minXP = Integer.valueOf(min.getValue());
        if (num < minXP) {
            throw new LcException(LcExceptionEnum.MIN_BIGTHAN_MAX);
        }
        Config config = new Config();
        config.setKey("max_xp");
        config = configMapper.selectOne(config);
        if (config == null) {
            throw new LcException(LcExceptionEnum.DB_DATA_ERROR);
        }
        config.setValue(num + "");
        configMapper.updateById(config);
    }

    /**
     * 修改最小金币
     *
     * @param num
     */
    @Override
    public void updateMinGold(int num) {
        if (num < 0) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        Config config = new Config();
        config.setKey("min_gold");
        config = configMapper.selectOne(config);
        if (config == null) {
            throw new LcException(LcExceptionEnum.DB_DATA_ERROR);
        }
        config.setValue(num + "");
        configMapper.updateById(config);
    }

    /**
     * 修改最大金币
     *
     * @param num
     */
    @Override
    public void updateMaxGold(int num) {
        Config min = new Config();
        min.setKey("min_gold");
        min = configMapper.selectOne(min);
        int minXP = Integer.valueOf(min.getValue());
        if (num < minXP) {
            throw new LcException(LcExceptionEnum.MIN_BIGTHAN_MAX);
        }
        Config config = new Config();
        config.setKey("max_gold");
        config = configMapper.selectOne(config);
        if (config == null) {
            throw new LcException(LcExceptionEnum.DB_DATA_ERROR);
        }
        config.setValue(num + "");
        configMapper.updateById(config);
    }

    /**
     * 获取所有配置信息
     *
     * @return
     */
    @Override
    public List<Config> getAllConfig() {
        Wrapper<Config> wrapper = new EntityWrapper();
        List<Config> list = configMapper.selectList(wrapper);
        return list;
    }

    /**
     * 设置偷看所需金币数量
     *
     * @param num
     */
    @Override
    public void updatePeekGold(int num) {
        if (num < 0) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        Config config = new Config();
        config.setKey("peek_frice");
        config = configMapper.selectOne(config);    //设置偷看金币数量
        if (config == null) {
            throw new LcException(LcExceptionEnum.DB_DATA_ERROR);
        }
        config.setValue(num + "");
        configMapper.updateById(config);
    }
}
