package com.example.lcdemo.base.controller;

import com.example.lcdemo.config.properties.JwtProperties;
import com.example.lcdemo.modular.auth.util.JwtTokenUtil;
import com.itspeed.higu.base.support.HttpKit;
import org.springframework.beans.factory.annotation.Autowired;


public class BaseController {

    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * 通过token直接获取用户id
     * @return
     */
    protected Integer getUserId(){
        return jwtTokenUtil.getUserIdFromToken(HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(9).replace("}",""));
    }
}
