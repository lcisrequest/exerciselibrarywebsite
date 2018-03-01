package com.example.lcdemo;


import okhttp3.*;
import okio.ByteString;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by tsy
 */
public class SocketTest {

    @Test
    public void socketTest(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(3000, TimeUnit.SECONDS)
                .writeTimeout(3000,TimeUnit.SECONDS)
                .connectTimeout(3000,TimeUnit.SECONDS)
                .build();

        String url = "ws://gw.yundzh.com/ws?token=0000008d:1513325667:3b7f5d8c698ba0480786a84798c5fe81bd8423cf";
        Request request = new Request.Builder().url(url).build();
        WebSocket mSocket = okHttpClient.newWebSocket(request, new WebSocketListener() {


            ExecutorService writeService = Executors.newSingleThreadExecutor();
            WebSocket socket = null;

            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                System.out.println("链接打开");
                this.socket = webSocket;
                //建立成功后发送消息到服务端
//                writeService.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        socket.send("/stkdata?qid=higu001&sub=1&obj=SH600000");
//                    }
//                });
                super.onOpen(webSocket, response);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                System.out.println("收到消息String:");
                super.onMessage(webSocket, text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                System.out.println("收到消息bytes:");
                System.out.println(bytes.string(Charset.forName("utf-8")));
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                System.out.println("关闭中");
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                System.out.println("链接已关闭");
                super.onClosed(webSocket, code, reason);
                writeService.shutdown();
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                System.out.println("链接错误");
                super.onFailure(webSocket, t, response);
            }
        });
//        mSocke
        mSocket.send("/stkdata?qid=higu001&sub=1&obj=SH600000");
    }

}
