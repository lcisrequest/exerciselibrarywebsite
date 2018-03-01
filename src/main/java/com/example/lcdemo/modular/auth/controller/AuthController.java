package com.example.lcdemo.modular.auth.controller;

import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.support.HttpKit;
import com.example.lcdemo.base.tips.SuccessTip;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.config.properties.JwtProperties;
import com.example.lcdemo.modular.admin.dao.UserInfoMapper;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.auth.controller.dto.AuthRequest;
import com.example.lcdemo.modular.auth.controller.dto.AuthResponse;
import com.example.lcdemo.modular.auth.util.JwtTokenUtil;
import com.itspeed.higu.base.exception.HiguException;
import com.itspeed.higu.base.exception.HiguExceptionEnum;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求验证的
 *
 * @author
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @RequestMapping(value = "${jwt.auth-path}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserInfo userInfo) {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        UserInfo user = new UserInfo();
        user.setUsername(username);
        user.setPassword(password);
        user = userInfoMapper.selectOne(user);
        if(user!=null){
            System.out.println("登陆成功");
        }
        if (null != user) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            //生成token
            final Map<String, Object> tokenMapper = jwtTokenUtil.generateToken(user.getId() + "", randomKey);

            return ResponseEntity.ok(com.itspeed.higu.base.tips.SuccessTip.create(new AuthResponse(tokenMapper.get("token").toString(), randomKey), null));
        } else {
            throw new HiguException(HiguExceptionEnum.PASSWORD_NOT_VALIDATE);
        }
    }

    /**
     * 管理员登录
     * @param authRequest
     * @return
     */
    @RequestMapping(value = "${jwt.admin-auth-path}")
    public ResponseEntity<?> adminLogin(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "/auth/logout/v1")
    public ResponseEntity<?> logout() {
        Claims tokenClaims = jwtTokenUtil.getClaimFromToken(HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(7));
        return ResponseEntity.ok(SuccessTip.create("注销成功"));
    }

    @RequestMapping(value = "/admin/logout/v1")
    public ResponseEntity<?> logoutAdmin() {
        Claims tokenClaims = jwtTokenUtil.getClaimFromToken(HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(7));
        return ResponseEntity.ok(SuccessTip.create("注销成功"));
    }
}
