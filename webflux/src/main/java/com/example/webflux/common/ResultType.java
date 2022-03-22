package com.example.webflux.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultType {
    OPERATION_SUCCESS(0,"操作成功"),
    OPERATION_FAIL(1,"操作失敗");

    int code;
    String msg;

}
