package org.example.backendapi.exception;

import lombok.Getter;
import org.example.backendapi.common.ExceptionStatusEnum;

@Getter
public class BizException extends RuntimeException {

    private ExceptionStatusEnum exceptionStatusEnum;

    public BizException(ExceptionStatusEnum statusEnum) {
        this.exceptionStatusEnum = statusEnum;
    }
}
