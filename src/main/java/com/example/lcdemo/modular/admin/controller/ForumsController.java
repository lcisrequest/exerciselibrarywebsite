package com.example.lcdemo.modular.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.admin.model.Forums;
import com.example.lcdemo.modular.admin.model.Reply;
import com.example.lcdemo.modular.admin.service.ForumsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forums")
public class ForumsController extends BaseController {
    @Autowired
    ForumsService forumsService;

    /**
     * 新增讨论
     *
     * @param forums
     * @return
     */
    @RequestMapping("/addForums")
    public ResponseEntity addForums(@RequestBody Forums forums) {
        forums.setUserId(getUserId());
        forumsService.addForums(forums);
        return ResponseEntity.ok(SuccessTip.create("添加成功"));
    }

    /**
     * 删除讨论
     *
     * @param forumsId
     * @return
     */
    @RequestMapping("/deleteForums")
    public ResponseEntity deleteForums(int forumsId) {
        forumsService.deleteForums(forumsId, getUserId());
        return ResponseEntity.ok(SuccessTip.create("删除车工"));
    }

    /**
     * 添加讨论点赞或删除点赞
     *
     * @param forumsId
     * @return
     */
    @RequestMapping("/addForumsLike")
    public ResponseEntity addForumsLike(int forumsId) {
        forumsService.addForumsLike(forumsId, getUserId());
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }

    /**
     * 新增讨论的回复
     *
     * @param reply
     * @param userId
     * @return
     */
    @RequestMapping("/addForumsReply")
    public ResponseEntity addForumsReply(Reply reply, int userId) {
        forumsService.addForumsReply(reply, getUserId());
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }

    /**
     * 分页获取指定讨论的所有回复
     *
     * @param page
     * @param limit
     * @param forumsId
     * @return
     */
    @RequestMapping("/getForumsAllReply")
    public ResponseEntity getForumsAllReply(int page, int limit, int forumsId) {
        List<Map<String, Object>> list = forumsService.getForumsAllReply(page, limit, forumsId);
        int count = forumsService.getForumsAllReplyNum(forumsId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("count", count);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }
}
