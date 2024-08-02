package org.example.backendapi.common;

import lombok.Getter;

@Getter
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result(RespStatusEnum status, String message, T data) {
        this.code = status.getCode();
        this.message = message;
        this.data = data;
    }

    public static<T> Result<T> success(RespStatusEnum status, String message, T data) {
        return new Result<>(status, message, data);
    }

    public static<T> Result<T> success(RespStatusEnum status, String message) {
        return new Result<>(status, message, null);
    }

    public static<T> Result<T> success(T data) {
        return new Result<>(RespStatusEnum.SUCCESSFUL, "successful", data);
    }

    public static<T> Result<T> fail(RespStatusEnum status, String message) {
        return new Result<>(status, message, null);
    }

    public static<T> Result<T> fail(String message) {
        return new Result<>(RespStatusEnum.SERVER_ERROR, message, null);
    }
}
