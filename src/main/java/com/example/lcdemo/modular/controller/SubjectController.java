package com.example.lcdemo.modular.controller;


import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.modular.admin.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subject")
public class SubjectController extends BaseController {
    @Autowired
    SubjectService subjectService;

}
