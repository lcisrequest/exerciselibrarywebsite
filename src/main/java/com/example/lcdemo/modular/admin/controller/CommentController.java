package com.example.lcdemo.modular.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.lcdemo.base.controller.BaseController;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.modular.admin.dto.CommentDTO;
import com.example.lcdemo.modular.admin.model.Reply;
import com.example.lcdemo.modular.admin.service.CollectService;
import com.example.lcdemo.modular.admin.service.CommentService;
import com.example.lcdemo.modular.backend.model.Knowledge;
import com.example.lcdemo.modular.backend.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {
    @Autowired
    CommentService commentService;
    @Autowired
    CollectService collectService;
    @Autowired
    KnowledgeService knowledgeService;

    /**
     * 新增评论
     *
     * @param comment
     * @return
     */
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public ResponseEntity addComment(@RequestBody CommentDTO comment) {
        commentService.addComment(comment, getUserId());
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }


    /**
     * 分页获取指定题目的评论
     *
     * @param subjectId
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getComment")
    public ResponseEntity getComment(int subjectId, int page, int limit) {
        List<Map<String, Object>> list = commentService.getComment(subjectId, page, limit);
        int conut = commentService.getCommentNum(subjectId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("count", conut);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }

    /**
     * 新增评论的回复
     *
     * @param reply
     * @return
     */
    @RequestMapping("/addReply")
    public ResponseEntity addReply(@RequestBody Reply reply) {
        commentService.addReply(reply, getUserId());
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }

    /**
     * 分页获取指定评论的回复
     *
     * @param commentId
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getReply")
    public ResponseEntity getReply(int commentId, int page, int limit) {
        List<Map<String, Object>> list = commentService.getReply(commentId, page, limit);
        int count = commentService.getReplyNum(commentId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("count", count);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }

    /**
     * 新增点赞
     *
     * @param commentId
     * @return
     */
    @RequestMapping("/addLike")
    public ResponseEntity addLike(int commentId) {
        commentService.addLike(commentId, getUserId());
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }

    /**
     * 添加收藏或取消收藏题目
     *
     * @param subjectId
     * @return
     */
    @RequestMapping("/addCollect")
    public ResponseEntity addCollect(int subjectId) {
        String str = collectService.addCollect(subjectId, getUserId());
        return ResponseEntity.ok(SuccessTip.create(str));
    }

    /**
     * 分页获取指定类型的我的收藏题目
     *
     * @param problemType
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getMyCollect")
    public ResponseEntity getAllMYCollect(String problemType, int page, int limit) {
        List<Map<String, Object>> list = collectService.getAllMYCollect(problemType, getUserId(), page, limit);
        int count = collectService.getAllMyCollectNum(problemType, getUserId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("count", count);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }

    /**
     * 分页获取我的收藏知识
     *
     * @param type
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getAllMyCollectKnowledge")
    public ResponseEntity getAllMyCollectKnowledge(int type, int page, int limit) {
        List<Knowledge> list = knowledgeService.getAllMyCollectKnowledge(type, getUserId(), page, limit);
        int count = knowledgeService.getAllMyCollectKnowledgeCount(type, getUserId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("count", count);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }

    /**
     * 添加知识收藏或取消收藏
     *
     * @param knowledgeId
     * @return
     */
    @RequestMapping("/addKnowledgeCollect")
    public ResponseEntity addKnowledgeCollect(int knowledgeId) {
        String str = knowledgeService.addKnowledgeCollect(knowledgeId, getUserId());
        return ResponseEntity.ok(SuccessTip.create(str));
    }

    /**
     * 获取我的未读回复
     *
     * @return
     */
    @RequestMapping("/getNotReadReply")
    public ResponseEntity getAllMyNotReadReply() {
        List<Map<String, Object>> list = commentService.getMyReply(getUserId());
        int count = list.size();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("count", count);
        return ResponseEntity.ok(SuccessTip.create(jsonObject, "请求成功"));
    }


    /**
     * 让回复变成已读
     *
     * @param replyId
     * @return
     */
    @RequestMapping("/isReadReply")
    public ResponseEntity letReplyIsRead(int replyId) {
        commentService.MakeReplyIsRead(replyId);
        return ResponseEntity.ok(SuccessTip.create("请求成功"));
    }
}

