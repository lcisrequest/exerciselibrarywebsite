package com.example.lcdemo.modular.admin.service;

import com.example.lcdemo.modular.admin.model.Forums;
import com.example.lcdemo.modular.admin.model.Reply;

import java.util.List;
import java.util.Map;

public interface ForumsService {
    void addForums(Forums forums);

    void deleteForums(int forumsId, int userId);

    void addForumsReply(Reply reply, int userId);

    void addForumsLike(int forumsId, int userId);

    List<Map<String, Object>> getForumsAllReply(int page, int limit, int forumsId);

    Integer getForumsAllReplyNum(int forumsId);
}
