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

	/**
	 * 经纪商组
	 */
	NOT_EXIST_AGENT(201, "不存在的经纪商"),
	HAS_EXIST_AGENT_GROUP(201, "已经存在该经纪商组"),

	EXIST_CHILD_USER(201, "该推广员存在下级用户/推广员，请先修改下级成员的所属推广员再删除"),
	EXIST_AGENT_CHILD_USER(201, "该经纪商存在下级用户/推广员，请先修改下级成员的所属经纪商再删除"),

	/**
	 * 后台商城管理
	 */
	NOT_EXIST_GOOD(201, "没有该商品"),
	GOODS_HAS_SHIPPED(201, "该商品已经发货"),

	/**
	 * 结算
	 */
	RECHARGE_WITHDRAW_FAILED(201, "提现失败"),

	HAS_COLLECTION_STOCK(201,"已在自选列表中" ),
	HAS_REGISTER(201, "用户已注册"),
	HAS_NOT_USER(201, "您登录的用户不存在" ),
	USER_IS_NOT_ENABLE(201, "您的账户已被禁用" ),
	PASSWORD_NOT_VALIDATE(201, "密码错误,请重新输入"),
	HAS_NOT_STOCK_P(201,"没有入市权限" ),
	NOT_ARRIVE_DAYS(201,"未达策略天数" ),
	STRATEGY_STATUS_ERROR(201,"订单状态错误" ),
	OTHER_ERROR(201, "您的网络有问题,请稍后重试"),
	CAN_NOT_BUY_STOCK(201, "您无法进行股票购买"),
	PRODUCT_NOT_START_TRANSACTION(201, "本产品未开启交易"),
	OVER_CONFIG(201,"超过购买限制,无法购买" ),
	NOT_ENOUGH_BALANCE(201,"余额不足!" ),
	SEND_SMS_ERROR(201, "短信发送失败!"),
	NOT_FOUND_RESOURCE(201, "未找到该资源"),

	NOT_FOUND_FEEDBACK(201, "未找到该反馈"),

	EXIST_ROLE(201, "已经存在该角色"),
	PARAM_ERROR(201,"参数错误"),
	NOT_FOUND_INVITATION_CODE(201, "未知邀请码"),
	ZHANGTING(201, "该股票已涨停，暂时无法买入"),
	DIETING(201, "该股票已跌停，暂时无法卖出"),
	CANT_USED_COUPON(201, "红包不可用"),
	ZSJC_ZD_HAS_RECORDS(201, "请勿重复下注!"),
	ZSJC_SDHY_NOT_FOUND(201, "请先下注"),
	ZSJC_SDHY_NOT_THREE(201, "三个游戏都要下注哟!"),
	ZSJC_SDHY_REPEAT(201,"请勿重复使用!" ),
	GOODS_NULL(201,"商品不存在"),
	ADDRESS_NULL(201,"收货地址未设置"),
	PARAM_NULL(201,"参数为空"),
	PROMOTE_SETTLEMENT_ERROR(201,"没有可结算的佣金"),
	PROMOTE_WITHDRAW_ERROR(201,"提现失败,佣金还不能提现"),
	Money_NOT_ENOUGH(201,"您的余额不足，请充值后重试"),
	REQUEST_REPEAT(201,"请求重复"),
	CAPTCHA_REPEAT(201,"不可重复发送" ),
	SHART_ORDER_REPEAT(201,"不可重复晒单！"),
	CAPTCHA_ERROR(201,"验证码错误"),
	IDNUMBER_ERROR(201,"身份证号不合法"),
	BANKCARD_ERROR(201,"银行卡号不合法"),
	USER_MISS_ERROR(201,"该用户不存在"),
	YOU_ARE_FRIENDS_OR_REQUEST_MISS(201,"该好友未请求，或请求已同意"),
	COUPON_BALANCE_ERROR(201, "现金券余额不足"),
	NOT_RIGHT_TIME(201, "非交易时间"),
	BANKCARD_NOT_SET(201,"您还未绑定银行卡");

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
