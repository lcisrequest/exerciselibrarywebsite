/*
package com.example.lcdemo.common.runner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itspeed.higu.common.SocketConstant;
import com.itspeed.higu.modular.app.service.UserStrategyService;
import com.itspeed.higu.modular.app.vo.EntrustingVO;
import com.itspeed.higu.modular.app.vo.RiskManagementVO;
import okhttp3.*;
import okio.ByteString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

*/
/**
 * Created by tsy
 *//*

@Component
public class RiskWebSocketListener extends WebSocketListener {

    @Autowired
    UserStrategyService userStrategyService;

    private WebSocket socket = null;

    private static int xcnum = 300;//一个线程处理多少条数据;

    public RiskWebSocketListener() {
        try {
            run();
        } catch (Exception e) {

        }
    }
    private void run() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(3000, TimeUnit.SECONDS)
                .writeTimeout(3000, TimeUnit.SECONDS)
                .connectTimeout(3000, TimeUnit.SECONDS)
                .build();
        // String url = "ws://localhost:8080/websocket";
        String url = "ws://" + SocketConstant.url + "/ws?token=" + SocketConstant.token;

        Request request = new Request.Builder().url(url).build();
        socket = okHttpClient.newWebSocket(request, this);
    }

    public void sendMsg(String s) {
//        System.out.println("发送:"+s);
        socket.send(s);
    }

//    public static RiskWebSocketListener getSocket() {
//        if (riskWebSocketListener == null) {
//            riskWebSocketListener = new RiskWebSocketListener();
//        }
//        return riskWebSocketListener;
//    }

    public void close() {
//        riskWebSocketListener = null;
        socket.close(1000, "主动关闭");
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
//        System.out.println("打开连接");
        this.socket = webSocket;
        //取所有需要监控的股票代码
        List<String> list =null;
        try {
            list = userStrategyService.getMonitorList();
//            System.out.println(list.size());
        } catch (NullPointerException np) {

        }
        if (null != list) {
            StringBuffer sb = new StringBuffer();
            for (String s : list) {
                sb.append(s + ",");
            }
            try {
                sb.deleteCharAt(sb.lastIndexOf(","));
                sendMsg("/stkdata?qid=1&obj=" + sb.toString() + "&sub=1&field=ZuiXinJia");
            } catch (StringIndexOutOfBoundsException e) {

            }
        }

        super.onOpen(webSocket, response);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        try {
            String re = bytes.string(Charset.forName("utf-8"));
            //{"Qid":"","Err":0,"Counter":1,"Data":{"Id":27,"ObjCount":1,"RepDataStkData":[{"Obj":"SZ000001","ZuiXinJia":12.74,"XuHao":0}]}}
            JSONObject jsonObject = JSON.parseObject(re);
            JSONObject data = jsonObject.getJSONObject("Data");
            JSONArray repDataStkData = data.getJSONArray("RepDataStkData");//数据有变动的股票
            //1.进行风控计算
            //取需要判断的股票列表
            Map<String, List<RiskManagementVO>> riskManagementMap = userStrategyService.getRiskManagement();
            // 2.进行委托监控
            Map<String, List<EntrustingVO>> entrustingMap = userStrategyService.getEntrust();
            //监控的股票操作
            for (int i = 0; i < repDataStkData.size(); i++) {
                //循环数据有变动的股票/每只股票
                String obj = repDataStkData.getJSONObject(i).getString("Obj");
                int zuixinjia = new Float(repDataStkData.getJSONObject(i).getFloat("ZuiXinJia") * 100).intValue();//单位换算为分
                if (zuixinjia == 0) {
                    continue;
                }
//                    System.out.println("该股票拥有订单数:"+list.size());
                //计算风控
                if (null != riskManagementMap && riskManagementMap.size() > 0 && riskManagementMap.containsKey(obj)) {
                    List<RiskManagementVO> riskManagementVOS = riskManagementMap.get(obj);
                    int riskThreadSize = riskManagementVOS.size() / xcnum + 1;//启动几个线程

                    for (int rts = 0; rts < riskThreadSize; rts++) {
                        //每个线程启动一部分操作
//                        System.out.println("卖股票咯");
                        // System.out.println("风控卖出");
                        userStrategyService.sellStrategy(riskManagementVOS.subList(rts * xcnum, (rts + 1) * xcnum > riskManagementVOS.size() ? riskManagementVOS.size() : (rts + 1) * xcnum)
                                , zuixinjia);
                    }
                }

                // 计算委托
                if (null != entrustingMap && entrustingMap.size() > 0 && entrustingMap.containsKey(obj)) {
                    List<EntrustingVO> entrustingVOS = entrustingMap.get(obj);
                    int entrustingThreadSize = entrustingVOS.size() / xcnum + 1;//启动几个线程
                    for (int rts = 0; rts < entrustingThreadSize; rts++) {
                        //每个线程启动一部分操作
                        userStrategyService.buyStrategy(entrustingVOS.subList(rts * xcnum, (rts + 1) * xcnum > entrustingVOS.size() ? entrustingVOS.size() : (rts + 1) * xcnum)
                                , zuixinjia);
                    }
                }
            }
        } catch (Exception e) {
//            System.out.println("接收数据出错，什么都不操作!");
        }
        super.onMessage(webSocket, bytes);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        System.out.println("连接关闭中");
        run();
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        System.out.println("连接关闭！");
        run();
        super.onClosed(webSocket, code, reason);
//                writeService.shutdown();
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
//        t.printStackTrace();
        try {
            Thread.currentThread().sleep(30000);
        } catch (InterruptedException e) {
        }
        run();
        super.onFailure(webSocket, t, response);
    }
}
*/
