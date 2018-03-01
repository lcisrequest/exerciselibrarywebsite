package com.example.lcdemo.modular.auth.security;

/**
 * Created by tsy
 */
public interface DataSecurityAction {
    /**
     * 执行数据的保护措施
     */
    String doAction(String beProtected);

    /**
     * 解除保护
     */
    String unlock(String securityCode);
}
