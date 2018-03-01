package com.example.lcdemo.base.aop;

import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.tips.ErrorTip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 */
public class BaseControllerExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截业务异常
     *
     * @author
     */
    @ExceptionHandler(LcException.class)
    @ResponseBody
    public ResponseEntity notFount(LcException e) {
        log.error("业务异常:", e);
//        ErrorTip errorTip = new ErrorTip(HiguExceptionEnum.OTHER_ERROR,e.getMessage());
        ErrorTip errorTip = new ErrorTip(e.getCode(),e.getMessage());
        return ResponseEntity.status(e.getCode()).body(errorTip);
//        return new ErrorTip(e.getCode(), e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     *
     * @author
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity notFount(RuntimeException e) {
        log.error("运行时异常:", e);
        ErrorTip errorTip = new ErrorTip(LcExceptionEnum.OTHER_ERROR.getCode(),LcExceptionEnum.OTHER_ERROR.getMessage());
        return ResponseEntity.status(LcExceptionEnum.OTHER_ERROR.getCode()).body(errorTip);
//        return new ErrorTip(HiguExceptionEnum.SERVER_ERROR.getCode(), HiguExceptionEnum.SERVER_ERROR.getMessage());
    }

}
