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
import com.example.lcdemo.modular.backend.dao.AdminMapper;
import com.example.lcdemo.modular.backend.model.Admin;
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
    JwtProperties jwtProperties;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    AdminMapper adminMapper;

    @RequestMapping(value = "${jwt.auth-path}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserInfo userInfo) {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        if (username == null || "".equals(username) || password == null || "".equals(password)) {
            throw new LcException(LcExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        int ban = userInfoMapper.selectUserIsBan(username);
        if (ban == 1) {
            throw new LcException(LcExceptionEnum.USER_IS_BAN);
        }
        UserInfo user = new UserInfo();
        user.setUsername(username);
        user.setPassword(password);
        user = userInfoMapper.selectOne(user);
        if (user != null) {
            System.out.println("登陆成功");
            final String randomKey = jwtTokenUtil.getRandomKey();
            //生成token
            final Map<String, Object> tokenMapper = jwtTokenUtil.generateToken(user.getId() + "", randomKey);

            return ResponseEntity.ok(SuccessTip.create(new AuthResponse(tokenMapper.get("token").toString(), randomKey), null));
        } else {
            throw new LcException(LcExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
    }

    /**
     * 管理员登录
     *
     * @param admin
     * @return
     */
    @RequestMapping(value = "${jwt.admin-auth-path}")
    public ResponseEntity<?> adminLogin(@RequestBody Admin admin) {
        String username = admin.getUsername();
        String password = admin.getPassword();
        if (username == null || "".equals(username) || password == null || "".equals(password)) {
            throw new LcException(LcExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        admin = adminMapper.selectOne(admin);
        if (admin != null) {
            final String adminrandomKey = jwtTokenUtil.getRandomKey();
            final Map<String, Object> tokenMapper = jwtTokenUtil.generateToken(admin.getId() + "", adminrandomKey);
            return ResponseEntity.ok(com.itspeed.higu.base.tips.SuccessTip.create(new AuthResponse(tokenMapper.get("token").toString(), adminrandomKey), null));
        } else {
            throw new LcException(LcExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
    }

    @RequestMapping(value = "/auth/logout/v1")
    public ResponseEntity<?> logout() {
        System.out.println("3");
        Claims tokenClaims = jwtTokenUtil.getClaimFromToken(HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(9).replace("}", ""));
        System.out.println("3");
        return ResponseEntity.ok(SuccessTip.create("注销成功"));
    }

    @RequestMapping(value = "/admin/logout/v1")
    public ResponseEntity<?> logoutAdmin() {
        Claims tokenClaims = jwtTokenUtil.getClaimFromToken(HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(9).replace("}", ""));
        return ResponseEntity.ok(SuccessTip.create("注销成功"));
    }
}
