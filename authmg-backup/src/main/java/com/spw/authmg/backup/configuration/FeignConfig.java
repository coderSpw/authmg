package com.spw.authmg.backup.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description feign配置类
 * @Author spw
 * @Date 2021/4/11
 */
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor customReuqestInterceptor() {
        return new BaseFeignInterceptor();
    }
}
