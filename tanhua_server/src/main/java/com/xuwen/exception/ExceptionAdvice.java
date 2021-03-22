package com.xuwen.exception;


import com.xuwen.pojo.vo.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类
 * @RestControllerAdvice：拦截业务层异常注解
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    /**
     * 处理自定义的业务异常
     * @param ex
     * @return
     */
    //@ExceptionHandler：处理异常注解
    //@ExceptionHandler(TanHuaException.class)：定义的处理哪个异常
    @ExceptionHandler(TanHuaException.class)
    public ResponseEntity handleTanHuaException(TanHuaException ex){
        if(null != ex.getErrData()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getErrData());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ErrorResult.error("000009",ex.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex){
        log.error("发生未知异常",ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResult.error());
    }
}