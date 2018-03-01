package com.example.lcdemo.modular.admin.validator.dto;

/**
 * 验证的凭据
 *
 */
public interface Credence {

    /**
     * 凭据类型
     */
    String getIdentityType();

    /**
     * 凭据名称
     */
    String getIdentifier();

    /**
     * 密码或者是其他的验证码之类的
     */
    String getCredential();
}
