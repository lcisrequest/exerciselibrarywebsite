package com.example.lcdemo.modular.admin.websocket;

import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


public class WsServer extends WebSocketServer {
    public WsServer(int port) {
        super(new InetSocketAddress(port));
    }

    public WsServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // ws连接的时候触发的代码，onOpen中我们不做任何操作

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        //断开连接时候触发代码
        userLeave(conn);
        System.out.println(reason);
        System.out.println("链接断开");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println(message);
     /*   if (null != message && message.startsWith("online")) {
            String userName = message.replaceFirst("online", message);//用户名
            userJoin(conn, userName);//用户加入
            System.out.println("连接成功 " + message);
            WsPool.sendMessageToAll("您发送的消息是: "+message);

        } else if (null != message && message.startsWith("offline")) {
            System.out.println("连接失败 " + message);
            userLeave(conn);
        }*/
        String userName = message;//用户名
        userJoin(conn, userName);//用户加入
        System.out.println("连接成功 " + message);
        WsPool.sendMessageToAll("您发送的消息是: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        //错误时候触发的代码
        System.out.println("on error");
        ex.printStackTrace();
    }

    /**
     * 去除掉失效的websocket链接
     *
     * @param conn
     */
    private void userLeave(WebSocket conn) {
        WsPool.removeUser(conn);
    }

    /**
     * 将websocket加入用户池
     *
     * @param conn
     * @param userName
     */
    private void userJoin(WebSocket conn, String userName) {
        WsPool.addUser(userName, conn);
        System.out.println("用户加入 " + userName);
    }

}