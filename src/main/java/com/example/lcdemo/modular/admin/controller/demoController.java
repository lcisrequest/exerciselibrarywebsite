package com.example.lcdemo.modular.admin.controller;


import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class demoController extends BaseController {

    @RequestMapping("/test")
    public ResponseEntity test(HttpServletRequest request,String urls) {
        System.out.println(urls);
        String send = "";
        StringBuffer url = request.getRequestURL();
        String urlStr = url.toString();
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).append("/").toString();
        System.out.println(url);
        System.out.println(tempContextUrl);
        System.out.println(urlStr);
        return ResponseEntity.ok(SuccessTip.create(tempContextUrl, ""));
    }

    @RequestMapping("/")
    public ResponseEntity getIt(String url){
        System.out.println(url);
        return ResponseEntity.ok(SuccessTip.create("登录成功"));
    }
}
