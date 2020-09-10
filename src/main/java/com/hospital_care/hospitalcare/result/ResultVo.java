package com.hospital_care.hospitalcare.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 带参数返回值封装
 */
@Data
@AllArgsConstructor
public class ResultVo<T> {

    public ResultVo(String msg, int code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    /**
     * 返回提示信息
     */
    private String msg;
    /**
     * 返回状态码
     */
    private int code;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 返回数据2
     */
    private T data2;
}
