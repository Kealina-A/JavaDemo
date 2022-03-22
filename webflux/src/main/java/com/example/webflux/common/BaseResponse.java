package com.example.webflux.common;

import lombok.Data;

@Data
public class BaseResponse<T> {

    private Integer code;

    private String msg;

    private T data;

    private BaseResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(ResultType resultType, T data) {
        this.code = resultType.getCode();
        this.msg = resultType.getMsg();
        this.data = data;
    }

    public BaseResponse(ResultType resultType) {
        this.code = resultType.getCode();
        this.msg = resultType.getMsg();
    }

}
