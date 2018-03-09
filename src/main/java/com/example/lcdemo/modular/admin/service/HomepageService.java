package com.example.lcdemo.modular.admin.service;

import com.example.lcdemo.modular.admin.model.Homepage;

import java.util.List;

public interface HomepageService {
    void addHomePage(Homepage homepage);

    void deleteHomePage(int homepageId);

    void updateHomePage(Homepage homepage);

    List<Homepage> getHomePage(String infoType, String title, int page, int limit);

    Integer getHomePageCount(String infoType, String title);
}
