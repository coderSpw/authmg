package com.spw.authmg.core.http;

/**
 * 响应码枚举
 * @author spw
 * @date 2021/02/15
 */
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 服务器出错
     */
    SERVER_ERROR(500, "服务器异常");

    public int code;

    public String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
