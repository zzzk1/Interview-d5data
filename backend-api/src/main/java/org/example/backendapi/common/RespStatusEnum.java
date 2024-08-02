package org.example.backendapi.common;

import lombok.Getter;

@Getter
public enum RespStatusEnum {
    SUCCESSFUL(200),
    NOT_FOUND(404),
    SERVER_ERROR(500),
    INCORRECT_USERNAME_OR_PASSWORD(400100);
    private int code;

    private RespStatusEnum(int code) {
        this.code = code;
    }
}
