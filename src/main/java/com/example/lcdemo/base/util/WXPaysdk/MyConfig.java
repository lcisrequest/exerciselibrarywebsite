
package com.example.lcdemo.base.util.WXPaysdk;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class MyConfig implements WXPayConfig {
    private byte[] certData;

    public MyConfig(boolean flag) throws Exception {
//		String path = this.getClass().getClassLoader().getResource("/").getPath()+"cert/cert/apiclient_cert.p12";
//		String path = this.getClass().getResource("/cert/cert/apiclient_cert.p12").getPath()  ;
//        String certPath = "/cert/cert/apiclient_cert.p12";
        //	String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
//		String str = path.substring(0,path.indexOf("WEB-INF"))+"cert/cert/apiclient_cert.p12";
        //	System.out.println(str);
        //	File file = new File(str);
        ////    InputStream certStream = new FileInputStream(file);
        //   this.certData = new byte[(int) file.length()];
        //   certStream.read(this.certData);
        //   certStream.close();
        //File file = new File(this.getClass().getResource("/").getPath());
        //	System.out.println(file.getAbsolutePath());
        //上面是证书配置，需要证书时导入证书
    }

    public MyConfig() throws Exception {
    }


    @Override
    public String getAppID() {
        // TODO Auto-generated method stub
        return "此处是APPid";
    }

    @Override
    public String getMchID() {
        // TODO Auto-generated method stub
        return "1498968742";//商户号
    }

    @Override
    public String getKey() {
        // TODO Auto-generated method stub
        return "8uFbWJXydw8rKKdGLUhosCBAZEwofQAV";//api密钥
    }

    @Override
    public InputStream getCertStream() {
        // TODO Auto-generated method stub
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        // TODO Auto-generated method stub
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        // TODO Auto-generated method stub
        return 10000;
    }
}

