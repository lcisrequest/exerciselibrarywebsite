package com.example.lcdemo.common.aop;

import com.example.lcdemo.base.aop.BaseControllerExceptionHandler;
import com.example.lcdemo.base.tips.ErrorTip;
import com.example.lcdemo.common.exception.BizExceptionEnum;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseControllerExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截jwt相关异常
     */
    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity jwtException(JwtException e) {
        log.error("jwt验证异常:", e);
        return ResponseEntity.status(BizExceptionEnum.TOKEN_ERROR.getCode()).body(new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(),BizExceptionEnum.TOKEN_ERROR.getMessage()));
    }

}
