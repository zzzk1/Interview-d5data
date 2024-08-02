package org.example.backendapi.handler;

import org.example.backendapi.common.Result;
import org.example.backendapi.exception.BizException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handling.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Result handleBizException(BizException bizException) {
        return Result.fail(bizException.getExceptionStatusEnum().getDesc());
    }
}
