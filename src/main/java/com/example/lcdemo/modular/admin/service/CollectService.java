package com.example.lcdemo.modular.admin.service;

import java.util.List;
import java.util.Map;

public interface CollectService {
    String addCollect(int subjectId, int userId);

    List<Map<String, Object>> getAllMYCollect(String problemType, int userId, int page, int limit);

    Integer getAllMyCollectNum(String problemType, int userId);
}
