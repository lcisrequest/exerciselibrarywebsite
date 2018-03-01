package com.example.lcdemo.modular.auth.controller.dto;


import com.example.lcdemo.modular.admin.validator.dto.Credence;

/**
 * 认证的请求dto
 *
 */
public class AuthRequest implements Credence {

    private String identityType;//凭据类型
    private String identifier;//帐号
    private String credential;//密码

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    @Override
    public String getIdentityType() {
        return identityType;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getCredential() {
        return credential;
    }
}
