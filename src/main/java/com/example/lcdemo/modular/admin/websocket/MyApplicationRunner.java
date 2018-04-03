package com.example.lcdemo.modular.admin.websocket;


import org.java_websocket.WebSocketImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 这里通过设定value的值来指定执行顺序
 */
@Component
@Order(value = 1)
public class MyApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        WebSocketImpl.DEBUG = false;
        WsServer s;
        s = new WsServer(8887);
        s.start();
        System.out.println("MyApplicationRunner1!");
    }

}