package com.example.lcdemo.common.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by tsy
 */
@Component
@Order(1)
public class RiskManagementRunner implements CommandLineRunner {

//    @Autowired
//    UserStrategyService userStrategyService;
//    @Autowired
//    SocketIOServerService socketIOServerService;



    @Override
    public void run(String... args) throws Exception {
        // socketIOServerService.run();
//        RiskWebSocketListener.getSocket();
        //启动socket

    }


}
