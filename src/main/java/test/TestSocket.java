package test;


import com.example.lcdemo.base.support.HttpKit;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tsy
 * 1、 APPID：b5de7d108eba11e7886b0242ac11003c；
 2、 Secret Key：0vbfNDL1oyVx；
 3、 Short APPID: 0000008d；
 4、 试用期时间：（2017/12/01-2017/12/30）；
 5、 官网地址：http://yun.gw.com.cn/index.html；
 6、 API文档中心：http://yun.gw.com.cn/DocCenter/（技术接入文档）
 7、 请求地址： http://gw.yundzh.com/
 */
public class TestSocket {

    public String getToken(){

        return null;
    }

    public static void main(String[] args) throws InterruptedException {

        String appId ="b5de7d108eba11e7886b0242ac11003c";
        String secretKey = "0vbfNDL1oyVx";
        //请求token 0000008d:1513530528:0c6467c7551ba6f4afdc34e10907b239d2731e5c  id:44
        //0000008d:1513530796:0f7eeb7feb2e05acb63d661dac69329643316590
//        Map<String, String> pa = new HashMap<>();
//        pa.put("appid", appId);
//        pa.put("secret_key", secretKey);
//        String json = HttpKit.sendGet("http://gw.yundzh.com/token/access", pa);
//        System.out.println(json);
//        JSONObject re = JSON.parseObject(json);
//        JSONObject data = re.getJSONObject("Data");
//        JSONArray reqData = data.getJSONArray("RepDataToken");
//        String token = reqData.getJSONObject(0).getString("token");
//        System.out.println("过期时间："+reqData.getJSONObject(0).getInteger("duration"));
//        System.out.println("token:"+token);

        String url = "http://gw.yundzh.com/stkdata";
        Map<String, String> map = new HashMap<>();
        map.put("token", "0000008d:1513530528:0c6467c7551ba6f4afdc34e10907b239d2731e5c");
        map.put("obj", "SZ000002");
//        "ws://gw.yundzh.com/ws?token=0000008d:1513325667:3b7f5d8c698ba0480786a84798c5fe81bd8423cf";
        String s = HttpKit.sendGet(url, map);
        System.out.println(s);


//        RiskWebSocketListener socket = new RiskWebSocketListener();
//        riskWebSocketListener.sendMsg("/stkdata?obj="+sb.toString()+"&sub=1&field=ZuiXinJia");
//        socket.sendMsg("/stkdata?obj=SZ000001");
//        while (true) {
//            Thread.sleep(2000);
//            socket.sendMsg("测试");
//            Thread.sleep(2000);
//            socket.close();
        }
//        System.out.println("主程序结束");
    }
