package com.example.lcdemo.modular.backend.service;

import java.util.List;
import java.util.Map;

public interface BackendForumsService {

    List<Map<String, Object>> getAllForums(int page, int limit);

    void topForums(int forumsId);

    void notTopForums(int forumsId);

    Integer getAllForumsNum();

    void deleteForums(int forumsId);
}
