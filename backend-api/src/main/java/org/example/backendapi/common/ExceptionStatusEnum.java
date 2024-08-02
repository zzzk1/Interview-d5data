package org.example.backendapi.common;

import lombok.Getter;

@Getter
public enum ExceptionStatusEnum {
    PERMISSION_DENY("need login");
    private final String desc;

    ExceptionStatusEnum(String desc) {
        this.desc = desc;
    }
}
