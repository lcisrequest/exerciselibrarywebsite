package com.example.lcdemo.modular.admin.controller;


import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.admin.dao.InterestMapper;
import com.example.lcdemo.modular.admin.model.Interest;
import com.example.lcdemo.modular.admin.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
