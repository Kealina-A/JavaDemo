package com.example.webflux.common;



public class Result {

    public static BaseResponse<Void> success() {
        return new BaseResponse<>(ResultType.OPERATION_SUCCESS);
    }
    public static BaseResponse<Object> success(Object data) {
        return new BaseResponse<>(ResultType.OPERATION_SUCCESS, data);
    }

    public static BaseResponse<Void> fail() {
        return new BaseResponse<>(ResultType.OPERATION_FAIL);
    }
}
