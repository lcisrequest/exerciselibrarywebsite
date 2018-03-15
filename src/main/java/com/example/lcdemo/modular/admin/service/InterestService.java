package com.example.lcdemo.modular.admin.service;

import com.example.lcdemo.modular.admin.model.Interest;

import java.util.List;

public interface InterestService {
    void changeInterest(int userId, String interestStr);

    boolean havaInterest(int userId);

    List<Interest> getAllMyInterest(int userId);
}
