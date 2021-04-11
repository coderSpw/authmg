package com.spw.authmg.vo;

import lombok.Data;

/**
 * @Description 登录信息
 * @Author spw
 * @Date 2021/3/29
 */
@Data
public class LoginVO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String kaptcha;
}
