package com.example.lcdemo.modular.admin.service;

public interface MessageService {

    void sendMessage(String telphone);

    boolean VarCodeIsTrue(String telphone, String varCode);

    boolean sendInTime(String telphone);

    void sendRegisterMessage(String telphone);

}
