package com.executor.gateway.core;

import com.executor.gateway.core.constant.RESPONSE;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应实体
 */
@Data
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 1853785175677090662L;

    private int code = RESPONSE.SUCCESS;
    private String message = "success";
    private T data;

    public ApiResult() {
    }

    public ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResult(T data) {
        this.data = data;
    }

    public ApiResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
