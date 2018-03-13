package com.example.lcdemo.modular.backend.controller;

import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.backend.model.Config;
import com.example.lcdemo.modular.backend.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class ConfigController extends BaseController {
    @Autowired
    ConfigService configService;

    /**
     * 获取所有配置信息
     *
     * @return
     */
    @RequestMapping("/getAllConfig")
    public ResponseEntity getAllConfig() {
        List<Config> list = configService.getAllConfig();
        return ResponseEntity.ok(SuccessTip.create(list, "请求成功"));
    }

    /**
     * 修改经验值和金币的最大最小值
     *
     * @param minXp
     * @param maxXp
     * @param minGold
     * @param maxGold
     * @return
     */
    @RequestMapping("/updateXpAndGold")
    public ResponseEntity updateXPAndGoldConfig(int minXp, int maxXp, int minGold, int maxGold) {
        configService.updateMinXP(minXp);
        configService.updateMaxXP(maxXp);
        configService.updateMinGold(minGold);
        configService.updateMaxGold(maxGold);
        return ResponseEntity.ok(SuccessTip.create("修改成功"));
    }
}
