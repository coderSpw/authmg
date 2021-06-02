package com.spw.authmg.core.http;

import lombok.*;

import java.io.Serializable;

/**
 * 统一响应结果集
 * @author spw
 * @date 2021/02/15
 */
@Data
@Builder
public class RespResult implements Serializable {
    /**
     * 响应码
     */
    private int code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应报文
     */
    private Object data;

    public static RespResult sucessResult(Object data) {
        return RespResult.builder()
                .code(ResultCode.SUCCESS.code)
                .msg(ResultCode.SUCCESS.msg)
                .data(data)
                .build();
    }

    public static RespResult sucessResult() {
        return RespResult.builder()
                .code(ResultCode.SUCCESS.code)
                .msg(ResultCode.SUCCESS.msg)
                .build();
    }

    public static RespResult failResult(String msg) {
        return RespResult.builder()
                .code(ResultCode.SERVER_ERROR.code)
                .msg(ResultCode.SERVER_ERROR.msg + ":" + msg)
                .build();
    }

}
