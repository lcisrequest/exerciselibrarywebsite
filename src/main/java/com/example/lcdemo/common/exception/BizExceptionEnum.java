package com.example.lcdemo.common.exception;

/**
 * 所有业务异常的枚举
 *
 */
public enum BizExceptionEnum {

    /**
     * token异常
     */
    TOKEN_EXPIRED(700, "请重新登录"),
    TOKEN_ERROR(700, "登录失败，请重新登录"),

    /**
     * 签名异常
     */
    SIGN_ERROR(700, "签名验证失败"),


    /**
     * 文件上传
     */
    FILE_READING_ERROR(400,"FILE_READING_ERROR!"),
    FILE_NOT_FOUND(400,"FILE_NOT_FOUND!"),
    UPLOAD_ERROR(500,"上传图片出错"),

    /**
     * 其他
     */
    AUTH_REQUEST_ERROR(400, "账号密码错误"),

    /**
     * 无权限
     */
    ADMIN_AUTH_ERROR(400,"无操作权限"),

    UNKNOWN_ERROR(400,"操作异常");

    BizExceptionEnum(int code, String message) {
        this.friendlyCode = code;
        this.friendlyMsg = message;
    }

    private int friendlyCode;

    private String friendlyMsg;

    public int getCode() {
        return friendlyCode;
    }

    public void setCode(int code) {
        this.friendlyCode = code;
    }

    public String getMessage() {
        return friendlyMsg;
    }

    public void setMessage(String message) {
        this.friendlyMsg = message;
    }

}
