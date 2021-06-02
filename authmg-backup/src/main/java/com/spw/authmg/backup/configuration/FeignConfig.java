package com.spw.authmg.backup.configuration;

import feign.Logger;
import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Description feign配置类
 * @Author spw
 * @Date 2021/4/11
 */
@Configuration
public class FeignConfig {

    /**
     * 请求与响应日志记录
     * none  不记录日志
     * basic 只记录方法和url以及响应状态码 执行时间
     * headers 记录请求和应答的头信息
     * full 记录请求和响应的头信息 正文  元数据
     * @return
     */
    @Bean
    public Logger.Level logger() {
        return Logger.Level.FULL;
    }

    /**
     * 重试 每500ms重试一次 一共重试3次
     * @return
     */
    @Bean
    public Retryer retryer() {
        return new Retryer.Default(500, TimeUnit.SECONDS.toMillis(1), 3);
    }

    /**
     * feign 请求拦截
     * @return
     */
    @Bean
    public RequestInterceptor customReuqestInterceptor() {
        return new BaseFeignInterceptor();
    }
}
