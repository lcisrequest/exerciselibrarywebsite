package com.example.lcdemo.base.exception;

/**
 * @Description 所有业务异常的枚举
 */
public enum LcExceptionEnum {

	/**
	 * 其他
	 */
	WRITE_ERROR(500,"渲染界面错误"),
	// AUTH_USER_NOT_EXIST(201, "您登录的账户不存在"),
	// AUTH_USER_NOT_EXIST(201, "您登录的账户不存在"),
	// AUTH_USER_NOT_EXIST(201, "您登录的账户不存在"),
	/**
	 * 解码错误
	 */
	IMG64_ERROR(500,"解码错误"),
	NOT_DELETE_SYSTEM_ADMIN(201, "无法删除系统管理员"),
	/**
	 * 文件上传
	 */
	FILE_READING_ERROR(400,"FILE_READING_ERROR!"),
	FILE_NOT_FOUND(400,"FILE_NOT_FOUND!"),
	/**
	 * 错误的请求
	 */
	REQUEST_NULL(400, "请求有错误"),
	SERVER_ERROR(500, "服务器异常"),
	HAS_REGISTER(201, "用户已注册"),
	HAS_NOT_USER(201, "您登录的用户不存在" ),
	PASSWORD_NOT_VALIDATE(201, "密码错误,请重新输入"),
	OTHER_ERROR(201, "您的网络有问题,请稍后重试"),
	PARAM_ERROR(201,"参数错误"),
	TEST_IS_NOT_EXIST(201,"该测试不存在"),
	PARAM_NULL(201,"参数为空");

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
