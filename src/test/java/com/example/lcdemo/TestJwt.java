package com.example.lcdemo;

import com.alibaba.fastjson.JSON;
import com.example.lcdemo.base.util.MD5Util;
import com.example.lcdemo.modular.auth.converter.BaseTransferEntity;
import com.example.lcdemo.modular.auth.security.impl.Base64SecurityAction;
import io.jsonwebtoken.Jwts;
import org.junit.Test;
import test.TestObject;
/**
 * Created by tsy
 * 头文件 ：
 * Authorization : Bearer {token}
 * 加密过程
 * 1.将数据(json格式)进行base64编码得到object数据, 把object+randomkey(登录时返回的)再进行md5加密作为sign
 * 2.api请求格式:{"object":object,"sign":sign}
 */
public class TestJwt {
    @Test
    public void test(){
        String key = "mySecret";

        String compactJws = "eyJhbGciOiJIUzUxMiJ9.eyJyYW5kb21LZXkiOiJwZmtxc2kiLCJzdWIiOiJhZG1pbiIsImV4cCI6MTUxMjg5MTc4MywiaWF0IjoxNTEyMjg2OTgzfQ.aDhfRP4xVHALho7NKN9R8DlJ3UixkbBtjgVHDJ6LDGJDaETNLjXuhDFK05NOfnMN0CTPueBr9s3TdFY-2Cj3yg";
        String randomKey = "pfkqsi";

        TestObject testObject = new TestObject();
        testObject.setName("唐思尧");

        //将数据转为json
        String jsonString = JSON.toJSONString(testObject);
        //进行base64编码
        String encode = new Base64SecurityAction().doAction(jsonString);
        //将数据和签名字符串用MD5加密
        String md5 = MD5Util.encrypt(encode + randomKey);

        //将base64编码后的数据和签名一起发送请求
        BaseTransferEntity baseTransferEntity = new BaseTransferEntity();
        baseTransferEntity.setObject(encode);
        baseTransferEntity.setSign(md5);

        System.out.println(JSON.toJSONString(baseTransferEntity));

//        {"object":"eyJhZ2UiOjEyLCJuYW1lIjoiZmZmZiIsInRpcHMiOiJjb2RlIiwidXNlciI6InN0eWxlZmVuZyJ9","sign":"d3e52a45b436e99a860bce42e3731f01"}
        System.out.println("body = " + Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getBody());
        System.out.println("header = " + Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getHeader());
        System.out.println("signature = " + Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getSignature());
    }
}
