package com.example.lcdemo.base.exception;

/**
 * @Description 所有业务异常的枚举
 */
public enum LcExceptionEnum {

    /**
     * 其他
     */
    WRITE_ERROR(500, "渲染界面错误"),
    // AUTH_USER_NOT_EXIST(201, "您登录的账户不存在"),
    // AUTH_USER_NOT_EXIST(201, "您登录的账户不存在"),
    // AUTH_USER_NOT_EXIST(201, "您登录的账户不存在"),
    /**
     * 解码错误
     */
    IMG64_ERROR(500, "解码错误"),
    NOT_DELETE_SYSTEM_ADMIN(201, "无法删除系统管理员"),
    /**
     * 文件上传
     */
    FILE_READING_ERROR(400, "FILE_READING_ERROR!"),
    FILE_NOT_FOUND(400, "FILE_NOT_FOUND!"),
    /**
     * 错误的请求
     */
    INTEREST_NOT_EXIST(201,"该兴趣不存在"),
    COURSE_NOT_EXIST(201,"该课程不存在"),
    CANNOT_DELETE(201,"这不是你的，不能删除"),
    MIN_BIGTHAN_MAX(201,"最大值不能比最小值小！"),
    DB_DATA_ERROR(201,"数据库初始数据设置出错！"),
    TODAY_IS_HAVE_CLOCK(201,"今天已经打过卡了呦！"),
    ADMIN_NOT_EXIST(201,"该管理员不存在"),
    COURSE_IS_EXIST(201,"该课程已存在"),
    SUPERADMIN_CANNOT_DELETE(201, "超级管理员不能删除"),
    DONNOT_HAVE_PAWER(201, "您的权限不足，只有超级管理员拥有添加和删除管理员的权限"),
    REQUEST_NULL(400, "请求有错误"),
    SERVER_ERROR(500, "服务器异常"),
    HAS_REGISTER(201, "用户已注册"),
    HAS_NOT_USER(201, "您登录的用户不存在"),
    PASSWORD_NOT_VALIDATE(201, "密码错误,请重新输入"),
    USERNAME_OR_PASSWORD_ERROR(201, "用户名或密码错误"),
    OTHER_ERROR(201, "您的网络有问题,请稍后重试"),
    PARAM_ERROR(201, "参数错误"),
    TEST_IS_NOT_EXIST(201, "该测试不存在"),
    SUBJECT_IS_NOT_EXIST(201, "该习题不存在"),
    SUBJECCT_IS_NULL(201, "该类型习题库为空"),
    ANSWER_NUM_IS_WRONG(201, "答案数量与题目数量不匹配"),
    PARAM_NULL(201, "参数为空");

    LcExceptionEnum(int code, String message) {
        this.friendlyCode = code;
        this.friendlyMsg = message;
    }

    LcExceptionEnum(int code, String message, String urlPath) {
        this.friendlyCode = code;
        this.friendlyMsg = message;
        this.urlPath = urlPath;
    }

    private int friendlyCode;

    private String friendlyMsg;

    private String urlPath;

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

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

}
