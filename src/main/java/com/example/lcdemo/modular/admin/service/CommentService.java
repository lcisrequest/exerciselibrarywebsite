package com.example.lcdemo.modular.admin.service;

import com.example.lcdemo.modular.admin.dto.CommentDTO;
import com.example.lcdemo.modular.admin.model.Reply;

import java.util.List;
import java.util.Map;

public interface CommentService {
    void addComment(CommentDTO comment, int userId);

    List<Map<String, Object>> getComment(int subjectId, int page, int limit);

    int getCommentNum(int subjectId);

    void addReply(Reply reply, int userId);

    List<Map<String, Object>> getReply(int commentId, int page, int limit);

    int getReplyNum(int commentId);

    void addLike(int commentId,int userId);

    List<Map<String, Object>> getMyReply(int userId);

    void MakeReplyIsRead(int replyId);
}
