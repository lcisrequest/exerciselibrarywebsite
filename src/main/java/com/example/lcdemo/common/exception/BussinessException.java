package com.example.lcdemo.common.exception;


import com.example.lcdemo.base.exception.LcException;

/**
 * @Description 业务异常的封装
 */
public class BussinessException extends LcException {

	public BussinessException(BizExceptionEnum bizExceptionEnum) {
		super(bizExceptionEnum.getCode(), bizExceptionEnum.getMessage(),"");
	}
}
