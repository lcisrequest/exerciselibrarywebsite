package com.example.lcdemo.modular.backend.service;

import com.example.lcdemo.modular.backend.model.Config;

import java.util.List;

public interface ConfigService {
    String getMinXP();

    String getMaxXP();

    String getMinGold();

    String getMaxGold();

    void updateMinXP(int num);

    void updateMaxXP(int num);

    void updateMinGold(int num);

    void updateMaxGold(int num);

    List<Config> getAllConfig();

    void updatePeekGold(int num);
}
