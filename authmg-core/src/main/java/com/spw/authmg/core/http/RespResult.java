package com.spw.authmg.core.http;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果集
 * @author spw
 * @date 2021/02/15
 */
@Data
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

    public RespResult setCode(int code) {
        this.code = code;
        return this;
    }

    public RespResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public RespResult setData(Object data) {
        this.data = data;
        return this;
    }

    public static RespResult sucessResult(Object data) {
        return new RespResult()
                .setCode(ResultCode.SUCCESS.code)
                .setMsg(ResultCode.SUCCESS.msg)
                .setData(data);
    }

    public static RespResult sucessResult() {
        return new RespResult()
                .setCode(ResultCode.SUCCESS.code)
                .setMsg(ResultCode.SUCCESS.msg);
    }

    public static RespResult failResult(String msg) {
        return new RespResult()
                .setCode(ResultCode.SERVER_ERROR.code)
                .setMsg(ResultCode.SERVER_ERROR.msg + ":" + msg);
    }

}
