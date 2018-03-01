package com.example.lcdemo.base.tips;

/**
 * 返回给前台的成功提示
 *
 */
public class SuccessTip extends Tip {
	
	public SuccessTip(){
		super.code = 200;
		super.message = "操作成功";
	}

	public static SuccessTip create(){
		return new SuccessTip();
	}

	public static Tip create(String msg){
		Tip successTip = new SuccessTip();
		successTip.setMessage(msg);
		return successTip;
	}

	public static Tip create(Object data,String msg) {
		Tip successTip = new SuccessTip();
		successTip.setData(data);
		successTip.setMessage(msg);
		return successTip;
	}
}
