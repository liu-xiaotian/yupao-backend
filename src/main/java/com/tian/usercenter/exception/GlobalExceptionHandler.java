package com.tian.usercenter.exception;

import com.tian.usercenter.common.BaseResponse;
import com.tian.usercenter.common.ErrorCode;
import com.tian.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e){
        log.error("runtimeException" + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(Exception e){
        log.error("runtimeException" + e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "");
    }
}
