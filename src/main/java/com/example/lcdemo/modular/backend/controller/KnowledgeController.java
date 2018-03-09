package com.example.lcdemo.modular.backend.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.admin.model.Homepage;
import com.example.lcdemo.modular.admin.service.HomepageService;
import com.example.lcdemo.modular.backend.model.Knowledge;
import com.example.lcdemo.modular.backend.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class KnowledgeController extends BaseController {
    @Autowired
    KnowledgeService knowledgeService;
    @Autowired
    HomepageService homepageService;

    /**
     * 文件上传
     *
     * @param file
     * @param req
     * @return
     */
    @RequestMapping("/fileUpload")
    public ResponseEntity fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest req) {
        String filename = knowledgeService.fileUpload(file, req);
        if (filename == null) {
            return ResponseEntity.ok(SuccessTip.create("上传失败"));
        } else {
            return ResponseEntity.ok(SuccessTip.create(filename, "上传成功"));
        }
    }

    /**
     * 添加考试大纲或课本知识
     *
     * @param knowledge
     * @return
     */
    @RequestMapping("/addKnowledge")
    public ResponseEntity addKnowledge(@RequestBody Knowledge knowledge) {
        if (knowledgeService.addKnowledge(knowledge)) {
            return ResponseEntity.ok(SuccessTip.create("添加成功"));
        } else {
            return ResponseEntity.ok(SuccessTip.create("添加失败"));
        }
    }

    /**
     * 修改考试大纲或课本知识
     *
     * @param knowledge
     * @return
     */
    @RequestMapping("/updateKnowledge")
    public ResponseEntity updateKnowledge(@RequestBody Knowledge knowledge) {
        if (knowledgeService.updateKnowledge(knowledge)) {
            return ResponseEntity.ok(SuccessTip.create("修改成功"));
        } else {
            return ResponseEntity.ok(SuccessTip.create("修改失败"));
        }
    }

    /**
     * 删除考试大纲或课本知识
     *
     * @param knowledgeId
     * @return
     */
    @RequestMapping("/deleteKnowledge")
    public ResponseEntity deleteKnowledge(int knowledgeId) {
        if (knowledgeService.deleteKnowledge(knowledgeId)) {
            return ResponseEntity.ok(SuccessTip.create("删除成功"));
        } else {
            return ResponseEntity.ok(SuccessTip.create("删除失败"));
        }
    }

    /**
     * 分页获取指定类型的考试大纲或课本知识
     *
     * @param type
     * @param problemType
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getKnowledge")
    public ResponseEntity getKnowledge(int type, String problemType, int page, int limit) {
        List<Knowledge> list = knowledgeService.getKnowledge(type, problemType, page, limit);
        int count = knowledgeService.getKnowledgeNum(type, problemType);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("count", count);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }

    /**
     * 新增首页信息
     *
     * @param homepage
     * @return
     */
    @RequestMapping("/addHomePage")
    public ResponseEntity addHomePage(@RequestBody Homepage homepage) {
        homepageService.addHomePage(homepage);
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }

    /**
     * 删除首页信息
     *
     * @param homepageId
     * @return
     */
    @RequestMapping("/deleteHomePage")
    public ResponseEntity deleteHomePage(int homepageId) {
        homepageService.deleteHomePage(homepageId);
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }

    /**
     * 更改首页信息
     *
     * @param homepage
     * @return
     */
    @RequestMapping("/updateHomePage")
    public ResponseEntity updateHomePage(@RequestBody Homepage homepage) {
        homepageService.updateHomePage(homepage);
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }


    /**
     * 后台分页获取首页信息
     *
     * @param infoType
     * @param title
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getHomepage")
    public ResponseEntity getHomePage(String infoType, String title, int page, int limit) {
        List<Homepage> list = homepageService.getHomePage(infoType, title, page, limit);
        int count = homepageService.getHomePageCount(infoType, title);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("count", count);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }
}
