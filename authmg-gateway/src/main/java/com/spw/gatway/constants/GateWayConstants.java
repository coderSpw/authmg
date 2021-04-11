package com.spw.gatway.constants;

/**
 * @Description 网关常量池
 * @Author spw
 * @Date 2021/4/11
 */
public final class GateWayConstants {
    /**
     * cors配置
     */
    public static final String ALLOWED_HEADERS = "uid,x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN,token,username,client";
    public static final String ALLOWED_METHODS = "*";
    public static final String ALLOWED_ORIGIN = "*";
    public static final String ALLOWED_Expose = "*";
    public static final String MAX_AGE = "18000L";
}
