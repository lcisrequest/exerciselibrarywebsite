package com.example.lcdemo.modular.admin.service;

public interface MessageService {

    void sendMessage(String tel, int userId);

    boolean VarCodeIsTrue(int userId, String varCode);

    boolean sendInTime(int userId);

}
