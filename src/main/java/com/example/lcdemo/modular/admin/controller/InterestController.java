package com.example.lcdemo.modular.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.admin.dao.InterestMapper;
import com.example.lcdemo.modular.admin.model.Interest;
import com.example.lcdemo.modular.admin.model.Test;
import com.example.lcdemo.modular.admin.service.InterestService;
import com.example.lcdemo.modular.backend.model.Knowledge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interest")
public class InterestController extends BaseController {
    @Autowired
    InterestService interestService;

    /**
     * 修改兴趣
     *
     * @param interestStr
     * @return
     */
    @RequestMapping("/changeInterest")
    public ResponseEntity changeInterest(String interestStr) {
        interestService.changeInterest(getUserId(), interestStr); //所有兴趣，用,分割
        return ResponseEntity.ok(SuccessTip.create("修改成功"));
    }

    /**
     * 判断用户是否拥有兴趣
     *
     * @return
     */
    @RequestMapping("/isHaveInterest")
    public ResponseEntity isHaveInterest() {
        boolean isHaveInterest = interestService.havaInterest(getUserId());
        return ResponseEntity.ok(SuccessTip.create(isHaveInterest, "请求成功"));
    }


    /**
     * 获取我的所有兴趣
     *
     * @return
     */
    @RequestMapping("/getAllMyInterest")
    public ResponseEntity getAllMyInterest() {
        List<Interest> list = interestService.getAllMyInterest(getUserId());
        return ResponseEntity.ok(SuccessTip.create(list, "请求成功"));
    }

    /**
     * 根据兴趣获取指定数量首页推荐练习和知识点
     *
     * @param num
     * @return
     */
    @RequestMapping("/getHomepageRecommend")
    public ResponseEntity getHomepageRecommend(int num) {
        List<Map<String, Object>> listTest = interestService.getHomePageTest(getUserId(), num);
        List<Map<String, Object>> listKnowledge = interestService.getHomePageKnowledge(getUserId(), num);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("listTest", listTest);
        jsonObject.put("listKnowledge", listKnowledge);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }
}
